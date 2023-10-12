package com.stonedt.intelligence.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.intelligence.dao.UserDao;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.PlatformService;
import com.stonedt.intelligence.util.ResultUtil;
import com.stonedt.intelligence.vo.BindParamsVo;
import com.stonedt.intelligence.vo.CopyWriting;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Set;

/**
 * @author 文轩
 */
@Service
@Slf4j
public class PlatformServiceImpl implements PlatformService {

    private final UserDao userDao;

    private final RestTemplate restTemplate;

    private final OkHttpClient okHttpClient;

    @Value("${platform.nlp.ocr-url}")
    private String nlpOcrUrl;

    @Value("${platform.xie.copy-writing}")
    private String xieCopyWritingUrl;

    public PlatformServiceImpl(UserDao userDao,
                               RestTemplate restTemplate,
                               OkHttpClient okHttpClient) {
        this.userDao = userDao;
        this.restTemplate = restTemplate;
        this.okHttpClient = okHttpClient;
    }


    /**
     * nlp服务绑定
     *
     * @param bindParamsVo 绑定参数
     */
    @Override
    public ResultUtil nlpBind(BindParamsVo bindParamsVo) {

        try {
            userDao.bindNlp(bindParamsVo);
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
     * @return 绑定结果
     */
    @Override
    public ResultUtil xieBind(BindParamsVo bindParamsVo) {
        try {
            userDao.bindXie(bindParamsVo);
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
     * @return 写作宝结果
     */
    @Override
    public SseEmitter xieReport(User user, CopyWriting copyWriting) {
        String text = copyWriting.getParams().get("text");
        //去除html标签,保留文字
        text = text.replaceAll("<[^>]*>", "");
        copyWriting.getParams().put("text", text);
        //调用写作宝服务
        RequestBody requestBody = RequestBody.create(JSON.toJSONString(copyWriting), null);
        Request request = new Request
                .Builder()
                .addHeader("secret-id", user.getXie_secret_id())
                .addHeader("secret-key", user.getXie_secret_key())
                .method("POST", requestBody)
                .url(xieCopyWritingUrl)
                .build();
        EventSource.Factory factory = EventSources.createFactory(okHttpClient);
        SseEmitter sseEmitter = new SseEmitter(300000L);
        EventSourceListener listener = new EventSourceListener() {
            @Override
            public void onClosed(@NotNull EventSource eventSource) {
                super.onClosed(eventSource);
                log.info("用户{}sse连接关闭", user.getId());
            }

            @Override
            public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
                super.onEvent(eventSource, id, type, data);
                try {
                    sseEmitter.send(SseEmitter.event()
                            .id(id)
                            .name(type)
                            .data(data));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                super.onFailure(eventSource, t, response);
                log.error("用户{}sse连接出错", user.getId());
                sseEmitter.complete();
            }

            @Override
            public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
                log.info("用户{}sse连接建立", user.getId());
                super.onOpen(eventSource, response);
            }
        };
        factory.newEventSource(request, listener);
        return sseEmitter;
    }
}
