package com.alexhamilton.udemy.ppmtoolbackend.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.alexhamilton.udemy.ppmtoolbackend.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	public String generateToken(Authentication auth) {
		User user = (User) auth.getPrincipal();
		Date now = new Date(System.currentTimeMillis());

		Date expiryDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);

		String userId = Long.toString(user.getId());

		Map<String, Object> claimsMap = new HashMap<>();
		claimsMap.put("id", userId);
		claimsMap.put("username", user.getUsername());
		claimsMap.put("fullName", user.getFullName());

		// @formatter:off
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claimsMap)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.compact();
		// @formatter:on
	}

}
