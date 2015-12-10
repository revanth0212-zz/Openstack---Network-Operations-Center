package com.utd.ips.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CPU_util {
	private String counter_name;
	private String counter_type;
	private String counter_unit;
	private double counter_volume;
	private String message_id;
	private String project_id;
	private String recorded_at;
	private String resource_id;
	private Resource_metadata resource_metadata;
	private String source;
	private String timestamp;
	private String user_id;
	public String getCounter_name() {
		return counter_name;
	}
	public void setCounter_name(String counter_name) {
		this.counter_name = counter_name;
	}
	public String getCounter_type() {
		return counter_type;
	}
	public void setCounter_type(String counter_type) {
		this.counter_type = counter_type;
	}
	public String getCounter_unit() {
		return counter_unit;
	}
	public void setCounter_unit(String counter_unit) {
		this.counter_unit = counter_unit;
	}
	public double getCounter_volume() {
		return counter_volume;
	}
	public void setCounter_volume(double counter_volume) {
		this.counter_volume = counter_volume;
	}
	public String getMessage_id() {
		return message_id;
	}
	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getRecorded_at() {
		return recorded_at;
	}
	public void setRecorded_at(String recorded_at) {
		this.recorded_at = recorded_at;
	}
	public String getResource_id() {
		return resource_id;
	}
	public void setResource_id(String resource_id) {
		this.resource_id = resource_id;
	}
	public Resource_metadata getResource_metadata() {
		return resource_metadata;
	}
	public void setResource_metadata(Resource_metadata resource_metadata) {
		this.resource_metadata = resource_metadata;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	


}
