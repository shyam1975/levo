package com.shyam.service;

import java.util.List;

import com.shyam.exception.OpenApiAppFoundException;
import com.shyam.exception.OpenApiAppNotFoundException;
import com.shyam.model.OpenApiApp;

public interface OpenApiAppService {

	OpenApiApp getLatestOpenApiApp(String app) throws OpenApiAppNotFoundException;

	OpenApiApp postOpenApiApp(String app, byte[] openApiAppJsonBytes) throws Exception;
	OpenApiApp putOpenApiApp(String app, byte[] openApiAppJsonBytes) throws OpenApiAppNotFoundException;

}