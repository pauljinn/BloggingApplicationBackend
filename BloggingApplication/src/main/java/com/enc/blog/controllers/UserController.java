package com.enc.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enc.blog.entities.User;
import com.enc.blog.payloads.UserDto;
import com.enc.blog.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	UserService userService;
	
	//Create 
	@PostMapping("/")
	public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto user){
		UserDto created = userService.createUser(user);
		ResponseEntity<UserDto> response = ResponseEntity.ok(created);
		return response;
	}
	//Update
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> update(@PathVariable("userId") String id, @Valid @RequestBody UserDto newUser){
		UserDto updatedUser = userService.updateUser(Long.parseLong(id), newUser);
		ResponseEntity<UserDto> response = ResponseEntity.ok(updatedUser);
		return response;
	}
	
	//Find All
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> findAllUser(){
		List<UserDto> allUsers = userService.getAllUser();
		return new ResponseEntity<List<UserDto>>(allUsers,HttpStatus.OK);
	}
	
	//Find by Id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> findUserById(@PathVariable String userId){
		UserDto userFound = userService.getUserById(Long.parseLong(userId));
		ResponseEntity<UserDto> response = new ResponseEntity<UserDto>(userFound, HttpStatus.FOUND);
		return response;
	}
	
	//Delete
	@DeleteMapping("/{userId}")
	public ResponseEntity<Map> deleteUser(@PathVariable String userId){
		userService.deleteUser(Long.parseLong(userId));
		return ResponseEntity.ok(Map.of("message","User deleted successfully"));
	}
}
