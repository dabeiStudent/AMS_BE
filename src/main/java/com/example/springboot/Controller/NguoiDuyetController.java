package com.example.springboot.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/duyet")
public class NguoiDuyetController {
	
	@GetMapping("/nguoi-duyet")
	public ResponseEntity<String> getNguoiDuyet(){
		return ResponseEntity.status(200).body("Oke nhe nguoi duyet");
	}
}
