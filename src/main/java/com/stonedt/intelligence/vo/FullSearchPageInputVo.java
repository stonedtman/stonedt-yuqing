package com.stonedt.intelligence.vo;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 全文检索页面查询入参
 *
 * @author 文轩
 */
@Data
public class FullSearchPageInputVo {
    private Integer menuStyle;
    private Integer pageSize;
    private Integer full_poly;
    private String fulltype;
    private String searchword;
    private String sourcename;
    private Integer page;
}
