package com.shyam.service;

import com.shyam.exception.InvalidOpenApiAppJsonException;
import com.shyam.exception.OpenApiAppFoundException;
import com.shyam.exception.OpenApiAppNotFoundException;
import com.shyam.model.OpenApiApp;

import com.shyam.repository.OpenApiAppRepository;
import io.swagger.models.Swagger;
import io.swagger.parser.SwaggerParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenApiAppServiceImpl implements OpenApiAppService {

	OpenApiAppRepository openApiAppRepository;
	Logger logger = LoggerFactory.getLogger("OpenApiAppServiceImpl");

	@Autowired
	public OpenApiAppServiceImpl(OpenApiAppRepository openApiAppRepository) {
		this.openApiAppRepository = openApiAppRepository;
	}

	@Override
	public OpenApiApp getLatestOpenApiApp(String app) throws OpenApiAppNotFoundException {
		OpenApiApp openApiApp = openApiAppRepository.getLatestOpenApiApp(app);
		if (openApiApp == null)
			throw new OpenApiAppNotFoundException("No prior records of the app " + app);
		return openApiApp;
	}

	/**
	public List<OpenApiApp> getAllOpenApiApp() {
		List<OpenApiApp> openApiApps = openApiAppRepository.findAll();;
		return openApiApps;
	}
	 **/

	@Override
	public OpenApiApp postOpenApiApp(String app, byte[] openApiAppJsonBytes) throws Exception {
		OpenApiApp existingOpenApiApp = openApiAppRepository.getLatestOpenApiApp(app);
		if (existingOpenApiApp != null)
			throw new OpenApiAppFoundException("App is already registered in system, try put operation, existing app is " + existingOpenApiApp.getApp());

		Swagger swagger = new SwaggerParser().parse(new String(openApiAppJsonBytes));
		//if (swagger == null){
		//	logger.info("swagger instance is null, problem with the input file?");
		//	throw new InvalidOpenApiAppJsonException("Invalid file sent, unable to parse openApiAppJson");
		//}


		OpenApiApp openApiApp = new OpenApiApp();
		openApiApp.setVersion(1);
		openApiApp.setApp(app);
		openApiApp.setOpenApiJson(new String(openApiAppJsonBytes));
		OpenApiApp newOpenApiApp = openApiAppRepository.save(openApiApp);
		return newOpenApiApp;
	}

	@Override
	public OpenApiApp putOpenApiApp(String app, byte[] openApiAppJsonBytes) throws OpenApiAppNotFoundException {
		OpenApiApp existingOpenApiApp = openApiAppRepository.getLatestOpenApiApp(app);
		if (existingOpenApiApp == null)
			throw new OpenApiAppNotFoundException("App is NOT registered in system, try post operation first, app is " + app);

		OpenApiApp openApiApp = new OpenApiApp();
		openApiApp.setVersion(existingOpenApiApp.getVersion()+1);
		openApiApp.setApp(app);
		openApiApp.setOpenApiJson(new String(openApiAppJsonBytes));
		OpenApiApp newOpenApiApp = openApiAppRepository.save(openApiApp);
		return newOpenApiApp;
	}

	/**
	@Override
	public OpenApiApp findMovieById(long id) throws MovieNotFoundException {
		Optional<OpenApiApp> movie = openApiAppRepository.findById(id);

		if (!movie.isPresent()) throw new MovieNotFoundException("Movie Not Found");

		return movie.get();
	}

	@Override
	public Integer update(OpenApiApp openApiApp) throws MovieNotFoundException {

		Optional<OpenApiApp> existingMovie = openApiAppRepository.findById(openApiApp.getId());
		if (!existingMovie.isPresent()) throw new MovieNotFoundException("Movie Not Found, so cannot be updated");

		Integer numRows = openApiAppRepository.updateMovie(openApiApp.getId(), openApiApp.getTitle(), openApiApp.getCategory(), openApiApp.getVersion());
		return numRows;
	}

	@Override
	public void deleteMovie(Long id) throws MovieNotFoundException{
		Optional<OpenApiApp> existingMovie = openApiAppRepository.findById(id);
		if (!existingMovie.isPresent()) throw new MovieNotFoundException("Movie Not Found, so cannot be deleted");


		openApiAppRepository.deleteById(id);
	}

	**/
}