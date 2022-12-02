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

import com.example.demo.model.MenuType;
import com.example.demo.repository.MenuTypeRepository;



@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/api/private")
public class ApiMenuTypeController {

@Autowired
MenuTypeRepository menuTypeRepository;
@GetMapping("/menutypes")
public ResponseEntity<List<MenuType>> getAllMenuTypes(@RequestParam(required = false) String name) {
  try {
    List<MenuType> menutypes = new ArrayList<MenuType>();
      menuTypeRepository.findAll().forEach(menutypes::add);

    if (menutypes.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(menutypes, HttpStatus.OK);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@PostMapping("/private/menutypes")
public ResponseEntity<MenuType> createRestaurant(@RequestBody String type) {
  try {
    MenuType _menuType = menuTypeRepository.save(new MenuType(type));
    return new ResponseEntity<>(_menuType, HttpStatus.CREATED);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@PutMapping("/private/menutypes/{id}")
public ResponseEntity<MenuType> updateMenuType(@PathVariable("id") long id, @RequestBody String type) {
  Optional<MenuType> menuTypeData = menuTypeRepository.findById(id);

  if (menuTypeData.isPresent()) {
    MenuType _menuType = menuTypeData.get();
    _menuType.setType(type);
    return new ResponseEntity<>(menuTypeRepository.save(_menuType), HttpStatus.OK);
  } else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
@DeleteMapping("/private/menutypes/{id}")
public ResponseEntity<HttpStatus> deleteMenuType(@PathVariable("id") long id) {
  try {
    menuTypeRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
}