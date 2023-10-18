package com.stonedt.intelligence.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.intelligence.constant.PromptConstant;
import com.stonedt.intelligence.constant.RedisPrefixConstant;
import com.stonedt.intelligence.dao.UserDao;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.ArticleService;
import com.stonedt.intelligence.service.PlatformService;
import com.stonedt.intelligence.util.ResultUtil;
import com.stonedt.intelligence.util.UserUtil;
import com.stonedt.intelligence.vo.BindParamsVo;
import com.stonedt.intelligence.vo.CopyWriting;
import com.stonedt.intelligence.vo.SseData;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 文轩
 */
@Service
@Slf4j
public class PlatformServiceImpl implements PlatformService {

    private final UserDao userDao;

    private final RestTemplate restTemplate;

    private final OkHttpClient okHttpClient;

    private final UserUtil userUtil;

    private final ArticleService articleService;

    private final StringRedisTemplate redisTemplate;

    @Value("${platform.nlp.ocr-url}")
    private String nlpOcrUrl;

    @Value("${platform.xie.copy-writing}")
    private String xieCopyWritingUrl;

    @Value("${platform.xie.title}")
    private String xieTitleUrl;

    @Value("${platform.notice.url}")
    private String noticeUrl;

    @Value("${platform.synthesize.url}")
    private String synthesizeUrl;

    public PlatformServiceImpl(UserDao userDao,
                               RestTemplate restTemplate,
                               OkHttpClient okHttpClient,
                               UserUtil userUtil,
                               ArticleService articleService,
                               StringRedisTemplate redisTemplate) {
        this.userDao = userDao;
        this.restTemplate = restTemplate;
        this.okHttpClient = okHttpClient;
        this.userUtil = userUtil;
        this.articleService = articleService;
        this.redisTemplate = redisTemplate;
    }


    /**
     * nlp服务绑定
     *
     * @param bindParamsVo 绑定参数
     * @param request
     */
    @Override
    public ResultUtil nlpBind(BindParamsVo bindParamsVo, HttpServletRequest request) {
        // 获取用户id
        User user = userUtil.getuser(request);
        bindParamsVo.setUserId(user.getId());

        try {
            userDao.bindNlp(bindParamsVo);
            User newUser = userDao.selectById(user.getId());
            request.getSession().setAttribute("User", newUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.build(500, "绑定失败");
        }
        return ResultUtil.ok();
    }

    /**
     * nlp光学字符识别
     *
     * @param user   用户
     * @param images 图片
     */
    @Override
    public ResultUtil nlpOcr(User user, MultipartFile images) throws IOException {
        //调用nlp服务,参数为图片

        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();

        ByteArrayResource fileAsResource = new ByteArrayResource(images.getBytes()) {
            @Override
            public String getFilename() {
                return images.getOriginalFilename();
            }
            @Override
            public long contentLength() {
                return images.getSize();
            }
        };

        params.add("images", fileAsResource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("secret-id",user.getNlp_secret_id());
        headers.set("secret-key",user.getNlp_secret_key());
        HttpEntity<MultiValueMap<String,Object>> requestEntity  = new HttpEntity<>(params, headers);
        String result = restTemplate.postForObject(nlpOcrUrl, requestEntity, String.class);

        if (result == null){
            return ResultUtil.build(500, "nlp服务调用失败");
        }
        JSONObject jsonObject = JSON.parseObject(result);
        Object code = jsonObject.get("code");
        //转Integer
        int codeInt = Integer.parseInt(code.toString());
        if (codeInt != 200){
            return ResultUtil.build(codeInt, jsonObject.get("msg").toString());
        }
        return ResultUtil.ok(jsonObject.get("results"));
    }

    /**
     * 写作宝服务绑定
     *
     * @param bindParamsVo 绑定参数
     * @param request
     * @return 绑定结果
     */
    @Override
    public ResultUtil xieBind(BindParamsVo bindParamsVo, HttpServletRequest request) {
        // 获取用户id
        User user = userUtil.getuser(request);
        bindParamsVo.setUserId(user.getId());
        try {
            userDao.bindXie(bindParamsVo);
            User newUser = userDao.selectById(user.getId());
            request.getSession().setAttribute("User", newUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.build(500, "绑定失败");
        }
        return ResultUtil.ok();
    }

    /**
     * 写作宝服务调用
     *
     * @param user        用户
     * @param copyWriting 写作宝参数
     * @param articleId 文章id
     * @return 写作宝结果
     */
    @Override
    public SseEmitter xieReport(User user, CopyWriting copyWriting, String articleId) {
        copyWriting.setPromptId(PromptConstant.XIE_REPORT);
        String text = copyWriting.getParams().get("text");
        //去除html标签,保留文字
        text = text.replaceAll("<[^>]*>", "");
        if (text.length() > PromptConstant.MAX_INPUT_LENGTH) {
            text = text.substring(0, PromptConstant.MAX_INPUT_LENGTH);
        }
        copyWriting.getParams().put("text", text);
        //调用写作宝服务
        RequestBody requestBody = RequestBody.create(JSON.toJSONString(copyWriting), okhttp3.MediaType.get("application/json"));
        Request request = new Request
                .Builder()
                .addHeader("secret-id", user.getXie_secret_id())
                .addHeader("secret-key", user.getXie_secret_key())
                .method("POST", requestBody)
                .url(xieCopyWritingUrl)
                .build();
        EventSource.Factory factory = EventSources.createFactory(okHttpClient);
        SseEmitter sseEmitter = new SseEmitter(300000L);
        sseEmitter.onError(e->{
            log.error("与用户{}的sse连接发生错误",user.getId());
            e.printStackTrace();
        });
        sseEmitter.onCompletion(()-> log.info("与用户{}的sse请求完成",user.getId()));
        //生成内容缓存
        StringBuilder contentCache = new StringBuilder();
        EventSourceListener listener = new EventSourceListener() {
            @Override
            public void onClosed(@NotNull EventSource eventSource) {
                super.onClosed(eventSource);
                log.info("用户{}的写作宝sse服务结束", user.getId());
                sseEmitter.complete();
            }

            @Override
            public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
                super.onEvent(eventSource, id, type, data);
                switch (type){
                    case "message":
                        JSONObject jsonObject = JSON.parseObject(data);
                        contentCache.append(jsonObject.get("data"));
                        break;
                    case "end":
                        sseEmitter.complete();
                        redisTemplate.opsForValue().set(RedisPrefixConstant.XIE_REPORT+articleId,contentCache.toString(),2,TimeUnit.DAYS);
                        break;
                    default:
                        break;
                }


                try {
                    sseEmitter.send(SseEmitter.event()
                            .id(id)
                            .name(type)
                            .data(data));
                } catch (IOException e) {
                    e.printStackTrace();
                    log.error("向用户{}发送的{}事件发送失败",user.getId(),type);
                }
            }

            @Override
            public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                super.onFailure(eventSource, t, response);
                log.error("用户{}的写作宝服务连接出错", user.getId());
                if (t != null) {
                    t.printStackTrace();
                }
                sseEmitter.complete();
            }

            @Override
            public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
                log.info("用户{}的写作宝服务连接建立", user.getId());
                super.onOpen(eventSource, response);
            }
        };
        factory.newEventSource(request, listener);
        return sseEmitter;
    }

    /**
     * 写作宝服务调用
     *
     * @param user        用户
     * @param articleId   文章id
     * @param projectId   项目id
     * @param relatedword 关键词
     * @param publishTime 发布时间
     * @param title      标题
     * @return 写作宝结果
     */
    @Override
    public SseEmitter xieReport(User user, String articleId, Long projectId, String relatedword, String publishTime, String title) {
        String contentCache = redisTemplate.opsForValue().get(RedisPrefixConstant.XIE_REPORT + articleId);
        String titleCache = redisTemplate.opsForValue().get(RedisPrefixConstant.XIE_TITLE + articleId);
        if (contentCache != null&&title.equals(titleCache)) {
            return sseFromCache(contentCache,user);
        }

        Map<String, Object> articleDetail = articleService.articleDetail(articleId, projectId, relatedword,publishTime);
        Object text = articleDetail.get("text");
        CopyWriting copyWriting = new CopyWriting();
        copyWriting.setTemperature(0.7f);
        HashMap<String, String> params = new HashMap<>(2);
        params.put("title", title);
        params.put("text", String.valueOf(text));
        copyWriting.setParams(params);
        return xieReport(user, copyWriting,articleId);
    }

    /**
     * 将缓存数据通过sse流的形式返回
     * @param cache
     * @return
     */
    private SseEmitter sseFromCache(String cache,User user) {
        int id = 1;
        SseEmitter sseEmitter = new SseEmitter(300000L);
        sseEmitter.onError(e->{
            log.error("与用户{}的sse连接发生错误",user.getId());
            e.printStackTrace();
        });
        sseEmitter.onCompletion(()-> log.info("与用户{}的sse请求完成",user.getId()));
        try {
            sseEmitter.send(SseEmitter
                    .event()
                    .id(String.valueOf(id))
                    .name("start")
                    .data("start"));
        } catch (IOException e) {
            log.error("用户{}sse{}事件发送失败",user.getId(),"start");
            e.printStackTrace();
        }
        int endIndex;
        for (int i = 0; i < cache.length(); i+=3) {
            id++;
            endIndex = i + 3;
            if (endIndex > cache.length()) {
                endIndex = cache.length();
            }
            String substring = cache.substring(i, endIndex);
            SseData sseData = new SseData();
            sseData.setData(substring);
            try {
                sseEmitter.send(SseEmitter
                        .event()
                        .id(String.valueOf(id))
                        .name("message")
                        .data(JSON.toJSONString(sseData)));

            } catch (IOException e) {
                log.error("用户{}sse{}事件发送失败",user.getId(),"data");
                e.printStackTrace();
            }

        }
        id++;
        try {
            sseEmitter.send(SseEmitter
                    .event()
                    .id(String.valueOf(id))
                    .name("end")
                    .data("end"));
        } catch (IOException e) {
            log.error("用户{}sse{}事件发送失败",user.getId(),"end");
            e.printStackTrace();
        }
        sseEmitter.complete();
        return sseEmitter;
    }

    /**
     * 写作宝标题生成
     *
     * @param user        用户
     * @param copyWriting 写作宝参数
     * @param articleId
     * @return 标题
     */
    @Override
    public ResultUtil xieReportTitle(User user, CopyWriting copyWriting, String articleId) {
        //查询缓存
        String titleCache = redisTemplate.opsForValue().get(RedisPrefixConstant.XIE_TITLE + articleId);
        if (titleCache != null) {
            return ResultUtil.ok(titleCache);
        }

        copyWriting.setPromptId(PromptConstant.XIE_REPORT);
        String text = copyWriting.getParams().get("text");
        //去除html标签,保留文字
        text = text.replaceAll("<[^>]*>", "");
        if (text.length() > 2900) {
            text = text.substring(0, 2900);
        }
        copyWriting.getParams().put("text", text);
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("secret-id", user.getXie_secret_id());
        headers.set("secret-key", user.getXie_secret_key());
        String jsonString = JSON.toJSONString(copyWriting);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
        String result = restTemplate.postForObject(xieTitleUrl, requestEntity, String.class);
        if (result == null) {
            return ResultUtil.build(500, "写作宝服务调用失败");
        }
        JSONObject jsonObject = JSON.parseObject(result);
        Object code = jsonObject.get("code");
        Object msg = jsonObject.get("msg");
        Object data = jsonObject.get("data");
        if ((int)code==200) {
            //缓存48小时
            redisTemplate.opsForValue().set(RedisPrefixConstant.XIE_TITLE + articleId,(String)data,2, TimeUnit.DAYS);
        }
        return ResultUtil.build(Integer.parseInt(code.toString()), msg.toString(), data);
    }

    /**
     * 获取最新公告
     *
     * @return 最新公告
     */
    @Override
    public ResultUtil getNewNotice() {
        String result = null;
        try {
            result = restTemplate.getForObject(noticeUrl, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return ResultUtil.build(500, "获取失败");
        }
        return JSON.parseObject(result, ResultUtil.class);
    }

    @Override
    public JSONObject getNewSynthesize() {
        String result = null;
        try {
            result = restTemplate.getForObject(synthesizeUrl, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
        JSONObject jsonObject = JSON.parseObject(result);
        Integer status = (Integer) jsonObject.get("status");

        if (status ==null ||status != 200) {
            return null;
        }
        return jsonObject.getJSONObject("data");
    }
}
