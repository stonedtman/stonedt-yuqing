/**
  * Copyright 2023 bejson.com 
  */
package com.stonedt.intelligence.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 文轩
 * 附加信息
 */
@Data
@Schema(name = "Extend_string_one", description = "附加信息")
public class Extend_string_one {

    /**
     * 视频地址
     */
    @Schema(name = "vediourl", description = "视频地址")
    private String vediourl;

    /**
     * 视频缩略图地址
     */
    @Schema(name = "videothumbnailurl", description = "视频缩略图地址")
    private String videoorientationurl;
    /**
     * 图片地址列表
     */
    @Schema(name = "imglist", description = "图片地址列表")
    private List<Imglist> imglist;

}