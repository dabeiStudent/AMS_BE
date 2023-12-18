package com.example.springboot.Controller;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot.BusinessLogic.UserServices;
import com.example.springboot.Model.UserModel;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/user")
public class UserController {
	private final UserServices userServices;
	@Autowired
	public UserController(UserServices userServices) {
		this.userServices= userServices;
	}
    @PostMapping("/register")
    public ResponseEntity<String> userRegister (@RequestParam(name="username") String userName,
    								@RequestParam(name="password") String passWord,
    								@RequestParam(name="fullname") String fullName) {
    	if(userName == "" || passWord == "" || fullName == "") {
    		return ResponseEntity.status(404).body("Vui lòng nhập đẩy đủ thông tin");
    	}else {
    		return userServices.userRegister(userName, passWord, fullName);
    	}
    }
    @PostMapping("/login")
    public ResponseEntity<String> userLogin(@RequestBody UserModel userModel, HttpServletResponse response,HttpServletRequest request){
    	String user = userModel.getId();
    	String passWord = userModel.getPassWord();
    	if(user =="" || passWord == "") {
    		return ResponseEntity.status(404).body("Vui lòng nhập đẩy đủ thông tin");
    	}else {
    		System.out.println(user +" "+ passWord);
    		return userServices.userLogin(user, passWord, response,request);
    	}
    }
}
