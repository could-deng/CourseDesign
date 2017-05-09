/**
 * 
 */
package com.example.coursedesign.bean;

import com.activeandroid.annotation.Table;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * @author yuanqiang
 *
 */
public class Country extends BmobObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int country_id;
	private String country_name;
	private String country_img;
	private int continents_id;
	private boolean is_popolar;

	public Country(int country_id, String country_name, String country_img, int continents_id, boolean is_popolar) {
		this.country_id = country_id;
		this.country_name = country_name;
		this.country_img = country_img;
		this.continents_id = continents_id;
		this.is_popolar = is_popolar;
	}

	public int getCountry_id() {
		return country_id;
	}

	public void setCountry_id(int country_id) {
		this.country_id = country_id;
	}

	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	public String getCountry_img() {
		return country_img;
	}

	public void setCountry_img(String country_img) {
		this.country_img = country_img;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getContinents_id() {
		return continents_id;
	}

	public void setContinents_id(int continents_id) {
		this.continents_id = continents_id;
	}

	public boolean is_popolar() {
		return is_popolar;
	}

	public void setIs_popolar(boolean is_popolar) {
		this.is_popolar = is_popolar;
	}
}
