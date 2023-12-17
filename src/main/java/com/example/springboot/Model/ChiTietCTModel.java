package com.example.springboot.Model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

public class ChiTietCTModel {
	private String loaiCT;
	private String maCT;
	private String trangThai;
	private String tenNguoiTao;
	private String maNguoiTao;
	private LocalDateTime ngayTao;
	private String noiDung;
	public ChiTietCTModel() {
		
	}
	public ChiTietCTModel(String loaiCT, String maCT, String trangThai, String tenNguoiTao, String maNguoiTao, LocalDateTime ngayTao, String noiDung) {
		this.loaiCT=loaiCT;
		this.maCT=maCT;
		this.trangThai=trangThai;
		this.tenNguoiTao=tenNguoiTao;
		this.maNguoiTao=maNguoiTao;
		this.ngayTao=ngayTao;
		this.noiDung=noiDung;
	}
	public String getLoaiCT() {
		return loaiCT;
	}
	public void setLoaiCT(String loaiCT) {
		this.loaiCT = loaiCT;
	}
	public String getMaCT() {
		return maCT;
	}
	public void setMaCT(String maCT) {
		this.maCT = maCT;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public String getTenNguoiTao() {
		return tenNguoiTao;
	}
	public void setTenNguoiTao(String tenNguoiTao) {
		this.tenNguoiTao = tenNguoiTao;
	}
	public String getMaNguoiTao() {
		return maNguoiTao;
	}
	public void setMaNguoiTao(String maNguoiTao) {
		this.maNguoiTao = maNguoiTao;
	}
	public LocalDateTime getNgayTao() {
		return ngayTao;
	}
	public void setNgayTao(LocalDateTime ngayTao) {
		this.ngayTao = ngayTao;
	}

	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	
}
