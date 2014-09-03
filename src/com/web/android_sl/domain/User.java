package com.web.android_sl.domain;

public class User {
	// {"code":"000006","data":{"data":{"user":[ {
	// "name":"","QQ":"","phone":"","Email":"","icon":"","introduction":"","motto":"","indentity":"5","theme":[
	// {
	// "themeID":"1","category":"","themeName":"","remark":"","userId":"","icon":"","point":"","ispublic":""}]}]}}}

	public User() {

	}

	public User(String name, String QQ, String phone, String Email,
			String icon, String introduction, String motto, String indentity) {
		this.name = name;
		this.QQ = QQ;
		this.phone = phone;
		this.Email = Email;
		this.icon = icon;
		this.introduction = introduction;
		this.motto = motto;
		this.indentity = indentity;
	}

	private String name;
	private String QQ;
	private String phone;
	private String Email;
	private String icon;
	private String introduction;
	private String motto;
	private String indentity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public String getIndentity() {
		return indentity;
	}

	public void setIndentity(String indentity) {
		this.indentity = indentity;
	}

}
