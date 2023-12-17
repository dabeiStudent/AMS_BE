package com.example.springboot.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

public class YeuCauChungTu {
	private String maCT;
	private String maLoai;
	private String nguoiTao;
	private LocalDateTime thoiGianTao;
	private String maTT;
	private String maForm;
	private Map<String, Object> noiDung;
	private ArrayList<Map<String, Object>> nguoiDuyet;
	public YeuCauChungTu() {
		
	}
	public YeuCauChungTu(String maCT,String maLoai, String nguoiTao, LocalDateTime thoiGianTao, String maTT, String maForm,
			Map<String, Object> noiDung, ArrayList<Map<String, Object>> nguoiDuyet) {
		super();
		this.maCT=maCT;
		this.maLoai = maLoai;
		this.nguoiTao = nguoiTao;
		this.thoiGianTao = thoiGianTao;
		this.maTT = maTT;
		this.maForm=maForm;
		this.noiDung = noiDung;
		this.nguoiDuyet = nguoiDuyet;
	}
	
	public String getMaCT() {
		return maCT;
	}
	public void setMaCT(String maCT) {
		this.maCT = maCT;
	}
	public String getMaLoai() {
		return maLoai;
	}
	public void setMaLoai(String maLoai) {
		this.maLoai = maLoai;
	}
	public String getNguoiTao() {
		return nguoiTao;
	}
	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}
	public LocalDateTime getThoiGianTao() {
		return thoiGianTao;
	}
	public void setThoiGianTao(LocalDateTime thoiGianTao) {
		this.thoiGianTao = thoiGianTao;
	}
	public String getMaTT() {
		return maTT;
	}
	public void setMaTT(String maTT) {
		this.maTT = maTT;
	}
	
	public String getMaForm() {
		return maForm;
	}
	public void setMaForm(String maForm) {
		this.maForm = maForm;
	}
	public Map<String, Object> getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(Map<String, Object> noiDung) {
		this.noiDung = noiDung;
	}
	public ArrayList<Map<String, Object>> getNguoiDuyet() {
		return nguoiDuyet;
	}
	public void setNguoiDuyet(ArrayList<Map<String, Object>> nguoiDuyet) {
		this.nguoiDuyet = nguoiDuyet;
	}
}
