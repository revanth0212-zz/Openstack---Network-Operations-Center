package com.utd.ips.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeaderAuth {
	private Tenant auth;
	

	public Tenant getAuth() {
		return auth;
	}

	public void setAuth(Tenant auth) {
		this.auth = auth;
	}
	
}
