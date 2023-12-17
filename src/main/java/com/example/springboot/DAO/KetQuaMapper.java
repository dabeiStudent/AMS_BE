package com.example.springboot.DAO;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.springboot.Model.*;
public class KetQuaMapper implements RowMapper<KetQuaModel>{
	@Override
	public KetQuaModel mapRow(ResultSet resultSet, int i) throws SQLException{
		KetQuaModel ketQua = new KetQuaModel();
		ketQua.setMaKetQua(resultSet.getInt("doc_result_id"));
		ketQua.setMaCT(resultSet.getString("doc_id"));
		ketQua.setBac(resultSet.getInt("lvl"));
		ketQua.setBatBuoc(resultSet.getString("required"));;
		if(resultSet.getString("result") != null) {
			ketQua.setKetQua(resultSet.getInt("result"));
		}else {
			ketQua.setKetQua(null);
		}
		ketQua.setNguoiDuyet(resultSet.getString("user_update"));
		if(resultSet.getTimestamp("time_update") != null)
		{
			ketQua.setThoiGianDuyet(resultSet.getTimestamp("time_update").toLocalDateTime());
		}else {
			ketQua.setThoiGianDuyet(null);
		}
		return ketQua;
	}
}
