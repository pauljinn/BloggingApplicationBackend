package com.enc.blog.services;

import java.util.List;

import com.enc.blog.entities.User;
import com.enc.blog.payloads.UserDto;

public interface UserService {
	//Create
	UserDto createUser(UserDto user);
	//Update
	UserDto updateUser(Long userId, UserDto newUser);
	//Get All
	List<UserDto> getAllUser();
	//Get by Id
	UserDto getUserById(Long userId);
	//Delete
	void deleteUser(Long userId);
}
