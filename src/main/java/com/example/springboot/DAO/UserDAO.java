package com.example.springboot.DAO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.springboot.BusinessLogic.UserServices;
import com.example.springboot.Model.ActionModel;
import com.example.springboot.Model.UserModel;

@Repository
public class UserDAO {
	private final JdbcTemplate jdbcTemplate;
	@Autowired
	public UserDAO(JdbcTemplate jdbcTemplate) {
	      this.jdbcTemplate = jdbcTemplate;
	}	
	public boolean registerUser(String userName, String passWord, String fullName) {
		 try {
	          String sql = "INSERT INTO ams_user(username, full_name, password) VALUES (?, ?, ?)";
	          jdbcTemplate.update(sql, userName, fullName, passWord);
	          return true;
	     } catch (Exception e) {
	          return false;
	        }
	}

	public UserModel getUser(String user, String passWord) {
		UserModel userLogin = new UserModel();
		try {
		String sql="SELECT * FROM ams_user au WHERE au.id = ? ";
		return jdbcTemplate.queryForObject(sql,new Object[] {user},(rs, rowNum) -> {
			userLogin.setId(rs.getString("id"));
			userLogin.setUserName(rs.getString("username"));
			userLogin.setFullName(rs.getString("full_name"));
			userLogin.setPassWord(rs.getString("password"));
            return userLogin;
		});
		}catch(Exception e) {
			return userLogin;
		}
	}
	public List<ActionModel> getAction(String userName) {
		try {
		String sql="select aa.id  , aa.action_name  from ams_team_user_action atua "
				+ "join ams_team_user atu on atu.id = atua.team_user_id "
				+ "join ams_action aa on aa.id = atua.action_id "
				+ "join ams_user au on au.id = atu.user_id "
				+ "where au.username  = ? ";
		return jdbcTemplate.query(sql,new Object[] {userName},(rs,column)->{
			ActionModel action = new ActionModel();
			action.setId(rs.getString("id"));
			action.setActionName(rs.getString("action_name"));
			return action;
		});
		}catch(Exception e) {
			return null;
		}
	}
}
