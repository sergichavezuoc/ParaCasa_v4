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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Menu;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;



@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/api/private")
public class ApiOrderController {

@Autowired
OrderRepository orderRepository;
@GetMapping("/orders")
public ResponseEntity<List<Order>> getAllOrders(@RequestParam(required = false) String name) {
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
@PostMapping("/private/orders")
public ResponseEntity<Order> createRestaurant(@RequestBody User user, @RequestBody Menu menu) {
  try {
    Order _order = orderRepository.save(new Order(user, menu));
    return new ResponseEntity<>(_order, HttpStatus.CREATED);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@PutMapping("/private/orders/{id}")
public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @RequestBody LocalDateTime dateAdded, @RequestBody User user) {
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
@DeleteMapping("/private/orders/{id}")
public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") long id) {
  try {
    orderRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
}