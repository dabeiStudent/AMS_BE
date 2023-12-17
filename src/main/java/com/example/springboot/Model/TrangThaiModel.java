package com.example.springboot.Model;

import java.time.LocalDateTime;
import java.util.Date;

public class TrangThaiModel {
	private int maTrangThaiCT;
	private String maCT;
	private String maTT;
	private String nguoiCapNhat;
	private LocalDateTime thoiGianCapNhat;
	public TrangThaiModel() {
		
	}
	public TrangThaiModel(int maTrangThaiCT, String maCT, String maTT, String nguoiCapNhat, LocalDateTime thoiGianCapNhat) {
		this.maTrangThaiCT = maTrangThaiCT;
		this.maCT = maCT;
		this.maTT = maTT;
		this.nguoiCapNhat = nguoiCapNhat;
		this.thoiGianCapNhat = thoiGianCapNhat;
	}
	public int getMaTrangThaiCT() {
		return maTrangThaiCT;
	}
	public void setMaTrangThaiCT(int maTrangThaiCT) {
		this.maTrangThaiCT = maTrangThaiCT;
	}
	public String getMaCT() {
		return maCT;
	}
	public void setMaCT(String maCT) {
		this.maCT = maCT;
	}
	public String getMaTT() {
		return maTT;
	}
	public void setMaTT(String maTT) {
		this.maTT = maTT;
	}
	public String getNguoiCapNhat() {
		return nguoiCapNhat;
	}
	public void setNguoiCapNhat(String nguoiCapNhat) {
		this.nguoiCapNhat = nguoiCapNhat;
	}
	public LocalDateTime getThoiGianCapNhat() {
		return thoiGianCapNhat;
	}
	public void setThoiGianCapNhat(LocalDateTime thoiGianCapNhat) {
		this.thoiGianCapNhat = thoiGianCapNhat;
	}
	
}
