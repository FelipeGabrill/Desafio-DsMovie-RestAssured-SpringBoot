package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

public class ScoreControllerRA {
	
	private String adminUsername, adminPassword;
	private String adminToken;
	
	private String existingMovieId, nonExistingMovieId;
	
	private Map<String, Object> postScoreInstance;
	
	@BeforeEach
	void setUp() throws JSONException {
		baseURI = "http://localhost:8080";
		
		existingMovieId = "2";
		nonExistingMovieId = "1000";
		
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
		
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
		
		postScoreInstance = new HashMap<>();
		
		postScoreInstance.put("movieId", existingMovieId);
		postScoreInstance.put("score", "4");
	}
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {
		postScoreInstance.put("movieId", nonExistingMovieId);
		JSONObject newScore = new JSONObject(postScoreInstance);
		
		given()
			.header("Content-type", "application/json")
	    	.header("Authorization", "Bearer " + adminToken)
	    	.body(newScore)
	    	.contentType(ContentType.JSON)
	    	.accept(ContentType.JSON)
		.when()	
			.put("/scores")
		.then()
			.statusCode(404);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		postScoreInstance.put("movieId", "");
		JSONObject newScore = new JSONObject(postScoreInstance);
		
		given()
			.header("Content-type", "application/json")
	    	.header("Authorization", "Bearer " + adminToken)
	    	.body(newScore)
	    	.contentType(ContentType.JSON)
	    	.accept(ContentType.JSON)
		.when()	
			.put("/scores")
		.then()
			.statusCode(422)
			.body("errors.fieldName[0]", equalTo("movieId"))
			.body("errors.message[0]", equalTo("Campo requerido"));
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
		postScoreInstance.put("score", "-1");
		JSONObject newScore = new JSONObject(postScoreInstance);
		
		given()
			.header("Content-type", "application/json")
	    	.header("Authorization", "Bearer " + adminToken)
	    	.body(newScore)
	    	.contentType(ContentType.JSON)
	    	.accept(ContentType.JSON)
		.when()	
			.put("/scores")
		.then()
			.statusCode(422)
			.body("errors.fieldName[0]", equalTo("score"))
			.body("errors.message[0]", equalTo("Valor mínimo 0"));
	}
}
