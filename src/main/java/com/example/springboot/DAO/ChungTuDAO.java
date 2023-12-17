package com.example.springboot.DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.springboot.Model.*;

@Repository
public class ChungTuDAO {
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ChungTuDAO(JdbcTemplate jdbcTemplate) {
	      this.jdbcTemplate = jdbcTemplate;	}	
	public List<ChungTuModel> getAllChungTus(String user) {
		 String sql = "SELECT c.doc_id as doc_id, lct.form_type_name as form_type_name, ams_u.full_name as user_create, c.time_create as time_create, ams_s.status_name as status_id "
		 		+ "FROM chungtu c "
		 		+ "JOIN ams_user as ams_u ON ams_u.id = c.user_create "
		 		+ "JOIN ams_status as ams_s ON ams_s.id = c.status_id "
		 		+ "JOIN ams_form_type lct ON lct.id = c.approval_type "
		 		+ "where c.user_create = ?";
	    return jdbcTemplate.query(sql, new ChungTuMapper(),user);
	}
	public List<ChungTuModel> getAllChungTuDuyet(String user){
		String sql = "SELECT c.doc_id as doc_id, lct.form_type_name as form_type_name, ams_u.full_name as user_create, c.time_create as time_create, ams_s.status_name as status_id "
		 		+ "FROM chungtu c "
		 		+ "JOIN ams_user as ams_u ON ams_u.id = c.user_create "
		 		+ "JOIN ams_status as ams_s ON ams_s.id = c.status_id "
		 		+ "JOIN ams_form_type lct ON lct.id = c.approval_type "
		 		+ "JOIN chungtu_ketqua ck on ck.doc_id = c.doc_id "
		 		+ "where ck.user_update = ?";
	    return jdbcTemplate.query(sql, new ChungTuMapper(),user);
	}
	public List<TrangThaiModel> getNhatKiChungTu(String maCT) {
		String sql="select ct.doc_status_id as doc_status_id, c.doc_id as doc_id, as2.status_name as status, ua.full_name as user_update, ct.time_update as time_update "
				+ "FROM chungtu c "
				+ "JOIN chungtu_trangthai ct on c.doc_id = ct.doc_id "
				+ "JOIN ams_status as2 on ct.status_id = as2.id "
				+ "JOIN ams_user ua on ct.user_update = ua.id "
				+ "where c.doc_id = ? "
				+ "order by ct.doc_status_id asc ";
		return jdbcTemplate.query(sql, new Object[] {maCT}, new TrangThaiMapper());
	}
	
	public ChiTietCTModel getChiTietChungTu(String maCT) {
		String sql= "select lc.form_type_name as loaiCT , c.doc_id as maCT , t.status_name as maTT , ua.full_name as nguoiTao, ua.id as maNguoiTao , c.time_create as thoiGianTao , cn.\"content\" as noiDung "
				+ "from chungtu c "
				+ "join chungtu_noidung cn on cn.doc_id = c.doc_id "
				+ "join ams_form_type lc on lc.id  = c.approval_type "
				+ "join ams_status t on t.id  = c.status_id "
				+ "join ams_user ua on ua.id  = c.user_create "
				+ "where c.doc_id = ? ";
		return jdbcTemplate.queryForObject(sql,new Object[] {maCT}, new ChiTietCTMapper());
	}
	
	public List<KetQuaModel> getKetQuaChungTu(String maCT) {
		String sql= "select ck.doc_result_id as doc_result_id, ck.doc_id as doc_id, ck.lvl as lvl, aak.name as required, ck.result as result, ck.user_update || ' - ' || ua.full_name  as user_update, ck.time_update as time_update "
				+ "from chungtu_ketqua ck "
				+ "join ams_approve_kind aak on aak.code = ck.approve_kind_code "
				+ "join ams_user ua on ua.id = ck.user_update "
				+ "where ck.doc_id = ? "
				+ "order by ck.lvl ";
		return jdbcTemplate.query(sql,new Object[] {maCT}, new KetQuaMapper());
	}
	public String getTenNguoiDuyet (String id) {
		String sql = "Select full_name from ams_user "
				+ "where id = ?";
		return jdbcTemplate.queryForObject(sql,new Object[] {id}, String.class);
	}
	public String postNewChungTu(YeuCauChungTu yeuCau) {
		try {
		String sql = "insert into chungtu (approval_type,user_create,time_create,status_id) "
				+ "values (?,?,?,?) ";
		jdbcTemplate.update(sql, yeuCau.getMaLoai(), yeuCau.getNguoiTao(), yeuCau.getThoiGianTao(), yeuCau.getMaTT());
		String sqlSelect = "select c.doc_id from chungtu c "
				+ "where c.approval_type = ?  and c.user_create = ? and c.time_create = ?  and c.status_id = ? ";
		String maCT = jdbcTemplate.queryForObject(sqlSelect, String.class,yeuCau.getMaLoai(),yeuCau.getNguoiTao(),yeuCau.getThoiGianTao(),"TT001");
		System.out.println("Them chung tu thanh cong");
		return maCT;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	public Boolean postChungTuNoiDung(YeuCauChungTu yeuCau, String jsonString) {
		try {
			String sql = "insert into chungtu_noidung (doc_id,form_id,content) "
					+ "values (?,?,?::jsonb) ";
			jdbcTemplate.update(sql, yeuCau.getMaCT(), yeuCau.getMaForm(),jsonString);
//			System.out.println("Them noi dung thanh cong");
			return true;
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	@Transactional
	public Boolean postChungTuTrangThai(YeuCauChungTu yeuCau) {
		try {
			String sql= "insert into chungtu_trangthai (doc_id,status_id,user_update,time_update) "
					+ "values (?,?,?,?)";
			jdbcTemplate.update(sql,yeuCau.getMaCT(),yeuCau.getMaTT(),yeuCau.getNguoiTao(),yeuCau.getThoiGianTao());
			String sql2 = "insert into chungtu_trangthai (doc_id, status_id, user_update,time_update) "
					+"values (?,?,?,?) ";
			List<Map<String, Object>> listNguoiduyet = yeuCau.getNguoiDuyet();
			for(Map<String,Object> nguoiDuyet : listNguoiduyet) {
				int lvl = Integer.parseInt(nguoiDuyet.get("lvl").toString());
				if(lvl == 1) {
					jdbcTemplate.update(sql2,yeuCau.getMaCT(),"TT002",nguoiDuyet.get("user_update"),yeuCau.getThoiGianTao());
				}
			}
			String sqlUpdate = "update chungtu set status_id = ? where doc_id = ? ";
			jdbcTemplate.update(sqlUpdate,"TT002",yeuCau.getMaCT());
//			System.out.println("Them trang thai thanh cong");
			return true;
		}catch(Exception e) {
			System.out.println(e);
			return false;
		}
	}
	public Boolean postChungTuKetQua(YeuCauChungTu yeuCau) {
        String sql = "INSERT INTO chungtu_ketqua (doc_id, lvl, result, approve_kind_code, user_update, time_update) VALUES (?, ?, ?, ?, ?, ?)";
        List<Map<String, Object>> nguoiDuyetList = yeuCau.getNguoiDuyet();
        try {
        	for (Map<String, Object> nguoiDuyet : nguoiDuyetList) {
        		jdbcTemplate.update(sql, 
        			yeuCau.getMaCT(),
        			(int) nguoiDuyet.get("lvl"),
        			nguoiDuyet.get("result"),
        			(String) nguoiDuyet.get("approve_kind_code"),
        			(String) nguoiDuyet.get("user_update"),
        			nguoiDuyet.get("time_update")
        	    );
        		System.out.print(nguoiDuyet.get("lvl").toString());
        		System.out.println(nguoiDuyet.get("approve_kind_code").toString());
        	}
//        	System.out.println("Them ket qua thanh cong");
       		return true;
        }catch(Exception e) {
        	System.out.println(e);
        	return false;
        }
	}
	public Map<String, String> getFormLabel(YeuCauChungTu yeuCau) {
		String sqlSelect = "select label, key from form_field ff "
				+ "where ff.form_id = ? and ff.important = true ";
		return jdbcTemplate.queryForObject(sqlSelect, (rs, rowNum) -> {
	        Map<String, String> formInfo = new HashMap<>();
	        formInfo.put("label", rs.getString("label"));
	        formInfo.put("key", rs.getString("key"));
	        return formInfo;
	    }, yeuCau.getMaForm());
	}
	public List<Map<String,String>> getCondition(String key, String maForm,String maLoai){
	    String sql = "select operator, compared_value, pair, logic from ams_form_type_condition aftc join ams_form_type aft on aft.id = aftc.form_type_id  where form_key = ? and form_type_id = ? and aft.form_id = ?";
	    return jdbcTemplate.query(sql, (rs, rowNum) -> {
	        Map<String, String> conditionInfo = new HashMap<>();
	        conditionInfo.put("match", rs.getString("operator"));
	        conditionInfo.put("compared_value", rs.getString("compared_value"));
	        conditionInfo.put("logic", rs.getString("logic"));
	        conditionInfo.put("pair",rs.getString("pair"));
	        return conditionInfo;
	    }, key, maLoai, maForm);
	}
	public List<String> pairValue(String key, String typeId, String pair, String operator) {
        String sql = "SELECT compared_value FROM ams_form_type_condition aftc "
                + "WHERE form_type_id = ? AND pair = ? AND operator = ? AND form_key = ?";

        return jdbcTemplate.queryForList(sql, String.class, typeId, pair, operator, key);
    }
	public List<Map<String, String>> getApprover(String key, String match, String comparedValue, String maForm){
		String sql = "select afta.lvl ,afta.frequence, afta.approve_kind_code ,au.id as user, au.full_name as name "
				+ "from "
				+ "	ams_form_type_approver afta "
				+ "join "
				+ "	ams_form_type_condition aftc on afta.condition_id =aftc.id "
				+ "join "
				+ "	ams_form_type aft on aftc.form_type_id = aft.id "
				+ "join "
				+ "	form_field ff on ff.form_id = aft.form_id and ff.\"key\" = aftc.form_key "
				+ "join "
				+ "	ams_team_user atu  on afta.user_team_id = atu.id "
				+ "join "
				+ "	ams_user au on au.id = atu.user_id "
				+ "where "
				+ "	ff.\"key\" = ? and aftc.\"operator\" = ? and aftc.compared_value = ? and aft.form_id = ? "
				+ "order by afta.lvl asc;";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
	        Map<String, String> approver = new HashMap<>();
	        approver.put("lvl", rs.getString("lvl"));
	        approver.put("frequence", rs.getString("frequence"));
	        approver.put("approve_kind_code",rs.getString("approve_kind_code"));
	        approver.put("user", rs.getString("user"));
	        approver.put("name", rs.getString("name"));
	        return approver;
	    }, key,match,comparedValue,maForm);
	}
	public Boolean checkCT(String maCT) {
		String sql = "select user_create from chungtu where doc_id = ? and status_id != 'TT005'";
	    try {
	        String userFound = jdbcTemplate.queryForObject(sql, String.class, maCT);
	        return userFound != null;
	    } catch (EmptyResultDataAccessException e) {
	        return false;
	    }
	}
	public Boolean checkKetQua(String maCT) {
		String sql = "select user_update from chungtu_ketqua where doc_id = ? and result is not null";
	    
	    List<String> results = jdbcTemplate.queryForList(sql, String.class, maCT);
	    if (results.size() > 0) {
	        return false; // Nếu có kết quả, trả về false
	    } else {
	        return true; // Nếu không có kết quả hoặc nhiều hơn 1, trả về true
	    }
	}
	public String getNguoiTao(String maCT) {
		String sql = "select user_create from chungtu where doc_id = ?";
		return jdbcTemplate.queryForObject(sql,String.class, maCT);
	}
	@Transactional
	public Boolean updateStatus(String maCT, String nguoiCapNhat) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		try {
			String sql = "update chungtu set status_id = 'TT005' where doc_id = ? ";
			jdbcTemplate.update(sql,maCT);
			String sql2= "insert into chungtu_trangthai (doc_id,status_id,user_update,time_update) "
					+ "values (?,?,?,?) ";
			jdbcTemplate.update(sql2,maCT,"TT005",nguoiCapNhat,currentDateTime);
			return true;
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<LoaiChungTuModel> getAllLoaiCT(){
		String sql="select * from ams_form_type";
		return jdbcTemplate.query(sql, new LoaiCTMapper());
	}
	public List<FormFieldModel> getAllFormFields(String formId) {
		String sql = "SELECT * FROM form_field "
				+ "where form_id = ?";
	    return jdbcTemplate.query(sql,new Object[] {formId}, new FormFieldRowMapper());
	}
	public List<Map<String, String>> listCheckNguoiDuyet(String maForm){
		String sql = "select distinct on (au.id) au.id ,afta.lvl, aft.form_id "
				+ "from ams_form_type_approver afta "
				+ "join ams_form_type_condition aftc on afta.condition_id  =aftc.id "
				+ "join ams_form_type aft on aft.id = aftc.form_type_id "
				+ "join ams_form af on af.id = aft.form_id "
				+ "join ams_team_user atu on atu.id  = afta.user_team_id "
				+ "join ams_user au on au.id  = atu.user_id "
				+ "where af.id = ? ";
		return jdbcTemplate.query(sql, (rs, rowNum)->{
				Map<String,String> nguoiDuyet = new HashMap<>();
				nguoiDuyet.put("id",rs.getString("id"));
				nguoiDuyet.put("lvl",rs.getString("lvl"));
				return nguoiDuyet;
		},maForm);
	}
}
