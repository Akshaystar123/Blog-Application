package com.codewithduregesh.blog.payloads;


import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.codewithduregesh.blog.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {

	
	private Long id;
	
	@NotEmpty
	@Size(min = 4, message = "Username must be min 4 characters")
	private String name;
	
	@Email(message = "Email address is ot valid..!!")
	private String email;
	
	@NotEmpty
	@Size(min = 3, max = 10, message = "Passward must be min 3 and max 10 chaacters..!!")
	private String passward;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
	
	
}
