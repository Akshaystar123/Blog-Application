package com.codewithduregesh.blog.services.Impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.codewithduregesh.blog.entities.User;
import com.codewithduregesh.blog.payloads.UserDto;
import com.codewithduregesh.blog.repositories.UserRepositoryI;

@SpringBootTest(classes = UserServiceImplTest.class)
class UserServiceImplTest {

	@Mock
	private UserRepositoryI userRepositoryI;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private ModelMapper modelMapper;

	UserDto userDto;

	User user;

	@BeforeEach
	public void setUp() {
		
		when(modelMapper.map(any(),any())).thenReturn(userDto);
        userDto = new UserDto();
		userDto.setId((long) 1);
		userDto.setName("Akshay");
		userDto.setEmail("khaire11@gmail.com");
		userDto.setPassward("Ask");
		userDto.setAbout("I am Tester");
	}

	@Test
	public void getAllUsersTest() {

		List<User> list = new ArrayList<>();

		User user = new User();
		user.setId((long) 1);
		user.setName("Akshay");
		user.setEmail("khaire11@gmail.com");
		user.setPassward("Ask");
		user.setAbout("I am Tester");

		User user1 = new User();
		user1.setId((long) 2);
		user1.setName("Vishal");
		user1.setEmail("vishal201@gmail.com");
		user1.setPassward("Vsk");
		user1.setAbout("I am Java Developer");

		list.add(user);
		list.add(user1);

		when(userRepositoryI.findAll()).thenReturn(list);

		List<UserDto> allUsers = userServiceImpl.getAllUsers();

		int actualResult = allUsers.size();

		int expectedResult = 2;

		assertEquals(expectedResult, actualResult);

	}

	/*
	 * @Test public void createUserTest() {
	 * 
	 * UserDto userDto = new UserDto();
	 * 
	 * userDto.setId(1l); userDto.setName("Akshay");
	 * userDto.setEmail("khaire11@gmail.com"); userDto.setPassward("Ask");
	 * userDto.setAbout("I am Tester");
	 * 
	 * User mockUser = new User();
	 * 
	 * when(modelMapper.map(userDto, User.class)).thenReturn(mockUser);
	 * 
	 * when(userRepositoryI.save(mockUser)).thenReturn(mockUser);
	 * 
	 * 
	 * UserDto createUser = userServiceImpl.createUser(userDto);
	 * 
	 * String actualEmail = createUser.getEmail();
	 * 
	 * String expectedEmail = "khaire11@gmail.com";
	 * 
	 * assertEquals(expectedEmail, actualEmail);
	 * 
	 * }
	 */
	@Test
	public void getUserByIdTest() {

		Long userId = 1l;

		User user = new User();
		user.setId(userId);
		user.setName("Akshay");

		when(userRepositoryI.findById(userId)).thenReturn(Optional.of(user));

		UserDto userDto = userServiceImpl.getUserById(userId);

		/*
		 * String actualName = userById.getName();
		 * 
		 * String expectedName = "Akshay";
		 */

		assertEquals(userDto, modelMapper.map(user, UserDto.class));

	}

}
