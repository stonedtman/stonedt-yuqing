package com.stonedt.intelligence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 相似文章搜索条件
 * @author 文轩
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "SimilarSearchCondition", description = "相似文章搜索条件")
public class SimilarSearchCondition extends SearchCondition{

    /**
     * 标题关键字
     */
    @Schema(name = "titlekeyword", description = "标题关键字")
    private String titlekeyword;
}
