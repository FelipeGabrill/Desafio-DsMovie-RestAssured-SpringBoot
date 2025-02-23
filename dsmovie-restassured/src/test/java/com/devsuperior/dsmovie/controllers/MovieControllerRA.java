package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

public class MovieControllerRA {
	
	private String clientUsername, clientPassword, adminUsername, adminPassword;
	private String clientToken, adminToken, invalidToken;
	private String movieTitle;
	private Long existingMovieId, nonExistingMovieId;
	
	@BeforeEach
	void setUp() throws JSONException {
		baseURI = "http://localhost:8080";
		
		existingMovieId = 1L;
		nonExistingMovieId = 1000L;
		
		movieTitle = "matrix";
		
		clientUsername = "ana@gmail.com";
		clientPassword = "123456";
		
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
		
		clientToken = TokenUtil.obtainAccessToken(clientUsername, clientPassword);
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		invalidToken = adminToken + "xpto";
		
	}
	
	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
		
		given() 
			.get("/movies")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {		
		given() 
			.get("/movies?size=25&title={movieTitle}", movieTitle)
		.then()
			.statusCode(200)
			.body("content.id[0]", is(4))
			.body("content.title[0]", equalTo("Matrix Resurrections"))
			.body("content.score[0]", is(0.0F))
			.body("content.count[0]", is(0))
			.body("content.image[0]", equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/hv7o3VgfsairBoQFAawgaQ4cR1m.jpg"));	
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {	
		given() 
		.get("/movies/{id}", existingMovieId)
	.then()
		.statusCode(200)
		.body("id", is(1))
		.body("title", equalTo("The Witcher"))
		.body("score", is(4.5F))
		.body("count", is(2))
		.body("image", equalTo("https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg"));
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {	
		given() 
		.get("/movies/{id}", nonExistingMovieId)
	.then()
		.statusCode(404);
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() throws JSONException {		
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() throws Exception {
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() throws Exception {
	}
}
