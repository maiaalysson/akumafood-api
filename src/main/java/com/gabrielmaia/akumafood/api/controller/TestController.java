package com.gabrielmaia.akumafood.api.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielmaia.akumafood.domain.model.Restaurant;
import com.gabrielmaia.akumafood.domain.repository.RestaurantRepository;

@RestController
@RequestMapping(value = "/tests", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
	
	@Autowired
	private RestaurantRepository repositoryRestaurant;
	
	@GetMapping("/restaurantes/por-nome-e-frete")
	List<Restaurant> resultFind(String name,  BigDecimal initialFee, BigDecimal finalFee){
		return repositoryRestaurant.find(name, initialFee, finalFee);
	}
	
}
