package com.stonedt.intelligence.entity;

import lombok.Data;

@Data
public class SystemLogEntity {
 
	private int id;
	private String user_browser;//用户浏览器
	private Integer user_id;//用户id
	private String user_browser_version;//用户浏览器版本
	private String operatingSystem;//操作系统
	private String username;//用户名
	private String loginip;//登陆ip
	private String module;//模块
	private String submodule;//子模块
	private String type;//类型
	private String createtime;//创建时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_browser() {
		return user_browser;
	}
	public void setUser_browser(String user_browser) {
		this.user_browser = user_browser;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getUser_browser_version() {
		return user_browser_version;
	}
	public void setUser_browser_version(String user_browser_version) {
		this.user_browser_version = user_browser_version;
	}
	public String getOperatingSystem() {
		return operatingSystem;
	}
	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLoginip() {
		return loginip;
	}
	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getSubmodule() {
		return submodule;
	}
	public void setSubmodule(String submodule) {
		this.submodule = submodule;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public SystemLogEntity(int id, String user_browser, Integer user_id, String user_browser_version,
			String operatingSystem, String username, String loginip, String module, String submodule, String type,
			String createtime) {
		super();
		this.id = id;
		this.user_browser = user_browser;
		this.user_id = user_id;
		this.user_browser_version = user_browser_version;
		this.operatingSystem = operatingSystem;
		this.username = username;
		this.loginip = loginip;
		this.module = module;
		this.submodule = submodule;
		this.type = type;
		this.createtime = createtime;
	}
	public SystemLogEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
	
}
