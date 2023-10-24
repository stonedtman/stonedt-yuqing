package com.stonedt.intelligence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 二维码数据
 * @author 文轩
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "QrcodeData", description = "二维码数据")
public class QrcodeData {

    /**
     * 二维码图片地址
     */
    @Schema(name = "qrcodeUrl", description = "二维码图片地址")
    private String qrcodeUrl;

    /**
     * 二维码场景值
     */
    @Schema(name = "sceneStr", description = "二维码场景值")
    private String sceneStr;
}
