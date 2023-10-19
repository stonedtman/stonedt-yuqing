/**
  * Copyright 2023 bejson.com 
  */
package com.stonedt.intelligence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 图片列表
 * @author 文轩
 */
@Data
@Schema(name = "Imglist", description = "图片列表")
public class Imglist {
    /**
     * 图片地址
     */
    @Schema(name = "imgurl", description = "图片地址")
    private String imgurl;

}