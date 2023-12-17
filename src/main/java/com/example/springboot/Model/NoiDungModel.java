package com.example.springboot.Model;

public class NoiDungModel {
	private int maNoiDung;
	private String maCT;
	private String maForm;
	private String noiDung;
	
	public NoiDungModel() {
		
	}
	public NoiDungModel(int maNoiDung, String maCT, String maForm, String noiDung) {
		this.maNoiDung=maNoiDung;
		this.maCT=maCT;
		this.maForm=maForm;
		this.noiDung=noiDung;
	}
	public int getMaNoiDung() {
		return maNoiDung;
	}
	public void setMaNoiDung(int maNoiDung) {
		this.maNoiDung = maNoiDung;
	}
	public String getMaCT() {
		return maCT;
	}
	public void setMaCT(String maCT) {
		this.maCT = maCT;
	}
	public String getMaForm() {
		return maForm;
	}
	public void setMaForm(String maForm) {
		this.maForm = maForm;
	}
	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	
}
