package com.example.springboot.Model;

public class LoaiChungTuModel {
	private String id;
	private String name;
	private String formId;
	public LoaiChungTuModel() {
		
	}
	public LoaiChungTuModel(String id, String name, String formId) {
		super();
		this.id = id;
		this.name = name;
		this.formId = formId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	
}
