package com.niit.todotraker.userservice.security;

import com.niit.todotraker.userservice.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class SecurityImpl implements ISecurity {

    //Generating JWT Token and Sending after putting into map
    @Override
    public Map<String, String> tokenGenerate(User user) {
        Map<String,String> result = new HashMap<>();
        String jwtToken = Jwts.builder().setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "userKey")
                .compact();
        result.put("token", jwtToken);
        return result;
    }
}
