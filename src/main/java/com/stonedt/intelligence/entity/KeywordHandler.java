package com.stonedt.intelligence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class KeywordHandler {

    private Integer id;

    private Integer analysis_id;

    /**
     * 是否处理 0:未处理,1:处理
     */
    private Integer is_hander;
}
