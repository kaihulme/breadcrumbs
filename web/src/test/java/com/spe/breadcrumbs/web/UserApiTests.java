package com.spe.breadcrumbs.web;

import static com.spe.breadcrumbs.web.DBConnection.getConnection;
import static org.assertj.core.api.Assertions.assertThat;

import com.spe.breadcrumbs.dao.AttemptDAO;
import com.spe.breadcrumbs.dao.AttemptDbDAO;
import com.spe.breadcrumbs.dao.UserDAO;
import com.spe.breadcrumbs.dao.UserDbDAO;
import com.spe.breadcrumbs.entity.Choice;
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
	@Mock
	private DataSource dataSource;
	@Mock
	private UserDbDAO userDAO;
	@Mock
	private DBConnection dbConnection;
	@Mock
	private Connection con;
	@Mock
	private ResultSet rs;
	@Mock
	private PreparedStatement stmt;
	private AttemptDAO attemptDAO;

	private User user;
	@Before
	public void setUp() throws Exception{
		assertNotNull(dbConnection);
		when(con.prepareStatement(any(String.class))).thenReturn(stmt);
		when(dataSource.getConnection()).thenReturn(con);

		user = new User();
		user.setId(Integer.toUnsignedLong(1));
		user.setFirstName("Jane");
		user.setLastName("Test");

		when(rs.first()).thenReturn(true);
		when(rs.getLong(1)).thenReturn(Integer.toUnsignedLong(1));
		when(rs.getString(2)).thenReturn(user.getFirstName());
		when(rs.getString(3)).thenReturn(user.getLastName());
		when(stmt.executeQuery()).thenReturn(rs);
		userDAO = new UserDbDAO();
		attemptDAO = new AttemptDbDAO();
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
//		userDAO.addUser(user);
//		User u = userDAO.getUser(1L);
//		assertEquals(user,u);
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
