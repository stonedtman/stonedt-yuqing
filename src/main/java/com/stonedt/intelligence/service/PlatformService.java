package com.stonedt.intelligence.service;

import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.util.ResultUtil;
import com.stonedt.intelligence.vo.BindParamsVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 文轩
 * description: 跨平台服务 <br>
 */
public interface PlatformService {

    /**
     * nlp服务绑定
     * @param bindParamsVo 绑定参数
     */
    ResultUtil nlpBind(BindParamsVo bindParamsVo);

    /**
     * nlp光学字符识别
     * @param user 用户
     * @param images 图片
     */
    ResultUtil nlpOcr(User user, MultipartFile images);
}
