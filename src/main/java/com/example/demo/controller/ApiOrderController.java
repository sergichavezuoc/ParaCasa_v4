package com.example.demo.controller;

import java.time.LocalDateTime;
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
import com.example.demo.model.Menu;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;



@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/api/private")
public class ApiOrderController {

@Autowired
OrderRepository orderRepository;
@ApiOperation(value = "List Orders"
,notes = "List all available Orders: require token validation")
@GetMapping("/orders")
public ResponseEntity<List<Order>> getAllOrders() {
  try {
    List<Order> orders = new ArrayList<Order>();
      orderRepository.findAll().forEach(orders::add);

    if (orders.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(orders, HttpStatus.OK);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@ApiOperation(value = "Create Order"
,notes = "Require token validation")
@PostMapping("/private/orders")
public ResponseEntity<Order> createRestaurant(
  @ApiParam(
    name =  "user",
    value = "User Object",
    required = true)    
@RequestBody User user, 
@ApiParam(
  name =  "menu",
  value = "Menu Object",
  required = true) 
@RequestBody Menu menu) {
  try {
    Order _order = orderRepository.save(new Order(user, menu));
    return new ResponseEntity<>(_order, HttpStatus.CREATED);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@ApiOperation(value = "Modifify Order"
,notes = "Require validation")
@PutMapping("/private/orders/{id}")
public ResponseEntity<Order> updateOrder(
  @ApiParam(
    name =  "id",
    value = "User id",
    required = true)    
@PathVariable("id") long id, 
@ApiParam(
  name =  "dateAdded",
  value = "LocalDateTime",
  required = true)   
@RequestBody LocalDateTime dateAdded, 
@ApiParam(
  name =  "user",
  value = "User Object",
  required = true)    
@RequestBody User user) {
  Optional<Order> orderData = orderRepository.findById(id);

  if (orderData.isPresent()) {
    Order _order = orderData.get();
    _order.setUser(user);
    _order.setDateAdded(dateAdded);
    return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
  } else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
@ApiOperation(value = "Delete Orderr"
,notes = "Require validation")
@DeleteMapping("/private/orders/{id}")
public ResponseEntity<HttpStatus> deleteOrder(
  @ApiParam(
    name =  "id",
    value = "Order id",
    required = true)     
@PathVariable("id") long id) {
  try {
    orderRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
}