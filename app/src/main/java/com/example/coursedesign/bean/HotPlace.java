/**
 * 
 */
package com.example.coursedesign.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 热门目的地bean
 * @author yuanqiang
 *
 */
public class HotPlace implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;
	private String tips;
	private ArrayList<Place> list_place;
	public HotPlace(String title, String tips, ArrayList<Place> list_place) {
		super();
		this.title = title;
		this.tips = tips;
		this.list_place = list_place;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public ArrayList<Place> getList_place() {
		return list_place;
	}
	public void setList_place(ArrayList<Place> list_place) {
		this.list_place = list_place;
	}
	
}
