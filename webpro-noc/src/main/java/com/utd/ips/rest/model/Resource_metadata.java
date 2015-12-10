package com.utd.ips.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource_metadata {
	private String cpu_number;
	private String disk_gb;
	private String display_name;
	private String ephemeral_gb;
	
	public String getDisplay_name() {
		return display_name;
	}

	public String getCpu_number() {
		return cpu_number;
	}

	public void setCpu_number(String cpu_number) {
		this.cpu_number = cpu_number;
	}

	public String getDisk_gb() {
		return disk_gb;
	}

	public void setDisk_gb(String disk_gb) {
		this.disk_gb = disk_gb;
	}

	public String getEphemeral_gb() {
		return ephemeral_gb;
	}

	public void setEphemeral_gb(String ephemeral_gb) {
		this.ephemeral_gb = ephemeral_gb;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
}
