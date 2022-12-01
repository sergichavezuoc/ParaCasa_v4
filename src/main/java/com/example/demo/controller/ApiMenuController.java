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


@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/api")
public class ApiMenuController {

@Autowired
MenuRepository menuRepository;
@GetMapping("/menus")
public ResponseEntity<List<Menu>> getAllMenus(@RequestParam(required = false) String name) {
  try {
    List<Menu> menus = new ArrayList<Menu>();
    if (name == null)
      menuRepository.findAll().forEach(menus::add);
    else
      menuRepository.findByNameContaining(name).forEach(menus::add);

    if (menus.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(menus, HttpStatus.OK);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@PostMapping("/private/menus")
public ResponseEntity<Menu> createRestaurant(@RequestBody Menu menu) {
  try {
    Menu _menu = menuRepository.save(new Menu(menu.getName(), menu.getDescription()));
    return new ResponseEntity<>(_menu, HttpStatus.CREATED);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@PutMapping("/private/menus/{id}")
public ResponseEntity<Menu> updateMenu(@PathVariable("id") long id, @RequestBody Menu menu) {
  Optional<Menu> menuData = menuRepository.findById(id);

  if (menuData.isPresent()) {
    Menu _menu = menuData.get();
    _menu.setName(menu.getName());
    _menu.setDescription(menu.getDescription());
    return new ResponseEntity<>(menuRepository.save(_menu), HttpStatus.OK);
  } else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
@DeleteMapping("/private/menus/{id}")
public ResponseEntity<HttpStatus> deleteMenu(@PathVariable("id") long id) {
  try {
    menuRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
}