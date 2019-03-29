package com.spe.breadcrumbs.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.Choice;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.User;
import com.spe.breadcrumbs.web.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest

public class UserApiTests {
	//TODO add tests
	@Autowired
	private UserController userController;
	@Before
	public void setUp() throws Exception{
	}

	@Test
	public void contextLoads() {
		assertThat(userController).isNotNull();
	}
    @Test
	public void testUsersNotNull(){
	}

    @Test
	public void testGetUserId(){
	}

    @Test
	public void testGetUserIdThrowsException(){

	}

    @Test
	public void testGetUserByCode(){

	}

    @Test
	public void testGetUserByCodeThrowsException(){

	}

    @Test
	public void testAddUser(){

	}

    @Test
	public void testAddUserDuplicateEmail(){

	}

    @Test
	public void testAddUserDuplicateCode(){

	}

    @Test
	public void testDeleteUser(){

	}

    @Test
	public void testDeleteUserThrowsException(){

	}

	@Test
	public void testUpdateUser(){

    }

    @Test
    public void tesUpdateUserThrowsException(){

    }

}
