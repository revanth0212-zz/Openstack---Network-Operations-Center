package com.utd.ips.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstanceJson {
	
	private InstanceQuery q;

	public InstanceQuery getQ() {
		return q;
	}

	public void setQ(InstanceQuery q) {
		this.q = q;
	}
	
	

}
