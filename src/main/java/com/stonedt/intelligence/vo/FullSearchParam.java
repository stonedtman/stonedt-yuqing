package com.stonedt.intelligence.vo;

import lombok.Data;

import java.util.List;
import java.util.Objects;

/**
* <p>搜索接口参数</p>
* <p>Title: FullSearchParam</p>  
* <p>Description: </p>  
* @author Mapeng 
* @date Jun 23, 2020
 */
public class FullSearchParam {
	
	private Integer timeType;
	private Integer mergeType;
	private String matchMethod;
	
	private Integer pageNum = 1;
	private Integer pageSize = 25;
	private String totalCount;
	private String totalPage;
	private String article_public_idstr;
	private String searchWord;
	private String classify;
	private String startTime;
	private String endTime;
	private Integer sortType; //信息排序
	private String emotions; //情感
	private Integer matchType; //匹配方式
	
	private String searchParam;
	private String topic;
	private String source_name;
	private String timetype;
	private String rtype;
	private String website_id;
	private Integer establish = 0; //企业成立时间
	private Integer status = 0;
	private Integer publish = 0;
	
	private String matchingmode;
	private String kinds;
	private String times;
	private String timee;




	//筛选项
	private String city;
	private String eventIndex;
	private String industryIndex;
	private String province;
	private Integer similar;
	private Integer searchType;
	private List<String> emotionalIndex;
	private Integer page;


	public FullSearchParam() {
	}

	public FullSearchParam(Integer timeType, Integer mergeType, String matchMethod, Integer pageNum, Integer pageSize, String totalCount, String totalPage, String article_public_idstr, String searchWord, String classify, String startTime, String endTime, Integer sortType, String emotions, Integer matchType, String searchParam, String topic, String source_name, String timetype, String rtype, String website_id, Integer establish, Integer status, Integer publish, String matchingmode, String kinds, String times, String timee, String city, String eventIndex, String industryIndex, String province, Integer similar, Integer searchType, List<String> emotionalIndex, Integer page) {
		this.timeType = timeType;
		this.mergeType = mergeType;
		this.matchMethod = matchMethod;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.article_public_idstr = article_public_idstr;
		this.searchWord = searchWord;
		this.classify = classify;
		this.startTime = startTime;
		this.endTime = endTime;
		this.sortType = sortType;
		this.emotions = emotions;
		this.matchType = matchType;
		this.searchParam = searchParam;
		this.topic = topic;
		this.source_name = source_name;
		this.timetype = timetype;
		this.rtype = rtype;
		this.website_id = website_id;
		this.establish = establish;
		this.status = status;
		this.publish = publish;
		this.matchingmode = matchingmode;
		this.kinds = kinds;
		this.times = times;
		this.timee = timee;
		this.city = city;
		this.eventIndex = eventIndex;
		this.industryIndex = industryIndex;
		this.province = province;
		this.similar = similar;
		this.searchType = searchType;
		this.emotionalIndex = emotionalIndex;
		this.page = page;
	}

	public Integer getTimeType() {
		return timeType;
	}

	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}

	public Integer getMergeType() {
		return mergeType;
	}

	public void setMergeType(Integer mergeType) {
		this.mergeType = mergeType;
	}

	public String getMatchMethod() {
		return matchMethod;
	}

	public void setMatchMethod(String matchMethod) {
		this.matchMethod = matchMethod;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public String getArticle_public_idstr() {
		return article_public_idstr;
	}

	public void setArticle_public_idstr(String article_public_idstr) {
		this.article_public_idstr = article_public_idstr;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

	public String getEmotions() {
		return emotions;
	}

	public void setEmotions(String emotions) {
		this.emotions = emotions;
	}

	public Integer getMatchType() {
		return matchType;
	}

	public void setMatchType(Integer matchType) {
		this.matchType = matchType;
	}

	public String getSearchParam() {
		return searchParam;
	}

	public void setSearchParam(String searchParam) {
		this.searchParam = searchParam;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public String getTimetype() {
		return timetype;
	}

	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

	public String getRtype() {
		return rtype;
	}

	public void setRtype(String rtype) {
		this.rtype = rtype;
	}

	public String getWebsite_id() {
		return website_id;
	}

	public void setWebsite_id(String website_id) {
		this.website_id = website_id;
	}

	public Integer getEstablish() {
		return establish;
	}

	public void setEstablish(Integer establish) {
		this.establish = establish;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPublish() {
		return publish;
	}

	public void setPublish(Integer publish) {
		this.publish = publish;
	}

	public String getMatchingmode() {
		return matchingmode;
	}

	public void setMatchingmode(String matchingmode) {
		this.matchingmode = matchingmode;
	}

	public String getKinds() {
		return kinds;
	}

	public void setKinds(String kinds) {
		this.kinds = kinds;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getTimee() {
		return timee;
	}

	public void setTimee(String timee) {
		this.timee = timee;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEventIndex() {
		return eventIndex;
	}

	public void setEventIndex(String eventIndex) {
		this.eventIndex = eventIndex;
	}

	public String getIndustryIndex() {
		return industryIndex;
	}

	public void setIndustryIndex(String industryIndex) {
		this.industryIndex = industryIndex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getSimilar() {
		return similar;
	}

	public void setSimilar(Integer similar) {
		this.similar = similar;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public List<String> getEmotionalIndex() {
		return emotionalIndex;
	}

	public void setEmotionalIndex(List<String> emotionalIndex) {
		this.emotionalIndex = emotionalIndex;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FullSearchParam that = (FullSearchParam) o;
		return Objects.equals(timeType, that.timeType) && Objects.equals(mergeType, that.mergeType) && Objects.equals(matchMethod, that.matchMethod) && Objects.equals(pageNum, that.pageNum) && Objects.equals(pageSize, that.pageSize) && Objects.equals(totalCount, that.totalCount) && Objects.equals(totalPage, that.totalPage) && Objects.equals(article_public_idstr, that.article_public_idstr) && Objects.equals(searchWord, that.searchWord) && Objects.equals(classify, that.classify) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(sortType, that.sortType) && Objects.equals(emotions, that.emotions) && Objects.equals(matchType, that.matchType) && Objects.equals(searchParam, that.searchParam) && Objects.equals(topic, that.topic) && Objects.equals(source_name, that.source_name) && Objects.equals(timetype, that.timetype) && Objects.equals(rtype, that.rtype) && Objects.equals(website_id, that.website_id) && Objects.equals(establish, that.establish) && Objects.equals(status, that.status) && Objects.equals(publish, that.publish) && Objects.equals(matchingmode, that.matchingmode) && Objects.equals(kinds, that.kinds) && Objects.equals(times, that.times) && Objects.equals(timee, that.timee) && Objects.equals(city, that.city) && Objects.equals(eventIndex, that.eventIndex) && Objects.equals(industryIndex, that.industryIndex) && Objects.equals(province, that.province) && Objects.equals(similar, that.similar) && Objects.equals(searchType, that.searchType) && Objects.equals(emotionalIndex, that.emotionalIndex) && Objects.equals(page, that.page);
	}

	@Override
	public int hashCode() {
		return Objects.hash(timeType, mergeType, matchMethod, pageNum, pageSize, totalCount, totalPage, article_public_idstr, searchWord, classify, startTime, endTime, sortType, emotions, matchType, searchParam, topic, source_name, timetype, rtype, website_id, establish, status, publish, matchingmode, kinds, times, timee, city, eventIndex, industryIndex, province, similar, searchType, emotionalIndex, page);
	}

	@Override
	public String toString() {
		return "FullSearchParam{" +
				"timeType=" + timeType +
				", mergeType=" + mergeType +
				", matchMethod='" + matchMethod + '\'' +
				", pageNum=" + pageNum +
				", pageSize=" + pageSize +
				", totalCount='" + totalCount + '\'' +
				", totalPage='" + totalPage + '\'' +
				", article_public_idstr='" + article_public_idstr + '\'' +
				", searchWord='" + searchWord + '\'' +
				", classify='" + classify + '\'' +
				", startTime='" + startTime + '\'' +
				", endTime='" + endTime + '\'' +
				", sortType=" + sortType +
				", emotions='" + emotions + '\'' +
				", matchType=" + matchType +
				", searchParam='" + searchParam + '\'' +
				", topic='" + topic + '\'' +
				", source_name='" + source_name + '\'' +
				", timetype='" + timetype + '\'' +
				", rtype='" + rtype + '\'' +
				", website_id='" + website_id + '\'' +
				", establish=" + establish +
				", status=" + status +
				", publish=" + publish +
				", matchingmode='" + matchingmode + '\'' +
				", kinds='" + kinds + '\'' +
				", times='" + times + '\'' +
				", timee='" + timee + '\'' +
				", city='" + city + '\'' +
				", eventIndex='" + eventIndex + '\'' +
				", industryIndex='" + industryIndex + '\'' +
				", province='" + province + '\'' +
				", similar=" + similar +
				", searchType=" + searchType +
				", emotionalIndex=" + emotionalIndex +
				", page=" + page +
				'}';
	}
}
