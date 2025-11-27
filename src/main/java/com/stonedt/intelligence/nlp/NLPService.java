package com.stonedt.intelligence.nlp;

import java.util.List;

public interface NLPService {
    /**
     * 登录接口
     */
     String login = "/api/login";

    /**
     * 词性标注
     */
    String lac ="/api/lac";

    String nlpLogin(String userName, String password) ;

    String nlpLac(List<String> text, Integer batchSize, String secretId, String secretKey, String token);
}
