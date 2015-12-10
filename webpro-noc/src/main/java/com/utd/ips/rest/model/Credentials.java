package com.utd.ips.rest.model;

public class Credentials {
	@Override
	public String toString() {
		return "Credentials [tenantName=" + tenantName + ", password=" + password + ", username=" + username
				+ ", token=" + token + "]";
	}

	private String tenantName;
	private String password;
	private String username;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
