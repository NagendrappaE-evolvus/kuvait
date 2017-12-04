package com.evolvus.abk.ftp.bean;


import lombok.Data;

@Data
public class CustomResponse {

	private String status;
	
	private String description;
	
	private Object data;
	
	@Override
	public String toString() {
		return String.format("[CustomResponse={status=%s,description=%s,data=%s}]", status,description,data);
	}
}
