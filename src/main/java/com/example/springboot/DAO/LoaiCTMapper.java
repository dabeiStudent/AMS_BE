package com.example.springboot.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.springboot.Model.LoaiChungTuModel;

public class LoaiCTMapper implements RowMapper<LoaiChungTuModel>{
	 @Override
	 public LoaiChungTuModel mapRow(ResultSet resultSet, int i) throws SQLException {
		 LoaiChungTuModel loaiCT = new LoaiChungTuModel();
		 loaiCT.setId(resultSet.getString("id"));
		 loaiCT.setName(resultSet.getString("form_type_name"));
		 loaiCT.setFormId(resultSet.getString("form_id"));
		 return loaiCT;
	 }
}
