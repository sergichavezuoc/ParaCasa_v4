package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
 
@Entity
@Table(name = "users")
public class User {
 
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(position = 0 )
    private String username;
    @ApiModelProperty(position = 1 )
    private String password;
    @ApiModelProperty(position = 2 )
    private String role;
    @ApiModelProperty(position = 3 )
    private boolean enabled;
    @ApiModelProperty(position = 4 )
    private boolean premium;
    @ApiModelProperty(position = 5 )
    @Email(regexp=".*@.*\\..*", message = "Email should be valid")
    private String email;
    @ApiModelProperty(position = 6 )
    @NotNull(message = "Name cannot be null")
    private String name;
    @ApiModelProperty(position = 7 )
    private String surname;
    public User(){
        
    }
    public User(String name, String surname, String username, String email, String password) {
        this.username=username;
        this.name=name;
        this.surname=surname;
        this.password=password;
        this.email=email;
        this.enabled=true;
        this.premium=true;
        this.role="ROLE_USER";
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean isPremium() {
        return premium;
    }
    public void setPremium(boolean premium) {
        this.premium = premium;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
 

 
}