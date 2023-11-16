package com.dongmanee.global.security.login.provider;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.dongmanee.global.security.login.service.UserDetailLoginService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtProvider {
	private final String salt;
	private final long accessTokenValidityIn;
	private final UserDetailLoginService userDetailLoginService;
	private Key secretKey;

	public JwtProvider(@Value("${jwt.secret}") String salt,
		@Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidityIn,
		UserDetailLoginService userDetailLoginService) {
		this.salt = salt;
		this.accessTokenValidityIn = accessTokenValidityIn * 1000;
		this.userDetailLoginService = userDetailLoginService;
	}

	@PostConstruct
	protected void init() {
		this.secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
	}

	// 토큰 생성
	public String createToken(String account, String roles) {
		Claims claims = Jwts.claims().setSubject(account);
		claims.put("roles", roles);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + accessTokenValidityIn))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	// 권한정보 획득
	// Spring Security 인증과정에서 권한확인을 위한 기능
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailLoginService.loadUserByUsername(this.getAccount(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	// 토큰에 담겨있는 유저 account 획득
	public String getAccount(String token) {
		return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
	}

	// Authorization Header를 통해 인증을 한다.
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("Authorization");
	}

	// 토큰 검증
	public boolean validateToken(String token) {
		try {
			// Bearer 검증
			if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
				return false;
			} else {
				token = token.split(" ")[1].trim();
			}
			Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			// 만료되었을 시 false
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

}
