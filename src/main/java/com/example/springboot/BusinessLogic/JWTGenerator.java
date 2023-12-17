package com.example.springboot.BusinessLogic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Repository
public class JWTGenerator {
//	private static final SecretKey secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
	private static final String secretKey = "duylannnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn";

	private long validityInMilliseconds = 3600000; //ms
	 public String createToken(String user, String fullname) {
	        Claims claims = Jwts.claims().setSubject(user);
	        claims.put("user",user);
	        
	        Date now = new Date();
	        Date validity = new Date(now.getTime() + validityInMilliseconds);

	        return Jwts.builder()
	                .setClaims(claims)
	                .setIssuedAt(now)
	                .setExpiration(validity)
	                .signWith(SignatureAlgorithm.HS256, secretKey)
	                .compact();
	 }
	 public Map<String,Object> tokenDecrypt (String token){
		 System.out.println(token);
		 Jws<Claims> claimsJws = Jwts.parserBuilder()
	                .setSigningKey(secretKey)
	                .build()
	                .parseClaimsJws(token);

	     Claims body = claimsJws.getBody();

	     String username = body.getSubject();
	     String user = (String) body.get("user");

	     Map<String, Object> tokenInfo = new HashMap<>();
	     tokenInfo.put("user", user);

	     return tokenInfo;
	 }
}	
