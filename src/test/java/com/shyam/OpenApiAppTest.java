package com.shyam;

import com.shyam.model.OpenApiApp;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OpenApiAppTest
{
    @LocalServerPort
    int randomServerPort;

    private static Long insertedId;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());


    @Test
    @Order(1)
    public void testPostOpenApiApp() throws URISyntaxException
    {
        //create a post request for an app
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:"+randomServerPort+"/openapiapp";
        URI uri = new URI(baseUrl);

        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("document", new ClassPathResource("openapi.json"));
        //map.add("document", new FileSystemResource("openapi.json"));
        map.add("app", "test8");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);


        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new    HttpEntity<LinkedMultiValueMap<String, Object>>(
                map, headers);
        ResponseEntity<OpenApiApp> result = restTemplate.postForEntity(uri, requestEntity, OpenApiApp.class);
        assertEquals(200, result.getStatusCodeValue());

        //update the app with new openapi using a post request.
        RestTemplate restTemplate2 = new RestTemplate();
        final String baseUrl2 = "http://localhost:"+randomServerPort+"/openapiapp";
        URI uri2 = new URI(baseUrl);

        LinkedMultiValueMap<String, Object> map2 = new LinkedMultiValueMap<>();
        map2.add("document", new ClassPathResource("openapi.json"));
        //map.add("document", new FileSystemResource("openapi.json"));
        map2.add("app", "test8");
        HttpHeaders headers2 = new HttpHeaders();
        headers2.setContentType(MediaType.MULTIPART_FORM_DATA);


        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity2 = new    HttpEntity<LinkedMultiValueMap<String, Object>>(
                map2, headers2);
        //ResponseEntity<OpenApiApp> result =
        restTemplate2.put(uri2, requestEntity2);
        //assertEquals(200, result.getStatusCodeValue());

    }

}