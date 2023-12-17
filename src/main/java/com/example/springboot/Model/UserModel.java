package com.example.springboot.Model;

public class UserModel {
	private String id;
	private String userName;
	private String fullName;
	private String passWord;
	public UserModel() {
		
	}
	public UserModel(String id, String userName, String fullName, String passWord) {
		super();
		this.id = id;
		this.userName = userName;
		this.fullName = fullName;
		this.passWord = passWord;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
}
