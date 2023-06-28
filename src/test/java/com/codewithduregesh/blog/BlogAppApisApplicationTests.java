package com.codewithduregesh.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.codewithduregesh.blog.repositories.UserRepositoryI;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private UserRepositoryI userRepositoryI;

	@Test
	void contextLoads() {
	}

	@Test
	public void repoTest() {

		String name = this.userRepositoryI.getClass().getName();
		Package package1 = this.userRepositoryI.getClass().getPackage();
		
		System.out.println(name);
		System.out.println(package1);
	}

}
