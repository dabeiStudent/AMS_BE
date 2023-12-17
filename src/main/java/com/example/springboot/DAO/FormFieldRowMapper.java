package com.example.springboot.DAO;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.springboot.Model.FormFieldModel;

public class FormFieldRowMapper implements RowMapper<FormFieldModel> {

    @Override
    public FormFieldModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        FormFieldModel formField = new FormFieldModel();
        formField.setId(rs.getString("id"));
        formField.setFormId(rs.getString("form_id"));
        formField.setKey(rs.getString("key"));
        formField.setLabel(rs.getString("label"));
        formField.setImportant(rs.getBoolean("important"));
        formField.setTypeInput(rs.getString("type_input"));
        formField.setSortOrder(rs.getInt("sort_order"));
        return formField;
    }
}