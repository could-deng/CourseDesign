/**
 * 
 */
package com.example.coursedesign.bean;

import java.io.Serializable;

/**
 * 目的地（图、介绍、地名）
 * @author yuanqiang
 *
 */
public class Place implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String info;
	private String url;
	public Place(String name, String info, String url) {
		super();
		this.name = name;
		this.info = info;
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
