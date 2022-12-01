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

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;



@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/api/private")
public class ApiUserController {

@Autowired
UserRepository userRepository;
@GetMapping("/users")
public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String name) {
  try {
    List<User> users = new ArrayList<User>();
      userRepository.findAll().forEach(users::add);

    if (users.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(users, HttpStatus.OK);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@PostMapping("/private/users")
public ResponseEntity<User> createRestaurant(@RequestBody User user) {
  try {
    User _user = userRepository.save(new User(user.getName(), user.getSurname(), user.getUsername(), user.getEmail(), user.getPassword()));
    return new ResponseEntity<>(_user, HttpStatus.CREATED);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@PutMapping("/private/users/{id}")
public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
  Optional<User> userData = userRepository.findById(id);

  if (userData.isPresent()) {
    User _user = userData.get();
    _user.setName(user.getName());
    _user.setSurname(user.getSurname());
    return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
  } else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
@DeleteMapping("/private/users/{id}")
public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
  try {
    userRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
}