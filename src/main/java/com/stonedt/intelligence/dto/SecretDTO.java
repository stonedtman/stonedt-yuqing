package com.stonedt.intelligence.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.stonedt.intelligence.util.ShaUtil;
import lombok.Data;

/**
 * secret-id和secret-key
 * @author 文轩
 */
@Data
public class SecretDTO {

    /**
     * secret-id
     */
    private String secretId;

    /**
     * secret-key
     */
    private String secretKey;

    /**
     * 生成
     */
    @JsonIgnore
    public static SecretDTO generate() {
        SecretDTO secretDTO = new SecretDTO();
        // 生成uuid
        String secretId = java.util.UUID.randomUUID().toString();
        // 将secretId进行sha1加密
        String secretKey = ShaUtil.getSHA1(secretId, false);
        secretDTO.setSecretId(secretId);
        secretDTO.setSecretKey(secretKey);
        return secretDTO;
    }


}
