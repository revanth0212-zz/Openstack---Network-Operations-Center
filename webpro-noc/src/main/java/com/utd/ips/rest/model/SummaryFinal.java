package com.utd.ips.rest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummaryFinal {
	private List<SummaryList> tenants;

	public List<SummaryList> getTenants() {
		return tenants;
	}

	public void setTenants(List<SummaryList> tenants) {
		this.tenants = tenants;
	}
}
