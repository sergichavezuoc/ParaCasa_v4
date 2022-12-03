package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Menu;
import com.example.demo.model.Restaurant;
import com.example.demo.repository.MenuRepository;
import com.example.demo.repository.RestaurantRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/api")
public class ApiRestaurantController {

@Autowired
RestaurantRepository restaurantRepository;
@ApiOperation(value = "List Restaurants"
,notes = "List all available Restaurants: public")
@GetMapping("/restaurants")
public ResponseEntity<List<Restaurant>> getAllRestaurants(
  @ApiParam(
    name =  "name",
    type = "String",
    value = "Word in restaurant name",
    example = "sol naciente",
    required = false)     
@RequestParam(required = false) String name) {
  try {
    List<Restaurant> restaurants = new ArrayList<Restaurant>();
    if (name == null)
      restaurantRepository.findAll().forEach(restaurants::add);
    else
      restaurantRepository.findByNameContaining(name).forEach(restaurants::add);

    if (restaurants.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(restaurants, HttpStatus.OK);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@ApiOperation(value = "Create Restaurant"
,notes = "Require token validation")
@PostMapping("/private/restaurants")
public ResponseEntity<Restaurant> createRestaurant(  
  @ApiParam(
    name =  "restaurant",
    value = "Restaurant Object",
    required = true)   
  @RequestBody Restaurant restaurant) {
  try {
    Restaurant _restaurant = restaurantRepository
        .save(new Restaurant(restaurant.getName(), restaurant.getDescription(), false));
    return new ResponseEntity<>(_restaurant, HttpStatus.CREATED);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@PutMapping("/private/restaurants/{id}")
public ResponseEntity<Restaurant> updateRestaurant(
  @ApiParam(
    name =  "id",
    value = "Restaurant id",
    required = true)   
@PathVariable("id") long id, 
@ApiParam(
  name =  "menu",
  value = "Menu Object",
  required = true)   
@RequestBody Restaurant restaurant) {
  Optional<Restaurant> restaurantData = restaurantRepository.findById(id);

  if (restaurantData.isPresent()) {
    Restaurant _restaurant = restaurantData.get();
    _restaurant.setName(restaurant.getName());
    _restaurant.setDescription(restaurant.getDescription());
    return new ResponseEntity<>(restaurantRepository.save(_restaurant), HttpStatus.OK);
  } else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
@DeleteMapping("/private/restaurants/{id}")
public ResponseEntity<HttpStatus> deleteRestaurant(
  @ApiParam(
    name =  "id",
    value = "Restaurant id",
    required = true)     
@PathVariable("id") long id) {
  try {
    restaurantRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
}