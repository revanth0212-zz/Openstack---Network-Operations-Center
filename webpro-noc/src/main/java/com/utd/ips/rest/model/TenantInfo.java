package com.utd.ips.rest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TenantInfo {
	private List<TenantsData> tenants;
	private List<TenantsData> tenants_links;
	public List<TenantsData> getTenants_links() {
		return tenants_links;
	}

	public void setTenants_links(List<TenantsData> tenants_links) {
		this.tenants_links = tenants_links;
	}

	public List<TenantsData> getTenants() {
		return tenants;
	}

	public void setTenants(List<TenantsData> tenants) {
		this.tenants = tenants;
	}



	
}
