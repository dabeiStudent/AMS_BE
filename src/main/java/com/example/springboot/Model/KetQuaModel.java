package com.example.springboot.Model;

import java.time.LocalDateTime;
import java.util.Date;

public class KetQuaModel {
	private int maKetQua;
	private String maCT;
	private int bac;
	private String batBuoc;
	private Integer ketQua;
	private String nguoiDuyet;
	private LocalDateTime thoiGianDuyet;
	public KetQuaModel() {
		
	}
	public KetQuaModel(int maKetQua, String maCT, int bac,String batBuoc, Integer ketQua, String nguoiDuyet, LocalDateTime thoiGianDuyet) {
		this.maKetQua=maKetQua;
		this.maCT=maCT;
		this.bac=bac;
		this.batBuoc=batBuoc;
		this.ketQua=ketQua;
		this.nguoiDuyet=nguoiDuyet;
		this.thoiGianDuyet=thoiGianDuyet;
	}
	public int getMaKetQua() {
		return maKetQua;
	}
	public void setMaKetQua(int maKetQua) {
		this.maKetQua = maKetQua;
	}
	public String getMaCT() {
		return maCT;
	}
	public void setMaCT(String maCT) {
		this.maCT = maCT;
	}
	public int getBac() {
		return bac;
	}
	public void setBac(int bac) {
		this.bac = bac;
	}
	public String getBatBuoc() {
		return batBuoc;
	}
	public void setBatBuoc(String batBuoc) {
		this.batBuoc = batBuoc;
	}
	public Integer getKetQua() {
		return ketQua;
	}
	public void setKetQua(Integer ketQua) {
		this.ketQua = ketQua;
	}
	public String getNguoiDuyet() {
		return nguoiDuyet;
	}
	public void setNguoiDuyet(String nguoiDuyet) {
		this.nguoiDuyet = nguoiDuyet;
	}
	public LocalDateTime getThoiGianDuyet() {
		return thoiGianDuyet;
	}
	public void setThoiGianDuyet(LocalDateTime thoiGianDuyet) {
		this.thoiGianDuyet = thoiGianDuyet;
	}
	
}
