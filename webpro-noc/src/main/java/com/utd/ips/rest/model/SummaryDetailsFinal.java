package com.utd.ips.rest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummaryDetailsFinal {
	private List<SummaryDetailsList> tenants;

	public List<SummaryDetailsList> getTenants() {
		return tenants;
	}

	public void setTenants(List<SummaryDetailsList> tenants) {
		this.tenants = tenants;
	}
}
