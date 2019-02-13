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

public class UserApiTests {
	//TODO add tests
	@Test
	public void contextLoads() {
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
