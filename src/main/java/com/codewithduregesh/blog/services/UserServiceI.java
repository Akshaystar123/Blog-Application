package com.codewithduregesh.blog.services;

import java.util.List;

import com.codewithduregesh.blog.payloads.UserDto;

public interface UserServiceI {
	
	UserDto registerNewUser(UserDto user);

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user,  Long userId);

	UserDto getUserById(Long userId);

	List<UserDto> getAllUsers();

	void deleteUser(Long userId);

}
