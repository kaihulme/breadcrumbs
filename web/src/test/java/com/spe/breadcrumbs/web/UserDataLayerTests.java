package com.spe.breadcrumbs.web;

import static org.assertj.core.api.Assertions.assertThat;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.OutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest

public class UserDataLayerTests {
	//TODO add ExpertDataTests
	//TODO add QuestionDataTests

	@Mock
	private DBConnection dbConnection;

	private UserDAO userDAO;
	private TestConnection testConnection = new TestConnection();
	private User testUser;
	@Before
	public void setUp() throws Exception{
		testConnection.executeSQLFile("createTables.sql");
		testConnection.executeSQLFile("questionData.sql");
		/*
		Database with the following data is made
		INSERT INTO Expert (firstName,lastName,email)
		VALUES ('Jane','Smith','Jane.Smith@hotmail.co.uk');

		INSERT INTO Quiz(title,expertId) VALUES('testQuiz',1);

		INSERT INTO User(firstName,lastName,email,code,score,quizId)
		VALUES ('John','Adam','JA@gmail.com','RAcJG',0,1),
		('Harry','Calway','HC@hotmail.com','3qRku',0,1),
		('Ashley','Johnson','AJ@yahoo.com','T5va9',0,1),
		('Loretta','Andrews','LA@gmail.com','N4Kax',0,1);
		 */
		assertNotNull(dbConnection);
		when(dbConnection.getConnection()).thenReturn(testConnection.getConnection());
		userDAO = new UserDbDAO(dbConnection);
//		System.setErr(new PrintStream(new OutputStream(){public void write(int i){}}));
		testUser = new User();
		testUser.setFirstName("Jane");
		testUser.setLastName("Andrews");
		testUser.setEmail("Jane.Andrews@gmail.com");
	}

    @Test
	public void testUsersNotNull(){
		assertNotNull(userDAO.getAllUsers());
	}

    @Test
	public void testGetUserId(){
		User u = userDAO.getUser(1L);
		assert(u.getId()== 1L);
	}

    @Test
	public void testGetUserIdNotFound(){
		assertNull(userDAO.getUser(5L));
	}

    @Test
	public void testGetUserByCode(){
		assert(userDAO.getByCode("RAcJG").getId() == 1L);
	}

    @Test
	public void testGetUserByCodeNotFound(){
		assertNull(userDAO.getByCode("efeg555"));
	}

	@Test
	public void testAddUser(){
		assert(userDAO.addUser(testUser));
		assert(userDAO.getUser(5L).getId() == 5L);
	}

	@Test
	public void testAddUserDuplicateEmail(){
		//a user with the following email exists
		//therefore she cannot be added to the database
		testUser.setEmail("JA@gmail.com");
		assert(!userDAO.addUser(testUser));
	}

	@Test
	public void testAddUserDuplicateCode(){
		testUser.setCode("RAcJG");
		assert(!userDAO.addUser(testUser));
	}



    @Test
	public void testDeleteUser(){
		assert(userDAO.deleteUser(1L));
		assertNull(userDAO.getUser(1L));
	}

	@Test
	public void testUpdateUser(){
		User u = userDAO.getUser(1L);
		//change their email
		u.setEmail("John.Adam@Yahoo.com");
		userDAO.update(1L,u);
		u = userDAO.getUser(1L);
		assertEquals(u.getEmail(),"John.Adam@Yahoo.com");
    }

    @Test
    public void testUpdateUserThrowsException(){
		User u = userDAO.getUser(1L);
		u.setCode("T5va9"); //as code has a unique constraint, it cannot be updated to this
		assert(!userDAO.update(1L,u));
    }

}
