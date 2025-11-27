package com.stonedt.intelligence.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ArticleRead {

    private Integer id;
    private String aid;
    private Integer user_id;
    private Date create_time;
    private Date update_time;
    private String publish_time;
    private String event_label;

}