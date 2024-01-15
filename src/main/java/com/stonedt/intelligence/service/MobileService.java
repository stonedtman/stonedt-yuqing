package com.stonedt.intelligence.service;

import com.google.zxing.WriterException;
import com.nimbusds.jose.JOSEException;
import com.stonedt.intelligence.entity.User;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface MobileService {
    void getMobileQRCode(User user, HttpServletResponse response) throws JOSEException, IOException, WriterException;

    RedirectView redirectBy(String uuid, String key);
}
