package com.codewithduregesh.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponse {

	private List<PostDto> contenet;
	private int pageNumber;
	private int pageSize;
	private long totalElement;
	private int  totalPages;
	private boolean lastPage;
	 
}
