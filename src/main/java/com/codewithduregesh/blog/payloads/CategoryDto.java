package com.codewithduregesh.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CategoryDto {

	private Long categoryId;
	
	@NotBlank
	@Size(min = 4, message = "Minimun size of Category title is 4")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10, message = "Minimun size of Category title is 10")
	private String categoryDescription;
}
