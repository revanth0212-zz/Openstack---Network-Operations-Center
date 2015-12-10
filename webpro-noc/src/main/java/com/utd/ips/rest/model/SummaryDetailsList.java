package com.utd.ips.rest.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SummaryDetailsList {
	private String name;
	private String tenant_id;
	private List<SummaryVM> vms = new ArrayList<SummaryVM>();

	public String getTenant_id() {
		return tenant_id;
	}

	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}

	public List<SummaryVM> getVms() {
		return vms;
	}

	public void setVms(List<SummaryVM> vms) {
		this.vms = vms;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
