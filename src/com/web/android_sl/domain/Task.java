package com.web.android_sl.domain;

import android.graphics.Bitmap;

public class Task {
	// {"task":[{"icon":"","Hits":"","point":"","category":"1","themeID":"2",
	// "time":"","ispublic":"1","remark":"1","taskName":"1","taskID":"6"}]}
	public Task() {
	}

	public Task(Bitmap icon, String Hits, String point, String category,
			String themeID, String time, String ispublic, String remark,
			String taskName, String taskID) {
		this.icon=icon;
		this.Hits=Hits;
		this.point=point;
		this.category=category;
		this.themeID=themeID;
		this.time=time;
		this.ispublic=ispublic;
		this.remark=remark;
		this.taskName=taskName;
		this.taskID=taskID;
	}

	private Bitmap icon;
	private String Hits;
	private String point;
	private String category;
	private String themeID;
	private String time;
	private String ispublic;
	private String remark;
	private String taskName;
	private String taskID;

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public String getHits() {
		return Hits;
	}

	public void setHits(String hits) {
		Hits = hits;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getThemeID() {
		return themeID;
	}

	public void setThemeID(String themeID) {
		this.themeID = themeID;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getIspublic() {
		return ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}
}
