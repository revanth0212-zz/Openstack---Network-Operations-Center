package com.utd.ips.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Token {
	//private String token;
	//private AuthToken auth;
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + "]";
	}

	/*public AuthToken getAuth() {
		return auth;
	}

	public void setAuth(AuthToken auth) {
		this.auth = auth;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}*/
	
	
}
