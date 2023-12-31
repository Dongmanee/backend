package com.dongmanee.domain.security.dto.response;

import lombok.Data;

@Data
public class JwsToken {

	private String accessToken;

	private JwsToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public static JwsToken of(String accessToken) {
		return new JwsToken(accessToken);
	}
}
