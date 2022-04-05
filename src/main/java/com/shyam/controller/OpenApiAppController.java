package com.shyam.controller;

import java.io.IOException;
import java.util.List;

import com.shyam.exception.OpenApiAppFoundException;
import com.shyam.exception.OpenApiAppNotFoundException;
import com.shyam.model.HttpResponse;
import com.shyam.model.OpenApiApp;
import com.shyam.service.OpenApiAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.shyam.controller.OpenApiAppController.ROOT_PATH;
import static org.springframework.http.HttpStatus.OK;

/**
 * @author Shyam Jeganathan
 */

@RestController
//@RequestMapping(path = { "/", "/movie"})
@RequestMapping(path = {ROOT_PATH})
@Api(value = "OpenApiAppController", description = "REST APIs related to OpenApiApp Entity")
public class OpenApiAppController {
	public static final String MOVIE_DELETED_SUCCESSFULLY = "Movie deleted successfully";
	public static final String ROOT_PATH = "/openapiapp";

	Logger logger = LoggerFactory.getLogger("OpenApiAppController");


	/**
	 * The service class used by the RestController
	 */
	@Autowired
	OpenApiAppService openApiAppService;


	@ApiOperation(value = "Find OpenApiApp in system by App Name ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
			@ApiResponse(code = 400, message = "Not Found"),
	})
	@GetMapping("/{app}")
	/**
	 * call service class to get the OpenApiApp for corresponding app
	 * propagate exception thrown by service class
	 */
	public ResponseEntity<OpenApiApp> getOpenApiApp(@PathVariable("app") String app) throws OpenApiAppNotFoundException {

		OpenApiApp openApiApp = openApiAppService.getLatestOpenApiApp(app);
		return new ResponseEntity<>(openApiApp, OK);
	}

	@ApiOperation(value = "Insert a OpenApiApp into system ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
	})
	@PostMapping
	/**
	 * call service layer to insert open api app, system will check for duplicate apps
	 * if duplicate app is found, an exception is thrown
	 */
	public ResponseEntity<OpenApiApp> postOpenApiApp(@RequestParam("document") MultipartFile multipartFile, @RequestParam("app") String app) throws OpenApiAppFoundException, IOException, Exception {

		logger.info(new String(multipartFile.getBytes()));
		OpenApiApp newOpenApiApp = openApiAppService.postOpenApiApp(app, multipartFile.getBytes());  //call to service class
		return new ResponseEntity<>(newOpenApiApp, OK);
	}

	@ApiOperation(value = "Update a OpenApiApp into system ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success|OK"),
	})
	@PutMapping
	/**
	 * call service layer to insert open api app, system will check for existing app
	 * if  app is NOT found, an exception is thrown
	 */
	public ResponseEntity<OpenApiApp> putOpenApiApp(@RequestParam("document") MultipartFile multipartFile, @RequestParam("app") String app) throws OpenApiAppNotFoundException, IOException {
		OpenApiApp newOpenApiApp = openApiAppService.putOpenApiApp(app, multipartFile.getBytes());  //call to service class
		return new ResponseEntity<>(newOpenApiApp, OK);
	}

	/**
	 * create a wrapper with HttpResponse object taking in the HttpStatus and a message
	 */
	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {

		return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
				message), httpStatus);
	}


}
