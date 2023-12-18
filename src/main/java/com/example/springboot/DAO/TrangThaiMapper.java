package com.example.springboot.DAO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.springboot.Model.*;
public class TrangThaiMapper implements RowMapper<TrangThaiModel> {
	 @Override
	 public TrangThaiModel mapRow(ResultSet resultSet, int i) throws SQLException {
		 TrangThaiModel trangThai = new TrangThaiModel();
		 trangThai.setMaCT(resultSet.getString("doc_id"));
		 trangThai.setMaTrangThaiCT(resultSet.getInt("doc_status_id"));
		 trangThai.setMaTT(resultSet.getString("status"));
		 trangThai.setNguoiCapNhat(resultSet.getString("user_update"));
		 trangThai.setUser(resultSet.getString("user"));
		 trangThai.setThoiGianCapNhat(resultSet.getTimestamp("time_update").toLocalDateTime());
		 return trangThai;
	 }
}
