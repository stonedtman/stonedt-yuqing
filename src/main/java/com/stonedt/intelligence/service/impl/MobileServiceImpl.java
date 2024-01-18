package com.stonedt.intelligence.service.impl;

import com.google.zxing.WriterException;
import com.nimbusds.jose.JOSEException;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.MobileService;
import com.stonedt.intelligence.service.UserService;
import com.stonedt.intelligence.util.QRCodeUtil;
import com.stonedt.intelligence.util.ShaUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author 文轩
 */
@Service
public class MobileServiceImpl implements MobileService {

    @Value("${system.url}")
    private String url;

    @Value("${token.private-key}")
    private String privateKey;

    private final UserService userService;

    private final StringRedisTemplate stringRedisTemplate;




    public MobileServiceImpl(UserService userService,
                             StringRedisTemplate stringRedisTemplate) {
        this.userService = userService;
        this.stringRedisTemplate = stringRedisTemplate;

    }

    @Override
    public void getMobileQRCode(User user, HttpServletResponse response) throws JOSEException, IOException, WriterException {
        String token = userService.getToken(user);
        // uuid
        String uuid = user.getUser_id() + "-" + System.currentTimeMillis();
        // 保存token
        stringRedisTemplate.opsForValue().set(uuid, token, 10, TimeUnit.MINUTES);
        // 根据私钥生成key
        String key = ShaUtil.getSHA1(privateKey + uuid, false);

        String qrCodeUrl = url + "/mobile/uuid/" + uuid + "/" + key;
        // 设置响应流输出类型为图片
        response.setContentType("image/png");
        // 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        // 预览而不是下载
        response.setHeader("Content-Disposition", "inline; filename=qrcode.png");
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("static/assets/images/logo.png");
        // 生成带logo二维码
        QRCodeUtil.encodeQR(qrCodeUrl, 250, response.getOutputStream(), resourceAsStream);
    }

    @Override
    public RedirectView redirectBy(String uuid, String key) {
        // 根据私钥生成key
        String sha1 = ShaUtil.getSHA1(privateKey + uuid, false);
        if (sha1.equals(key)) {
            // 获取token
            String token = stringRedisTemplate.opsForValue().get(uuid);
            // 重定向
            return new RedirectView(url + "/mobile/monitor?token=" + token);
        } else {
            return new RedirectView(url + "/mobile/monitor");
        }
    }
}
