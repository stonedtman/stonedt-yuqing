package com.stonedt.intelligence.nlp.impl;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;


import com.stonedt.intelligence.nlp.NLPService;
import com.stonedt.intelligence.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

import java.awt.*;
import java.util.List;

import static org.apache.poi.poifs.crypt.HashAlgorithm.md5;


@Service
public class NLPServiceImpl implements NLPService {
    private static final Logger log = LoggerFactory.getLogger(NLPServiceImpl.class);


    @Value("${nlp.service.url}")
    private String url;

    /**
     * U3RvbmVkdCwxMjM=
     * @param userName
     * @param password
     * @return
     */
    @Override
    public String nlpLogin(String userName, String password) {
        Base64.Encoder encoder = Base64.getEncoder();
        String s = encoder.encodeToString(password.getBytes());

        Map<String,Object> map  =new HashMap<>();
        map.put("username",userName);
        map.put("password",s);
        map.put("flag",1);
        HttpRequest post = HttpRequest.post(url+login);
        post.header(Header.CONTENT_TYPE,"application/json");
        post.body(JSONUtil.toJsonStr(map));
        try {
            log.info("远程调用:"+url+login);
            HttpResponse execute = post.execute();
            String body = execute.body();
            return body;
        }catch (Exception e){
            log.error("远程调用:"+url+login+"失败;失败原因:"+e.getMessage(),e);
        }

        return "";
    }

    @Override
    public String nlpLac(List<String> text, Integer batchSize,String secretId,String secretKey,String token) {

        HttpRequest post = HttpRequest.post(url+lac);
        post.header(Header.CONTENT_TYPE,"application/json");
        post.header("Secret-Id",secretId);
        post.header("Secret-Key",secretKey);
        post.header("Token",token);
        Map<String,Object> map  =new HashMap<>();
        map.put("batch_size",batchSize);
        map.put("text",text);
        post.body(JSONUtil.toJsonStr(map));

        try {
            log.info("远程调用:"+url+lac);
            HttpResponse execute = post.execute();
            String body = execute.body();
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if(jsonObject.getStr("code").equals("200")){
                return jsonObject.getStr("results");
            }

        }catch (Exception e){
            log.error("远程调用:"+url+lac+"失败;失败原因:"+e.getMessage(),e);
        }
        return "";
    }



    public static void  main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("接种疫苗");
        list.add("你好");
        list.add("你们");
        list.add("因为");
        list.add("所有");
        list.add("表示");
        NLPServiceImpl nlpService = new NLPServiceImpl();
        String result = nlpService.nlpLogin("", "");
        JSONObject jsonObject = JSONUtil.parseObj(result);
        String secretId = jsonObject.getStr("secret-id");
        String secretKey = jsonObject.getStr("secret-key");
        String token = jsonObject.getStr("token");
        String s1 = nlpService.nlpLac(list, 100, secretId, secretKey, token);
        if(StringUtils.isBlank(s1)){

        }
        JSONArray objects = JSONUtil.parseArray(s1);
        List<String> results = new ArrayList<>();
        for (Object object : objects) {
            JSONObject json = (JSONObject) object;
            List<String> tag = json.getJSONArray("tag").toList(String.class);
            List<String> word = json.getJSONArray("word").toList(String.class);
//            for (int i = 0; i <tag.size() ; i++) {
//                if(word.get(i).equals("v")){
//
//                }
//
//            }
            if(tag.contains("n")){
                results.add(StringUtils.join(word,""));
            }

        }
        System.out.println(results);
    }
}
