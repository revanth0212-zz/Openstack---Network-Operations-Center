package com.utd.ips.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tenant {
	private String tenantName;
	private PasswordCred passwordCredentials;

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public PasswordCred getPasswordCredentials() {
		return passwordCredentials;
	}

	public void setPasswordCredentials(PasswordCred passwordCredentials) {
		this.passwordCredentials = passwordCredentials;
	}

	
}
