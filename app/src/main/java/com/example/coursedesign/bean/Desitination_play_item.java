/**
 * 
 */
package com.example.coursedesign.bean;

/**
 * @author yuanqiang
 *
 */
public class Desitination_play_item {
	private int play_icon;
	private String play_info;
	public Desitination_play_item(int play_icon, String play_info) {
		super();
		this.play_icon = play_icon;
		this.play_info = play_info;
	}
	public int getPlay_icon() {
		return play_icon;
	}
	public void setPlay_icon(int play_icon) {
		this.play_icon = play_icon;
	}
	public String getPlay_info() {
		return play_info;
	}
	public void setPlay_info(String play_info) {
		this.play_info = play_info;
	}
	
}
