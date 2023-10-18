package com.stonedt.intelligence.service;

import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.util.ResultUtil;
import com.stonedt.intelligence.vo.BindParamsVo;
import com.stonedt.intelligence.vo.CopyWriting;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

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
    ResultUtil nlpOcr(User user, MultipartFile images,String imageUrl) throws IOException;


    /**
     * nlp图像识别
     * @param user 用户
     * @param images 图片
     * @param imageUrl 图片地址
     * @return 图像识别结果
     */
    ResultUtil nlpImage(User user, MultipartFile images, String imageUrl)throws IOException;

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
     *
     * @param user        用户
     * @param copyWriting 写作宝参数
     * @param articleId
     * @return 写作宝结果
     */
    SseEmitter xieReport(User user, CopyWriting copyWriting,String articleId);


    /**
     * 写作宝服务调用
     *
     * @param user        用户
     * @param articleId   文章id
     * @param projectId   项目id
     * @param relatedword 关键词
     * @param publishTime 发布时间
     * @param title
     * @return 写作宝结果
     */
    SseEmitter xieReport(User user, String articleId, Long projectId, String relatedword, String publishTime,String title);

    /**
     * 写作宝标题生成
     *
     * @param user        用户
     * @param copyWriting 写作宝参数
     * @param articleId
     * @return 标题
     */
    ResultUtil xieReportTitle(User user, CopyWriting copyWriting, String articleId);

    /**
     * 获取最新公告
     * @return 最新公告
     */
    ResultUtil getNewNotice();

    Map<String,Object> getNewSynthesize();
}
