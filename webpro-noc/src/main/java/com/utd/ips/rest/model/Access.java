package com.utd.ips.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Access {
	//private String access;
	private Token token;

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}

/*	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}
*/
	@Override
	public String toString() {
		return "Access  token=" + token + "]";
	}
	
}
