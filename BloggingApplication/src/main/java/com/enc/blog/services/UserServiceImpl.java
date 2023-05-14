package com.enc.blog.services;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enc.blog.entities.User;
import com.enc.blog.exceptions.ResourceNotFoundException;
import com.enc.blog.payloads.UserDto;
import com.enc.blog.repositories.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo userRepository;
	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User createdUser = userRepository.save(modelMapper.map(userDto, User.class));
		UserDto createdUserDto = modelMapper.map(createdUser, UserDto.class);
		return createdUserDto;
	}

	@Override
	public UserDto updateUser(Long userId, UserDto newUserDto) {
		boolean userPresent = userExist(userId);
		if(userPresent) {
//			System.out.println("Here");
//			User existedUser = userRepository.findById(userId).get();
//			existedUser.setName(newUser.getName());
//			existedUser.setAbout(newUser.getAbout());
//			existedUser.setEmail(newUser.getEmail());
//			existedUser.setPassword(newUser.getPassword());
			newUserDto.setId(userId);
			return createUser(newUserDto);
		}
		else {
			throw new ResourceNotFoundException("User with ID " + userId + " not present");
		}
	}

	@Override
	public List<UserDto> getAllUser() {
		List<User> allUsers = userRepository.findAll();
		List<UserDto> allUsersDto = Arrays.asList(modelMapper.map(allUsers,UserDto[].class));
		return allUsersDto;
	}

	@Override
	public UserDto getUserById(Long userId) {
		User userFound = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with ID " + userId + " not present"));
		return modelMapper.map(userFound, UserDto.class);
	}

	@Override
	public void deleteUser(Long userId) {
		boolean userPresent = userExist(userId);
		if(userPresent) {
			userRepository.deleteById(userId);
		}
		else {
			throw new ResourceNotFoundException("User with ID " + userId + " not present");
		}
	}
	
	public boolean userExist(Long userId) {
		return userRepository.existsById(userId);
	}

}
