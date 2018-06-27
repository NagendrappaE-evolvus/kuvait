package com.evolvus.abk.ftp.domain.mappers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity 
@ToString
@Table(name="FTP_MAPPER_VERSION")
public class MapperVersion {
	
	@Id
	@Column(name="MAPPER_NAME",length=30)
	private String mapperName;
	
	@Column(name="MAPPER_KEY",length=2)
	private String mapperKey;
	
	@Column(name="CURR_VERSION")
	private Integer currentVersion;
}
