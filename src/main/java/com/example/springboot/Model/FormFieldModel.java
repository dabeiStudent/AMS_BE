package com.example.springboot.Model;

public class FormFieldModel {
	private String id;
    private String formId;
    private String key;
    private String label;
    private boolean important;
    private String typeInput;
    private int sortOrder;
    public FormFieldModel() {
    	
    }
	public FormFieldModel(String id, String formId, String key, String label, boolean important, String typeInput,
			int sortOrder) {
		super();
		this.id = id;
		this.formId = formId;
		this.key = key;
		this.label = label;
		this.important = important;
		this.typeInput = typeInput;
		this.sortOrder = sortOrder;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isImportant() {
		return important;
	}
	public void setImportant(boolean important) {
		this.important = important;
	}
	public String getTypeInput() {
		return typeInput;
	}
	public void setTypeInput(String typeInput) {
		this.typeInput = typeInput;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
    
}
