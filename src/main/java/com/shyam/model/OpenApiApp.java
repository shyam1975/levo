package com.shyam.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Data
public class OpenApiApp implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;

	@Column(columnDefinition = "json")
	private String openApiJson;
	private String app;
	private int version;

	public OpenApiApp() {
	}

	public OpenApiApp(Long id, String openApiJson, String app, int version) {
		this.id = id;
		this.openApiJson = openApiJson;
		this.app = app;
		this.version = version;
	}

}