package com.stonedt.intelligence.entity;

import java.util.Date;

/**
 * description: 用户信息实体 <br>
 * date: 2020/4/14 11:25 <br>
 * author: huajiancheng <br>
 * version: 1.0 <br>
 */
public class User {
    private Integer id;
    private String create_time;
    private Long user_id;
    private String telephone;
    private String password;
    private String email;
    private String end_login_time;
    private Integer status;
    private String username;
    private String wechat_number;
    private String openid;
    private Integer login_count;
    private Integer identity;
    private String organization_id;
    private Integer isOnline;

    /**
     * nlp服务绑定id
     */
    private String nlp_secret_id;

    /**
     * nlp服务绑定key
     */
    private String nlp_secret_key;

    /**
     * nlp服务绑定状态
     */
    private Integer nlp_flag;

    /**
     * 写作宝服务绑定id
     */
    private String xie_secret_id;

    /**
     * 写作宝服务绑定key
     */
    private String xie_secret_key;

    /**
     * 写作宝服务绑定状态
     */
    private Integer xie_flag;

    /**
     * 账号有效期
     */
    private Date term_of_validity;

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public User() {
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEnd_login_time() {
        return end_login_time;
    }

    public void setEnd_login_time(String end_login_time) {
        this.end_login_time = end_login_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWechat_number() {
        return wechat_number;
    }

    public void setWechat_number(String wechat_number) {
        this.wechat_number = wechat_number;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getLogin_count() {
        return login_count;
    }

    public void setLogin_count(Integer login_count) {
        this.login_count = login_count;
    }

    public Integer getIdentity() {
        return identity;
    }

    public void setIdentity(Integer identity) {
        this.identity = identity;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getNlp_secret_id() {
        return nlp_secret_id;
    }

    public void setNlp_secret_id(String nlp_secret_id) {
        this.nlp_secret_id = nlp_secret_id;
    }

    public String getNlp_secret_key() {
        return nlp_secret_key;
    }

    public void setNlp_secret_key(String nlp_secret_key) {
        this.nlp_secret_key = nlp_secret_key;
    }

    public Integer getNlp_flag() {
        return nlp_flag;
    }

    public void setNlp_flag(Integer nlp_flag) {
        this.nlp_flag = nlp_flag;
    }

    public String getXie_secret_id() {
        return xie_secret_id;
    }

    public void setXie_secret_id(String xie_secret_id) {
        this.xie_secret_id = xie_secret_id;
    }

    public String getXie_secret_key() {
        return xie_secret_key;
    }

    public void setXie_secret_key(String xie_secret_key) {
        this.xie_secret_key = xie_secret_key;
    }

    public Integer getXie_flag() {
        return xie_flag;
    }

    public void setXie_flag(Integer xie_flag) {
        this.xie_flag = xie_flag;
    }

    public Date getTerm_of_validity() {
        return term_of_validity;
    }

    public void setTerm_of_validity(Date term_of_validity) {
        this.term_of_validity = term_of_validity;
    }
}
