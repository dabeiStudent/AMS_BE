package com.example.springboot.Model;

public class ActionModel {
	private String id;
	private String actionName;
	public ActionModel() {
		
	}
	
	public ActionModel(String id, String actionName) {
		super();
		this.id = id;
		this.actionName = actionName;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
}
