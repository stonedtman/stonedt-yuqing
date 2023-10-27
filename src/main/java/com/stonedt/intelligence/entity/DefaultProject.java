package com.stonedt.intelligence.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author 文轩
 * @TableName default_project
 */
public class DefaultProject implements Serializable {
    /**
     * 自增id
     */
    private Integer id;

    /**
     * 创建时间
     */
    private Date create_time;

    /**
     * 方案公共id
     */
    private Long project_id;

    /**
     * 方案名
     */
    private String project_name;

    /**
     * 修改时间
     */
    private Date update_ime;

    /**
     * 方案类型（普通1，高级2）
     */
    private Integer project_type;

    /**
     * 方案描述
     */
    private String project_description;

    /**
     * 主体词
     */
    private String subject_word;

    /**
     * 人物词
     */
    private String character_word;

    /**
     * 事件词
     */
    private String event_word;

    /**
     * 地域词
     */
    private String regional_word;

    /**
     * 屏蔽词
     */
    private String stop_word;

    /**
     * 软删除（0：否 1：是）
     */
    private Integer del_wtatus;

    /**
     * 方案组id
     */
    private Long group_id;

    private static final long serialVersionUID = 1L;

    public DefaultProject() {
    }

    public DefaultProject(Integer id, Date create_time, Long project_id, String project_name, Date update_ime, Integer project_type, String project_description, String subject_word, String character_word, String event_word, String regional_word, String stop_word, Integer del_wtatus, Long group_id) {
        this.id = id;
        this.create_time = create_time;
        this.project_id = project_id;
        this.project_name = project_name;
        this.update_ime = update_ime;
        this.project_type = project_type;
        this.project_description = project_description;
        this.subject_word = subject_word;
        this.character_word = character_word;
        this.event_word = event_word;
        this.regional_word = regional_word;
        this.stop_word = stop_word;
        this.del_wtatus = del_wtatus;
        this.group_id = group_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public Date getUpdate_ime() {
        return update_ime;
    }

    public void setUpdate_ime(Date update_ime) {
        this.update_ime = update_ime;
    }

    public Integer getProject_type() {
        return project_type;
    }

    public void setProject_type(Integer project_type) {
        this.project_type = project_type;
    }

    public String getProject_description() {
        return project_description;
    }

    public void setProject_description(String project_description) {
        this.project_description = project_description;
    }

    public String getSubject_word() {
        return subject_word;
    }

    public void setSubject_word(String subject_word) {
        this.subject_word = subject_word;
    }

    public String getCharacter_word() {
        return character_word;
    }

    public void setCharacter_word(String character_word) {
        this.character_word = character_word;
    }

    public String getEvent_word() {
        return event_word;
    }

    public void setEvent_word(String event_word) {
        this.event_word = event_word;
    }

    public String getRegional_word() {
        return regional_word;
    }

    public void setRegional_word(String regional_word) {
        this.regional_word = regional_word;
    }

    public String getStop_word() {
        return stop_word;
    }

    public void setStop_word(String stop_word) {
        this.stop_word = stop_word;
    }

    public Integer getDel_wtatus() {
        return del_wtatus;
    }

    public void setDel_wtatus(Integer del_wtatus) {
        this.del_wtatus = del_wtatus;
    }

    public Long getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Long group_id) {
        this.group_id = group_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultProject that = (DefaultProject) o;
        return Objects.equals(id, that.id) && Objects.equals(create_time, that.create_time) && Objects.equals(project_id, that.project_id) && Objects.equals(project_name, that.project_name) && Objects.equals(update_ime, that.update_ime) && Objects.equals(project_type, that.project_type) && Objects.equals(project_description, that.project_description) && Objects.equals(subject_word, that.subject_word) && Objects.equals(character_word, that.character_word) && Objects.equals(event_word, that.event_word) && Objects.equals(regional_word, that.regional_word) && Objects.equals(stop_word, that.stop_word) && Objects.equals(del_wtatus, that.del_wtatus) && Objects.equals(group_id, that.group_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, create_time, project_id, project_name, update_ime, project_type, project_description, subject_word, character_word, event_word, regional_word, stop_word, del_wtatus, group_id);
    }

    @Override
    public String toString() {
        return "DefaultProject{" +
                "id=" + id +
                ", create_time=" + create_time +
                ", project_id=" + project_id +
                ", project_name='" + project_name + '\'' +
                ", update_ime=" + update_ime +
                ", project_type=" + project_type +
                ", project_description='" + project_description + '\'' +
                ", subject_word='" + subject_word + '\'' +
                ", character_word='" + character_word + '\'' +
                ", event_word='" + event_word + '\'' +
                ", regional_word='" + regional_word + '\'' +
                ", stop_word='" + stop_word + '\'' +
                ", del_wtatus=" + del_wtatus +
                ", group_id=" + group_id +
                '}';
    }
}