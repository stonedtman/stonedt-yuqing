package com.stonedt.intelligence.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Analysis {
	
	private Integer id;
	private String create_time;
	private Long analysis_id;
	private Long project_id;
	private Integer time_period;
	private String data_overview;
	private String relative_news;
	private String emotional_proportion;
	private String plan_word_hit;
	private String keyword_emotion_trend;
	private String hot_event_ranking;
	private String highword_cloud;
	private String keyword_index;
	private String media_activity_analysis;
	private String hot_spot_ranking;
	private String data_source_distribution;
	private String data_source_analysis;
	private String keyword_exposure_rank;
	private String selfmedia_volume_rank;
	private String ner;
	private String category_rank;

	private String industrial_distribution;//行业分布统计
	private String event_statistics;//事件统计

	private Date CreateTime;
	private String updateTime;
	private Long AnalysisId;

	private Long ProjectId;
	private String RelativeNews;
	private String KeywordIndex;
	private String EmotionalProportion;
	private String PlanWordHit;
	private String PopularInformation;
	private String PopularEvent;
	private String HotCompany;
	private String HotPeople;
	private String HotSpot;
	private Integer TimePeriod;
	private String keyword_emotion_statistical;

	private Boolean isNeedRefresh;
	

	
	
	
}
