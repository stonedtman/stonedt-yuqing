/**
  * Copyright 2023 bejson.com 
  */
package com.stonedt.intelligence.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author 文轩
 * 分页信息
 */
@Data
@Schema(name = "PageInfo", description = "分页信息")
public class PageInfo<T> {

    /**
     * 数据列表
     */
    @Schema(name = "data", description = "数据列表")
    private List<T> data;
    /**
     * 总页数
     */
    @Schema(name = "totalPage", description = "总页数")
    private Integer totalPage;
    /**
     * 总条数
     */
    @Schema(name = "totalCount", description = "总条数")
    private Integer totalCount;
    /**
     * 当前页
     */
    @Schema(name = "currentPage", description = "当前页")
    private Integer currentPage;


}