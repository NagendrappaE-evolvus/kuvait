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
@Table(name = "FTP_MAPPER_VERSION")
public class MapperVersion {
	
	@Column(name = "MAPPER_NAME", length = 30)
	private String mapperName;

	@Id
	@Column(name = "MAPPER_KEY", length = 2)
	private String mapperKey;
	
	@Column(name="VERSION_CHARS",length=2)
	private String versionChars;

	@Column(name = "CURR_VERSION")
	private Long currentVersion;

	@Column(name = "CURR_ARCH_VERSION")
	private Long currArchVersion;
}
