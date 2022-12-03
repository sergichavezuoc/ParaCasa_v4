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
import com.example.demo.model.MenuType;
import com.example.demo.repository.MenuTypeRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;



@CrossOrigin(origins = "http://localhost:8081")
@Controller
@RequestMapping("/api/private")
public class ApiMenuTypeController {

@Autowired
MenuTypeRepository menuTypeRepository;
@ApiOperation(value = "List Types"
,notes = "List all available Restaurant Types: require token validation")
@GetMapping("/menutypes")
public ResponseEntity<List<MenuType>> getAllMenuTypes() {
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
@ApiOperation(value = "Create Type"
,notes = "Require token validation")
@PostMapping("/private/menutypes")
public ResponseEntity<MenuType> createMenuType(
  @ApiParam(
    name =  "type",
    value = "Menu type String",
    required = true)  
@RequestBody String type) {
  try {
    MenuType _menuType = menuTypeRepository.save(new MenuType(type));
    return new ResponseEntity<>(_menuType, HttpStatus.CREATED);
  } catch (Exception e) {
    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@ApiOperation(value = "Modify Type"
,notes = "Require token validation")
@PutMapping("/private/menutypes/{id}")
public ResponseEntity<MenuType> updateMenuType(
  @ApiParam(
    name =  "id",
    value = "MenuType id",
    required = true)    
@PathVariable("id") long id, 
  @ApiParam(
    name =  "type",
    value = "Menu type String",
    required = true)  
@RequestBody String type) {
  Optional<MenuType> menuTypeData = menuTypeRepository.findById(id);

  if (menuTypeData.isPresent()) {
    MenuType _menuType = menuTypeData.get();
    _menuType.setType(type);
    return new ResponseEntity<>(menuTypeRepository.save(_menuType), HttpStatus.OK);
  } else {
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
}
@ApiOperation(value = "Delete Type"
,notes = "Require token validation")
@DeleteMapping("/private/menutypes/{id}")
public ResponseEntity<HttpStatus> deleteMenuType(
  @ApiParam(
    name =  "id",
    value = "MenuType id",
    required = true)      
@PathVariable("id") long id) {
  try {
    menuTypeRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  } catch (Exception e) {
    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
}