package com.stonedt.intelligence.entity;

import java.util.Date;
import java.util.Objects;

/**
 * description: 用户信息实体 <br>
 * date: 2020/4/14 11:25 <br>
 * author: huajiancheng <br>
 * version: 1.0 <br>
 */
public class User implements java.io.Serializable{
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

    private Integer user_type;

    private Integer user_level;

    private Integer wechatflag;

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

    /**
     * 邮箱配置json
     */
    private String mail_json;

    private final static long serialVersionUID = 4L;


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

    public String getMail_json() {
        return mail_json;
    }

    public void setMail_json(String mail_json) {
        this.mail_json = mail_json;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }

    public Integer getUser_level() {
        return user_level;
    }

    public void setUser_level(Integer user_level) {
        this.user_level = user_level;
    }

    public Integer getWechatflag() {
        return wechatflag;
    }

    public void setWechatflag(Integer wechatflag) {
        this.wechatflag = wechatflag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(create_time, user.create_time) && Objects.equals(user_id, user.user_id) && Objects.equals(telephone, user.telephone) && Objects.equals(password, user.password) && Objects.equals(email, user.email) && Objects.equals(end_login_time, user.end_login_time) && Objects.equals(status, user.status) && Objects.equals(username, user.username) && Objects.equals(wechat_number, user.wechat_number) && Objects.equals(openid, user.openid) && Objects.equals(login_count, user.login_count) && Objects.equals(identity, user.identity) && Objects.equals(organization_id, user.organization_id) && Objects.equals(isOnline, user.isOnline) && Objects.equals(user_type, user.user_type) && Objects.equals(user_level, user.user_level) && Objects.equals(wechatflag, user.wechatflag) && Objects.equals(nlp_secret_id, user.nlp_secret_id) && Objects.equals(nlp_secret_key, user.nlp_secret_key) && Objects.equals(nlp_flag, user.nlp_flag) && Objects.equals(xie_secret_id, user.xie_secret_id) && Objects.equals(xie_secret_key, user.xie_secret_key) && Objects.equals(xie_flag, user.xie_flag) && Objects.equals(term_of_validity, user.term_of_validity) && Objects.equals(mail_json, user.mail_json);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, create_time, user_id, telephone, password, email, end_login_time, status, username, wechat_number, openid, login_count, identity, organization_id, isOnline, user_type, user_level, wechatflag, nlp_secret_id, nlp_secret_key, nlp_flag, xie_secret_id, xie_secret_key, xie_flag, term_of_validity, mail_json);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", create_time='" + create_time + '\'' +
                ", user_id=" + user_id +
                ", telephone='" + telephone + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", end_login_time='" + end_login_time + '\'' +
                ", status=" + status +
                ", username='" + username + '\'' +
                ", wechat_number='" + wechat_number + '\'' +
                ", openid='" + openid + '\'' +
                ", login_count=" + login_count +
                ", identity=" + identity +
                ", organization_id='" + organization_id + '\'' +
                ", isOnline=" + isOnline +
                ", user_type=" + user_type +
                ", user_level=" + user_level +
                ", wechatflag=" + wechatflag +
                ", nlp_secret_id='" + nlp_secret_id + '\'' +
                ", nlp_secret_key='" + nlp_secret_key + '\'' +
                ", nlp_flag=" + nlp_flag +
                ", xie_secret_id='" + xie_secret_id + '\'' +
                ", xie_secret_key='" + xie_secret_key + '\'' +
                ", xie_flag=" + xie_flag +
                ", term_of_validity=" + term_of_validity +
                ", mail_json='" + mail_json + '\'' +
                '}';
    }
}
