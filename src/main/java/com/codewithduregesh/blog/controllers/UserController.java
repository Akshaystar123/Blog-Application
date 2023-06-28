package com.codewithduregesh.blog.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithduregesh.blog.config.AppConstants;
import com.codewithduregesh.blog.payloads.ApiResponse;
import com.codewithduregesh.blog.payloads.UserDto;
import com.codewithduregesh.blog.services.UserServiceI;


@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserServiceI userServiceI;
	
	private static Logger logger= LoggerFactory.getLogger(UserController.class);

	/**
	 * @author Akshay 
	 * @apiNote cretae new user
	 * @param 
	 * @return
	 */
	
	// POST- create user

	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		
		logger.info("Initiating service call for creating user");

		UserDto createUserDto = this.userServiceI.createUser(userDto);
		
		logger.info("Completed service call of creating user");

		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);

	}

	/**
	 * @author Akshay 
	 * @apiNote update User
	 * @param userId
	 * @return
	 */
	
	// PUT- update user

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId") Long userId) {

		logger.info("Initiating service call for updating user"+userId);

		UserDto updateUser = this.userServiceI.updateUser(userDto, userId);
		
		logger.info("Initiating service call for updating user"+userId);

		return new ResponseEntity<UserDto>(updateUser, HttpStatus.OK);
	}
	/**
	 * @author Akshay 
	 * @apiNote delete User
	 * @param userId
	 * @return
	 */
	// ADMIN
	// DELETE- delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long userId) {
		
		logger.info("Initiating service call for deleteing user"+userId);

		this.userServiceI.deleteUser(userId);
		
		logger.info("Completed service call for deleted user"+userId);

		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.DELETE_USER, true), HttpStatus.OK);

	}
	/**
	 * @author Akshay 
	 * @apiNote get all User
	 * @param 
	 * @return
	 */
	// GET- get user

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser() {
		
		logger.info("Initiating service call for searching Alluser");

		List<UserDto> allUsers = this.userServiceI.getAllUsers();
		
		logger.info("Completetd service call for searched Alluser");

		return new ResponseEntity<List<UserDto>>(allUsers, HttpStatus.OK);

	}
	/**
	 * @author Akshay 
	 * @apiNote get User
	 * @param userId
	 * @return
	 */
	// GET- get user by id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable("userId") Long userId) {

		logger.info("Initiating service call for get single user"+userId);
		
		UserDto userById = this.userServiceI.getUserById(userId);
		
		logger.info("Completed service call for get single user"+userId);


		return new ResponseEntity<UserDto>(userById, HttpStatus.OK);

	}
}
