package com.alexhamilton.udemy.ppmtoolbackend.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.alexhamilton.udemy.ppmtoolbackend.domain.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

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

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);

			return true;
		} catch (SignatureException ex) {
			System.out.println("Invalid JWT Signature");
		} catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT");
		} catch (ExpiredJwtException e) {
			System.out.println("Expired JWT");
		} catch (UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT");
		} catch (IllegalArgumentException ex) {
			System.out.println("JWT claims are empty");
		}

		return false;
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
		String id = (String) claims.get("id");

		return Long.parseLong(id);
	}

}
