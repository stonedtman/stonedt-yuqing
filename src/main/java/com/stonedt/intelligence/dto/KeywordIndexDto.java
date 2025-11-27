package com.stonedt.intelligence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KeywordIndexDto implements Serializable {

    private Integer trend;

    private Integer count;

    private String index;

    private String keyword;

    private Integer value_chain;
}
