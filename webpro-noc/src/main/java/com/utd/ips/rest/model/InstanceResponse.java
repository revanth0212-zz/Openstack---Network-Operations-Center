package com.utd.ips.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InstanceResponse {
	private String UUID;
	private String vmName;
	private String tenantName;
	private double cpuUtil = -1;
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getVmName() {
		return vmName;
	}
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public double getCpuUtil() {
		return cpuUtil;
	}
	public void setCpuUtil(double cpuUtil) {
		this.cpuUtil = cpuUtil;
	}
	
	
}
