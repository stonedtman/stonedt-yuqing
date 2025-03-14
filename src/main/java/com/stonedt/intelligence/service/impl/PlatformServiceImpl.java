package com.stonedt.intelligence.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.intelligence.constant.XieConstant;
import com.stonedt.intelligence.constant.RedisPrefixConstant;
import com.stonedt.intelligence.dao.UserDao;
import com.stonedt.intelligence.dto.SecretDTO;
import com.stonedt.intelligence.dto.UserDTO;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.ArticleService;
import com.stonedt.intelligence.service.PlatformService;
import com.stonedt.intelligence.util.ResultUtil;
import com.stonedt.intelligence.util.ShaUtil;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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

    @Value("${platform.nlp.classpic-url}")
    private String nlpClasspicUrl;

    @Value("${platform.xie.url}")
    private String xieUrl;

    @Value("${platform.notice.url}")
    private String noticeUrl;

    @Value("${platform.synthesize.url}")
    private String synthesizeUrl;

    @Value("${platform.nlp.check-url}")
    private String nlpCheckUrl;

    @Value("${account.public.use}")
    private boolean accountPublicUse;

    @Value("${account.public.nlp.secret-id}")
    private String publicNlpSecretId;

    @Value("${account.public.nlp.secret-key}")
    private String publicNlpSecretKey;

    @Value("${account.public.xie.secret-id}")
    private String publicXieSecretId;

    @Value("${account.public.xie.secret-key}")
    private String publicXieSecretKey;

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
     * @param response
     */
    @Override
    public ResultUtil nlpBind(BindParamsVo bindParamsVo, HttpServletRequest request, HttpServletResponse response) {
        // 获取用户id
        User user = userUtil.getuser(request);
        bindParamsVo.setUserId(user.getId());
        SecretDTO secretDTO = new SecretDTO();
        BeanUtils.copyProperties(bindParamsVo, secretDTO);
        //校验secretId和secretKey
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String jsonString = JSON.toJSONString(secretDTO);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
        try {
            String result = restTemplate.postForObject(nlpCheckUrl, requestEntity, String.class);
            if (result == null) {
                return ResultUtil.build(500, "nlp服务调用失败");
            }
            JSONObject jsonObject = JSON.parseObject(result);
            Boolean exist = jsonObject.getBoolean("result");
            if (!exist) {
                return ResultUtil.build(500, "该nlp账号不存在");
            }
        } catch (RestClientException e) {
            log.error("nlp服务调用失败",e);
            return ResultUtil.build(500, "nlp校验服务调用失败");
        }
        try {
            userDao.bindNlp(bindParamsVo);
            User newUser = userDao.selectById(user.getId());
            userUtil.setUser(request,response,newUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.build(500, "绑定失败");
        }
        return ResultUtil.ok();
    }

    /**
     * 根据url获取图片
     */
    private MultipartFile getImageByUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        String fileName;
        byte[] bytes;
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return null;
            }
            //获取图片字真实链接地址
            String realUrl = response.request().url().toString();
            //获取图片文件名
            String tempFileName = realUrl.substring(realUrl.lastIndexOf("/") + 1);
            tempFileName = tempFileName.substring(0, tempFileName.indexOf("?"));
            if (tempFileName.length() > 70) {
                tempFileName = UUID.randomUUID().toString();
            }
            fileName = tempFileName;
            //获取图片字节流
            if (response.body() != null) {
                bytes = response.body().bytes();
            }else {
                return null;
            }
        }
        return new MultipartFile() {
            @Override
            public String getName() {
                return fileName;
            }

            @Override
            public String getOriginalFilename() {
                return fileName;
            }

            @Override
            public String getContentType() {
                return "image/jpeg";
            }

            @Override
            public boolean isEmpty() {
                return bytes.length == 0;
            }

            @Override
            public long getSize() {
                return bytes.length;
            }

            @Override
            public byte[] getBytes() {
                return bytes;
            }

            @Override
            public InputStream getInputStream() {
                return new ByteArrayInputStream(bytes);
            }

            @Override
            public void transferTo(File file) throws IOException, IllegalStateException {
                try(FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    fileOutputStream.write(bytes);
                }
            }
        };


    }

    /**
     * 调用nlp图像服务
     * @param user 用户
     * @param images 图片
     * @param url 调用地址
     * @return nlp服务返回结果
     * @throws IOException io异常
     */

    private String callNlpImages(User user, MultipartFile images,String url) throws IOException {
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

        final String secretId;
        final String secretKey;
        if (accountPublicUse) {
            secretId = publicNlpSecretId;
            secretKey = publicNlpSecretKey;
        }else {
            secretId = user.getNlp_secret_id();
            secretKey = user.getNlp_secret_key();
        }

        params.add("images", fileAsResource);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("secret-id",secretId);
        headers.set("secret-key",secretKey);
        HttpEntity<MultiValueMap<String,Object>> requestEntity  = new HttpEntity<>(params, headers);
        return restTemplate.postForObject(url, requestEntity, String.class);
    }

    /**
     * nlp光学字符识别
     *
     * @param user 用户
     */
    @Override
    public ResultUtil nlpOcr(User user, String imageUrl) throws IOException {
        String cache = redisTemplate.opsForValue().get(RedisPrefixConstant.NLP_OCR + imageUrl);
        if (cache != null) {
            return ResultUtil.ok(JSON.parse(cache));
        }
        //获取图片文件对象
        MultipartFile images = getImageByUrl(imageUrl);
        if (images == null) {
            return ResultUtil.build(500, "图片获取失败");
        }
        //调用nlp服务,参数为图片
        String result = callNlpImages(user, images, nlpOcrUrl);

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
        Object results = jsonObject.get("results");
        //缓存48小时
        redisTemplate.opsForValue().set(RedisPrefixConstant.NLP_OCR + imageUrl,JSON.toJSONString(results),2, TimeUnit.DAYS);
        return ResultUtil.ok(results);
    }

    @Override
    public ResultUtil nlpImage(User user, String imageUrl) throws IOException {
        String cache = redisTemplate.opsForValue().get(RedisPrefixConstant.NLP_IMAGE + imageUrl);
        if (cache != null) {
            return ResultUtil.ok(JSON.parse(cache));
        }
        //获取图片文件对象
        MultipartFile images = getImageByUrl(imageUrl);
        if (images == null) {
            return ResultUtil.build(500, "图片获取失败");
        }
        //调用nlp服务,参数为图片
        String result = callNlpImages(user, images, nlpClasspicUrl);
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
        Object results = jsonObject.get("results");
        //缓存48小时
        redisTemplate.opsForValue().set(RedisPrefixConstant.NLP_IMAGE + imageUrl,JSON.toJSONString(results),2, TimeUnit.DAYS);
        return ResultUtil.ok(results);
    }

    /**
     * 写作宝服务绑定
     *
     * @param bindParamsVo 绑定参数
     * @param request
     * @return 绑定结果
     */
    @Override
    public ResultUtil xieBind(BindParamsVo bindParamsVo, HttpServletRequest request, HttpServletResponse response) {
        // 获取用户id
        User user = userUtil.getuser(request);
        bindParamsVo.setUserId(user.getId());
        String secretId = bindParamsVo.getSecretId();
        String secretKey = bindParamsVo.getSecretKey();
        if(secretId == null || secretKey == null) {
            return ResultUtil.build(500, "secretId和secretKey不能为空!");
        }
        //校验secretId和secretKey
        String sha1 = ShaUtil.getSHA1(secretId, false);
        if(!sha1.equals(secretKey)) {
            return ResultUtil.build(500, "secretId和secretKey不匹配!");
        }
        //根据secretId和secretKey获取用户
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        SecretDTO secretDTO = new SecretDTO();
        BeanUtils.copyProperties(bindParamsVo, secretDTO);
        String jsonString = JSON.toJSONString(secretDTO);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
        String result = restTemplate.postForObject(xieUrl + XieConstant.XIE_CHECK_SECRET, requestEntity, String.class);
        if (result == null) {
            return ResultUtil.build(500, "写作宝服务调用失败");
        }
        JSONObject jsonObject = JSON.parseObject(result);
        Boolean exist = jsonObject.getBoolean("data");
        if (!exist) {
            return ResultUtil.build(500, "该写作宝账号不存在");
        }
        try {
            userDao.bindXie(bindParamsVo);
            User newUser = userDao.selectById(user.getId());
            userUtil.setUser(request,response,newUser);
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
        copyWriting.setPromptId(XieConstant.XIE_REPORT_PROMPT_ID);
        String text = copyWriting.getParams().get("text");
        //去除html标签,保留文字
        text = text.replaceAll("<[^>]*>", "");
        if (text.length() > XieConstant.MAX_INPUT_LENGTH) {
            text = text.substring(0, XieConstant.MAX_INPUT_LENGTH);
        }
        copyWriting.getParams().put("text", text);
        //调用写作宝服务
        final String secretId;
        final String secretKey;
        if (accountPublicUse) {
            secretId = publicXieSecretId;
            secretKey = publicXieSecretKey;
        }else {
            secretId = user.getXie_secret_id();
            secretKey = user.getXie_secret_key();
        }

        RequestBody requestBody = RequestBody.create(JSON.toJSONString(copyWriting), okhttp3.MediaType.get("application/json"));
        Request request = new Request
                .Builder()
                .addHeader("secret-id", secretId)
                .addHeader("secret-key", secretKey)
                .method("POST", requestBody)
                .url(xieUrl+XieConstant.XIE_COPY_WRITING)
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
        if (text == null) {
            SseEmitter sseEmitter = new SseEmitter(300000L);
            try {
                sseEmitter.send(SseEmitter
                        .event()
                        .id("1")
                        .name("error")
                        .data("啊哦,网络开小差了,请稍后再试"));
            } catch (IOException e) {
                log.error("向用户{}发送{}事件时发生异常",user.getId(),"error");
                e.printStackTrace();
            }
            sseEmitter.complete();
            return sseEmitter;
        }
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
            log.error("向用户{}发送{}事件时发生异常",user.getId(),"start");
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
                log.error("向用户{}发送{}事件时发生异常",user.getId(),"message");
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
            log.error("向用户{}发送{}事件时发生异常",user.getId(),"end");
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

        copyWriting.setPromptId(XieConstant.XIE_REPORT_PROMPT_ID);
        String text = copyWriting.getParams().get("text");
        //去除html标签,保留文字
        text = text.replaceAll("<[^>]*>", "");
        if (text.length() > 2900) {
            text = text.substring(0, 2900);
        }
        copyWriting.getParams().put("text", text);

        HttpHeaders headers = getHttpHeaders(user);
        String jsonString = JSON.toJSONString(copyWriting);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonString, headers);
        String result = restTemplate.postForObject(xieUrl+XieConstant.XIE_TITLE, requestEntity, String.class);
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

    @NotNull
    private HttpHeaders getHttpHeaders(User user) {
        final String secretId;
        final String secretKey;
        if (accountPublicUse) {
            secretId = publicXieSecretId;
            secretKey = publicXieSecretKey;
        }else {
            secretId = user.getXie_secret_id();
            secretKey = user.getXie_secret_key();
        }
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("secret-id", secretId);
        headers.set("secret-key", secretKey);
        return headers;
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
