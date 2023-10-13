package com.stonedt.intelligence.service;

import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.util.ResultUtil;
import com.stonedt.intelligence.vo.BindParamsVo;
import com.stonedt.intelligence.vo.CopyWriting;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 文轩
 * description: 跨平台服务 <br>
 */
public interface PlatformService {

    /**
     * nlp服务绑定
     *
     * @param bindParamsVo 绑定参数
     * @param request
     */
    ResultUtil nlpBind(BindParamsVo bindParamsVo, HttpServletRequest request);

    /**
     * nlp光学字符识别
     * @param user 用户
     * @param images 图片
     */
    ResultUtil nlpOcr(User user, MultipartFile images) throws IOException;

    /**
     * 写作宝服务绑定
     *
     * @param bindParamsVo 绑定参数
     * @param request
     * @return 绑定结果
     */
    ResultUtil xieBind(BindParamsVo bindParamsVo, HttpServletRequest request);

    /**
     * 写作宝服务调用
     * @param user 用户
     * @param copyWriting 写作宝参数
     * @return 写作宝结果
     */
    SseEmitter xieReport(User user, CopyWriting copyWriting);

    /**
     * 写作宝标题生成
     * @param user 用户
     * @param copyWriting 写作宝参数
     * @return 标题
     */
    ResultUtil xieReportTitle(User user, CopyWriting copyWriting);
}
