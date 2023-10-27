package com.stonedt.intelligence.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName default_opinion_condition
 */
public class DefaultOpinionCondition implements Serializable {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 偏好设置公共id
     */
    private Long opinionConditionId;

    /**
     * 方案id
     */
    private Long projectId;

    /**
     * 时间范围(1:24小时，2:昨天，3:今天，4:3天，5：7天，6：15天，7：30天，8自定义)
     */
    private Integer time;

    /**
     * 精准筛选（0：关闭：1打开）
     */
    private Integer precise;

    /**
     * 情感属性（1：正面 2：中性 3：负面）
可多选，英文逗号分隔
     */
    private String emotion;

    /**
     * 相似文章(0:取消合并 1：合并文章)
     */
    private Integer similar;

    /**
     * 信息排序（1：时间降序 2：时间升序 3：相似数倒序）
     */
    private Integer sort;

    /**
     * 匹配方式（1：全文 2：标题 3：正文）
     */
    private Integer matchs;

    /**
     * 自定义时间
     */
    private String times;

    /**
     * 
     */
    private String timee;

    /**
     * 数据来源
     */
    private String classify;

    /**
     * 网站名称
     */
    private String websitename;

    /**
     * 作者名称
     */
    private String author;

    /**
     * 涉及机构
     */
    private String organization;

    /**
     * 文章分类
     */
    private String categorylable;

    /**
     * 涉及企业
     */
    private String enterprisetype;

    /**
     * 高科技型企业
     */
    private String hightechtype;

    /**
     * 涉及政策
     */
    private String policylableflag;

    /**
     * 数据品类
     */
    private String datasourceType;

    /**
     * 涉及事件
     */
    private String eventindex;

    /**
     * 涉及行业
     */
    private String industryindex;

    /**
     * 涉及省份
     */
    private String province;

    /**
     * 涉及城市
     */
    private String city;

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 偏好设置公共id
     */
    public Long getOpinionConditionId() {
        return opinionConditionId;
    }

    /**
     * 偏好设置公共id
     */
    public void setOpinionConditionId(Long opinionConditionId) {
        this.opinionConditionId = opinionConditionId;
    }

    /**
     * 方案id
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * 方案id
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * 时间范围(1:24小时，2:昨天，3:今天，4:3天，5：7天，6：15天，7：30天，8自定义)
     */
    public Integer getTime() {
        return time;
    }

    /**
     * 时间范围(1:24小时，2:昨天，3:今天，4:3天，5：7天，6：15天，7：30天，8自定义)
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * 精准筛选（0：关闭：1打开）
     */
    public Integer getPrecise() {
        return precise;
    }

    /**
     * 精准筛选（0：关闭：1打开）
     */
    public void setPrecise(Integer precise) {
        this.precise = precise;
    }

    /**
     * 情感属性（1：正面 2：中性 3：负面）
可多选，英文逗号分隔
     */
    public String getEmotion() {
        return emotion;
    }

    /**
     * 情感属性（1：正面 2：中性 3：负面）
可多选，英文逗号分隔
     */
    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    /**
     * 相似文章(0:取消合并 1：合并文章)
     */
    public Integer getSimilar() {
        return similar;
    }

    /**
     * 相似文章(0:取消合并 1：合并文章)
     */
    public void setSimilar(Integer similar) {
        this.similar = similar;
    }

    /**
     * 信息排序（1：时间降序 2：时间升序 3：相似数倒序）
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 信息排序（1：时间降序 2：时间升序 3：相似数倒序）
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 匹配方式（1：全文 2：标题 3：正文）
     */
    public Integer getMatchs() {
        return matchs;
    }

    /**
     * 匹配方式（1：全文 2：标题 3：正文）
     */
    public void setMatchs(Integer matchs) {
        this.matchs = matchs;
    }

    /**
     * 自定义时间
     */
    public String getTimes() {
        return times;
    }

    /**
     * 自定义时间
     */
    public void setTimes(String times) {
        this.times = times;
    }

    /**
     * 
     */
    public String getTimee() {
        return timee;
    }

    /**
     * 
     */
    public void setTimee(String timee) {
        this.timee = timee;
    }

    /**
     * 数据来源
     */
    public String getClassify() {
        return classify;
    }

    /**
     * 数据来源
     */
    public void setClassify(String classify) {
        this.classify = classify;
    }

    /**
     * 网站名称
     */
    public String getWebsitename() {
        return websitename;
    }

    /**
     * 网站名称
     */
    public void setWebsitename(String websitename) {
        this.websitename = websitename;
    }

    /**
     * 作者名称
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 作者名称
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 涉及机构
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * 涉及机构
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * 文章分类
     */
    public String getCategorylable() {
        return categorylable;
    }

    /**
     * 文章分类
     */
    public void setCategorylable(String categorylable) {
        this.categorylable = categorylable;
    }

    /**
     * 涉及企业
     */
    public String getEnterprisetype() {
        return enterprisetype;
    }

    /**
     * 涉及企业
     */
    public void setEnterprisetype(String enterprisetype) {
        this.enterprisetype = enterprisetype;
    }

    /**
     * 高科技型企业
     */
    public String getHightechtype() {
        return hightechtype;
    }

    /**
     * 高科技型企业
     */
    public void setHightechtype(String hightechtype) {
        this.hightechtype = hightechtype;
    }

    /**
     * 涉及政策
     */
    public String getPolicylableflag() {
        return policylableflag;
    }

    /**
     * 涉及政策
     */
    public void setPolicylableflag(String policylableflag) {
        this.policylableflag = policylableflag;
    }

    /**
     * 数据品类
     */
    public String getDatasourceType() {
        return datasourceType;
    }

    /**
     * 数据品类
     */
    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }

    /**
     * 涉及事件
     */
    public String getEventindex() {
        return eventindex;
    }

    /**
     * 涉及事件
     */
    public void setEventindex(String eventindex) {
        this.eventindex = eventindex;
    }

    /**
     * 涉及行业
     */
    public String getIndustryindex() {
        return industryindex;
    }

    /**
     * 涉及行业
     */
    public void setIndustryindex(String industryindex) {
        this.industryindex = industryindex;
    }

    /**
     * 涉及省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 涉及省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 涉及城市
     */
    public String getCity() {
        return city;
    }

    /**
     * 涉及城市
     */
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DefaultOpinionCondition other = (DefaultOpinionCondition) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getOpinionConditionId() == null ? other.getOpinionConditionId() == null : this.getOpinionConditionId().equals(other.getOpinionConditionId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getTime() == null ? other.getTime() == null : this.getTime().equals(other.getTime()))
            && (this.getPrecise() == null ? other.getPrecise() == null : this.getPrecise().equals(other.getPrecise()))
            && (this.getEmotion() == null ? other.getEmotion() == null : this.getEmotion().equals(other.getEmotion()))
            && (this.getSimilar() == null ? other.getSimilar() == null : this.getSimilar().equals(other.getSimilar()))
            && (this.getSort() == null ? other.getSort() == null : this.getSort().equals(other.getSort()))
            && (this.getMatchs() == null ? other.getMatchs() == null : this.getMatchs().equals(other.getMatchs()))
            && (this.getTimes() == null ? other.getTimes() == null : this.getTimes().equals(other.getTimes()))
            && (this.getTimee() == null ? other.getTimee() == null : this.getTimee().equals(other.getTimee()))
            && (this.getClassify() == null ? other.getClassify() == null : this.getClassify().equals(other.getClassify()))
            && (this.getWebsitename() == null ? other.getWebsitename() == null : this.getWebsitename().equals(other.getWebsitename()))
            && (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
            && (this.getOrganization() == null ? other.getOrganization() == null : this.getOrganization().equals(other.getOrganization()))
            && (this.getCategorylable() == null ? other.getCategorylable() == null : this.getCategorylable().equals(other.getCategorylable()))
            && (this.getEnterprisetype() == null ? other.getEnterprisetype() == null : this.getEnterprisetype().equals(other.getEnterprisetype()))
            && (this.getHightechtype() == null ? other.getHightechtype() == null : this.getHightechtype().equals(other.getHightechtype()))
            && (this.getPolicylableflag() == null ? other.getPolicylableflag() == null : this.getPolicylableflag().equals(other.getPolicylableflag()))
            && (this.getDatasourceType() == null ? other.getDatasourceType() == null : this.getDatasourceType().equals(other.getDatasourceType()))
            && (this.getEventindex() == null ? other.getEventindex() == null : this.getEventindex().equals(other.getEventindex()))
            && (this.getIndustryindex() == null ? other.getIndustryindex() == null : this.getIndustryindex().equals(other.getIndustryindex()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getOpinionConditionId() == null) ? 0 : getOpinionConditionId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getTime() == null) ? 0 : getTime().hashCode());
        result = prime * result + ((getPrecise() == null) ? 0 : getPrecise().hashCode());
        result = prime * result + ((getEmotion() == null) ? 0 : getEmotion().hashCode());
        result = prime * result + ((getSimilar() == null) ? 0 : getSimilar().hashCode());
        result = prime * result + ((getSort() == null) ? 0 : getSort().hashCode());
        result = prime * result + ((getMatchs() == null) ? 0 : getMatchs().hashCode());
        result = prime * result + ((getTimes() == null) ? 0 : getTimes().hashCode());
        result = prime * result + ((getTimee() == null) ? 0 : getTimee().hashCode());
        result = prime * result + ((getClassify() == null) ? 0 : getClassify().hashCode());
        result = prime * result + ((getWebsitename() == null) ? 0 : getWebsitename().hashCode());
        result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        result = prime * result + ((getOrganization() == null) ? 0 : getOrganization().hashCode());
        result = prime * result + ((getCategorylable() == null) ? 0 : getCategorylable().hashCode());
        result = prime * result + ((getEnterprisetype() == null) ? 0 : getEnterprisetype().hashCode());
        result = prime * result + ((getHightechtype() == null) ? 0 : getHightechtype().hashCode());
        result = prime * result + ((getPolicylableflag() == null) ? 0 : getPolicylableflag().hashCode());
        result = prime * result + ((getDatasourceType() == null) ? 0 : getDatasourceType().hashCode());
        result = prime * result + ((getEventindex() == null) ? 0 : getEventindex().hashCode());
        result = prime * result + ((getIndustryindex() == null) ? 0 : getIndustryindex().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", createTime=").append(createTime);
        sb.append(", opinionConditionId=").append(opinionConditionId);
        sb.append(", projectId=").append(projectId);
        sb.append(", time=").append(time);
        sb.append(", precise=").append(precise);
        sb.append(", emotion=").append(emotion);
        sb.append(", similar=").append(similar);
        sb.append(", sort=").append(sort);
        sb.append(", matchs=").append(matchs);
        sb.append(", times=").append(times);
        sb.append(", timee=").append(timee);
        sb.append(", classify=").append(classify);
        sb.append(", websitename=").append(websitename);
        sb.append(", author=").append(author);
        sb.append(", organization=").append(organization);
        sb.append(", categorylable=").append(categorylable);
        sb.append(", enterprisetype=").append(enterprisetype);
        sb.append(", hightechtype=").append(hightechtype);
        sb.append(", policylableflag=").append(policylableflag);
        sb.append(", datasourceType=").append(datasourceType);
        sb.append(", eventindex=").append(eventindex);
        sb.append(", industryindex=").append(industryindex);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}