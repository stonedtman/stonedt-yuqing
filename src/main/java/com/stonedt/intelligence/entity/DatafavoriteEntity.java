package com.stonedt.intelligence.entity;

import lombok.Data;
@Data
public class DatafavoriteEntity {
	private int id;
	private String title;
	private String article_public_id;
	private String publish_time;
	private Long user_id;
	private String favoritetime;
	private int status;
	private int emotionalIndex;
	private Long projectid;
	private Long groupid;
	private String source_name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArticle_public_id() {
		return article_public_id;
	}
	public void setArticle_public_id(String article_public_id) {
		this.article_public_id = article_public_id;
	}
	public String getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getFavoritetime() {
		return favoritetime;
	}
	public void setFavoritetime(String favoritetime) {
		this.favoritetime = favoritetime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getEmotionalIndex() {
		return emotionalIndex;
	}
	public void setEmotionalIndex(int emotionalIndex) {
		this.emotionalIndex = emotionalIndex;
	}
	public Long getProjectid() {
		return projectid;
	}
	public void setProjectid(Long projectid) {
		this.projectid = projectid;
	}
	public Long getGroupid() {
		return groupid;
	}
	public void setGroupid(Long groupid) {
		this.groupid = groupid;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public DatafavoriteEntity(int id, String title, String article_public_id, String publish_time, Long user_id,
			String favoritetime, int status, int emotionalIndex, Long projectid, Long groupid, String source_name) {
		super();
		this.id = id;
		this.title = title;
		this.article_public_id = article_public_id;
		this.publish_time = publish_time;
		this.user_id = user_id;
		this.favoritetime = favoritetime;
		this.status = status;
		this.emotionalIndex = emotionalIndex;
		this.projectid = projectid;
		this.groupid = groupid;
		this.source_name = source_name;
	}
	public DatafavoriteEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
}
