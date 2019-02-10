package com.spe.breadcrumbs.web;

import com.spe.breadcrumbs.dao.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class WebApplicationTests {
	//TODO add tests
	@Test
	public void contextLoads() {
	}

	public void testUsersNotNull(){
	}

	public void testGetUserId(){

	}

	public void testGetUserIdThrowsException(){

	}

	public void testAddUser(){

	}

	public void testAddUserDuplicateEmail(){

	}

	public void testAddUserDuplicateCode(){

	}

	public void testDeleteUser(){

	}

	public void testDeleteUserThrowsException(){

	}

}
