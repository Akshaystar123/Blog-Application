package com.codewithduregesh.blog.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithduregesh.blog.exceptions.*;
import com.codewithduregesh.blog.config.AppConstants;
import com.codewithduregesh.blog.entities.Role;
import com.codewithduregesh.blog.entities.User;
import com.codewithduregesh.blog.payloads.UserDto;
import com.codewithduregesh.blog.repositories.RoleRepositoryI;
import com.codewithduregesh.blog.repositories.UserRepositoryI;
import com.codewithduregesh.blog.services.UserServiceI;

@Service
public class UserServiceImpl implements UserServiceI {

	@Autowired
	private UserRepositoryI userRepositoryI;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepositoryI roleRepositoryI;

	@Override
	public UserDto createUser(UserDto userDto) {

		User dtoToUser = this.dtoToUser(userDto);
		User savedUser = this.userRepositoryI.save(dtoToUser);

		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long userId) {

		User user = this.userRepositoryI.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassward(userDto.getPassward());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepositoryI.save(user);
		UserDto userToDto1 = this.userToDto(updatedUser);
		return userToDto1;
	}

	@Override
	public UserDto getUserById(Long userId) {

		User user = this.userRepositoryI.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Long", userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepositoryI.findAll();

		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Long userId) {

		User user = this.userRepositoryI.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		this.userRepositoryI.delete(user);
	}

	public User dtoToUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		/*
		 * user.setId(userDto.getId()); user.setName(userDto.getName());
		 * user.setEmail(userDto.getEmail()); user.setPassward(userDto.getPassward());
		 * user.setAbout(userDto.getAbout());
		 */

		return user;

	}

	public UserDto userToDto(User user) {

		UserDto userDto = this.modelMapper.map(user, UserDto.class);

		/*
		 * userDto.setId(user.getId()); userDto.setName(user.getName());
		 * userDto.setEmail(user.getEmail()); userDto.setPassward(user.getPassward());
		 * userDto.setAbout(user.getAbout());
		 */

		return userDto;

	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);
		
		//encoded the password
		user.setPassward(this.passwordEncoder.encode(user.getPassward()));
		
		//roles
		Role role = this.roleRepositoryI.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);	
		
		User newUser = this.userRepositoryI.save(user);
		
		return this.modelMapper.map(newUser, UserDto.class);
	}

}
