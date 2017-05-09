/**
 * 
 */
package com.example.coursedesign.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * @author yuanqiang
 *
 */
public class City extends BmobObject implements Serializable{
	private Integer city_id;
	private Integer country_id;
	private String city_name;
	private String city_pic_url;
	private Integer people_gone;
	private String city_info;
	private double latitude;
	private double longtitude;

	private boolean selected;//主要用于适配器，标示是否选中

	public City(Integer city_id, Integer country_id, String city_name, String city_pic_url, Integer people_gone, String city_info, double laititude, double longtitude,boolean selected) {
		this.city_id = city_id;
		this.country_id = country_id;
		this.city_name = city_name;
		this.city_pic_url = city_pic_url;
		this.people_gone = people_gone;
		this.city_info = city_info;
		this.latitude = laititude;
		this.longtitude = longtitude;
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	public Integer getCity_id() {
		return city_id;
	}

	public void setCity_id(Integer city_id) {
		this.city_id = city_id;
	}

	public Integer getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Integer country_id) {
		this.country_id = country_id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getCity_pic_url() {
		return city_pic_url;
	}

	public void setCity_pic_url(String city_pic_url) {
		this.city_pic_url = city_pic_url;
	}

	public Integer getPeople_gone() {
		return people_gone;
	}

	public void setPeople_gone(Integer people_gone) {
		this.people_gone = people_gone;
	}

	public String getCity_info() {
		return city_info;
	}

	public void setCity_info(String city_info) {
		this.city_info = city_info;
	}

	public double getLaititude() {
		return latitude;
	}

	public void setLaititude(double laititude) {
		this.latitude = laititude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}
}
