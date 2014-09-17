package com.web.android_sl.domain;

public class Item {
//{"items":[ { "itemID":"57","taskID":"2","oldName":"课程简介-慕课网.flv","newName":"lgdsRBLktdIYQNGIuna5R9I7wluT","category":"video","path1":"http://onlywithyou.qiniudn.com/lgdsRBLktdIYQNGIuna5R9I7wluT","path2":"","title":"","remark":"","sort":"1","download":"","size":"4789070","houzhui":".flv"}, 

	private String oldName;
	private String path;
	private String title;
	private String remark;
	
	public Item(){}
	
	public Item(String oldName,String path,String title,String remark){
		this.oldName=oldName;
		this.path=path;
		this.title=title;
		this.remark=remark;		
	}
	
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	


}
