package com.stonedt.intelligence.util;



import com.alibaba.fastjson.JSON;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.stonedt.intelligence.dto.UserDTO;
import com.stonedt.intelligence.entity.User;

import java.text.ParseException;
import java.util.Map;

/**
 * JWT工具类
 * @author 文轩
 */
public class JWTUtils {

    /**
     *  创建jwt
     */
    public static String createJWT(Map map,String privateKey) throws Exception {
        //头部
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();
        //载荷部分
        Payload payload = new Payload(map);
        //签名部分
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        JWSSigner jwsSigner = new MACSigner(privateKey);
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    /**
     *  创建jwt
     */
    public static String createJWT(String jsonString,String privateKey) throws Exception {
        //头部
        JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();
        //载荷部分
        Payload payload = new Payload(jsonString);
        //签名部分
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        JWSSigner jwsSigner = new MACSigner(privateKey);
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }

    /**
     *  解密
     *    作用:查询是否是系统自己生成的jwt
     */
    public static boolean decode(String jwt,String privateKey) throws JOSEException {
        //解密jwt
        JWSObject parse = null;
        try {
            parse = JWSObject.parse(jwt);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        //传入秘钥
        JWSVerifier jwsVerifier = null;
        try {
            jwsVerifier = new MACVerifier(privateKey);
        } catch (JOSEException e) {
            e.printStackTrace();
            return false;
        }
        //解锁
        return parse.verify(jwsVerifier);
    }

    /**
     *  通过jwt字符串获取载荷信息
     */
    public static Map<String, Object> getPayLoad(String jwt) throws Exception {
        JWSObject parse = JWSObject.parse(jwt);
        return parse.getPayload().toJSONObject();
    }

    /**
     * 通过jwt字符串获取载荷信息
     * @param jwt
     * @return
     * @throws Exception
     */

    public static UserDTO getUser(String jwt) throws Exception {
        Map<String, Object> payLoad = getPayLoad(jwt);
        return JSON.parseObject(JSON.toJSONString(payLoad), UserDTO.class);
    }

    /**
     * 通过jwt字符串获取载荷实体
     */
    public static<T> T getEntity(String jwt,Class<T> clazz) throws Exception {
        //获取载荷原始信息
        String string = JWSObject.parse(jwt).getPayload().toString();
        //将载荷信息转换为实体对象
        return JSON.parseObject(string, clazz);
    }



}
