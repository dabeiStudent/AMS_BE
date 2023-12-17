package com.example.springboot.DAO;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.springboot.Model.*;
public class ChiTietCTMapper implements RowMapper<ChiTietCTModel> {
	@Override
	public ChiTietCTModel mapRow(ResultSet resultSet, int i) throws SQLException{
		ChiTietCTModel chitietCT = new ChiTietCTModel();
		chitietCT.setLoaiCT(resultSet.getString("loaiCT"));
		chitietCT.setMaCT(resultSet.getString("maCT"));
		chitietCT.setTrangThai(resultSet.getString("maTT"));
		chitietCT.setTenNguoiTao(resultSet.getString("nguoiTao"));
		chitietCT.setMaNguoiTao(resultSet.getString("maNguoiTao"));
		chitietCT.setNgayTao(resultSet.getTimestamp("thoiGianTao").toLocalDateTime());
		chitietCT.setNoiDung(resultSet.getString("noiDung"));
		return chitietCT;
	}
}
