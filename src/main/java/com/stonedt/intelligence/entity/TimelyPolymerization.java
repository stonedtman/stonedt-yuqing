
package com.stonedt.intelligence.entity;

import java.io.Serializable;

/**
 * 
 * @author wangyi
 *
 */

public class TimelyPolymerization implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String create_time;
	private Integer type;
	private String type_name;
	private String value;
	private String icon;
	private Integer status;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public TimelyPolymerization(Integer id, String create_time, Integer type, String type_name, String value,
			String icon, Integer status) {
		super();
		this.id = id;
		this.create_time = create_time;
		this.type = type;
		this.type_name = type_name;
		this.value = value;
		this.icon = icon;
		this.status = status;
	}
	public TimelyPolymerization() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	
}
