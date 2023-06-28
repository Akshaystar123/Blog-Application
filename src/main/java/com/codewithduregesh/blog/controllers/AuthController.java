package com.codewithduregesh.blog.controllers;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithduregesh.blog.exceptions.ApiException;
import com.codewithduregesh.blog.payloads.JwtAuthRequest;
import com.codewithduregesh.blog.payloads.JwtAuthResponse;
import com.codewithduregesh.blog.payloads.UserDto;
import com.codewithduregesh.blog.security.JwtTokenHelper;
import com.codewithduregesh.blog.services.UserServiceI;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

	@Autowired
 	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserServiceI userServiceI;

	private static Logger logger= LoggerFactory.getLogger(AuthController.class);

	
	/**
	 * @author Akshay 
	 * @apiNote create token
	 * @param JwtAuthRequest
	 * @return
	 */
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		this.authenticate(request.getUsername(), request.getPassword());
		
		logger.info("Initiating service call for create token"+request);

		UserDetails userDetail = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userDetail);

		JwtAuthResponse response = new JwtAuthResponse();

		response.setToken(token);
		
		response.setUser(this.modelMapper.map(userDetail, UserDto.class));
		
		logger.info("Completed service call for create token"+request);

		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
	}

	private void authenticate(String username, String password) throws Exception {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		logger.info("Initiating service call for authenticate"+username,password);

		try {
			this.authenticationManager.authenticate(authenticationToken);

		} catch (BadCredentialsException e) {

			System.out.println("Invalid details");
			
			throw new ApiException("Invalid username or password..!!");
		}
		
		logger.info("Completed service call for authenticate"+username,password);

	}
	
	/**
	 * @author Akshay 
	 * @apiNote Register User 
	 * @param UserDto
	 * @return
	 */
	
	
	//register new API
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
		
		logger.info("Initiating service call for register user"+userDto);

		UserDto registeredNewUser = userServiceI.registerNewUser(userDto);
		
		logger.info("Completed service call for register user"+userDto);

		return new ResponseEntity<UserDto>(registeredNewUser,HttpStatus.CREATED);	
	}
}
