package com.codewithduregesh.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codewithduregesh.blog.entities.User;
import com.codewithduregesh.blog.exceptions.ResourceNotFoundException;
import com.codewithduregesh.blog.repositories.UserRepositoryI;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepositoryI userRepositoryI;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// loading user from database by username
		User user = this.userRepositoryI.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "email", username));
		
		return user;
	}

}
