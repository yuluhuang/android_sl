package com.web.android_sl.domain;

import android.graphics.Bitmap;

public class Theme {
	// {
	// "themeID":"1","category":"","themeName":"","remark":"","userId":"","icon":"","point":"","ispublic":""}

	public Theme() {
	}

	public Theme(String themeID, String category, String themeName,
			String remark, String userId, Bitmap icon, String point,
			String ispublic) {
		this.themeID = themeID;
		this.category = category;
		this.themeName = themeName;
		this.remark = remark;
		this.userId = userId;
		this.icon = icon;
		this.point = point;
		this.ispublic = ispublic;
	}

	private String themeID;
	private String category;
	private String themeName;
	private String remark;
	private String userId;
	private Bitmap icon;
	private String point;
	private String ispublic;

	public String getThemeID() {
		return themeID;
	}

	public void setThemeID(String themeID) {
		this.themeID = themeID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Bitmap getIcon() {
		return icon;
	}

	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getIspublic() {
		return ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}

}
