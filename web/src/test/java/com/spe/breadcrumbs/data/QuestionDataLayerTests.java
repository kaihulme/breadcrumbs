package com.spe.breadcrumbs.data;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.*;
import com.spe.breadcrumbs.web.DBConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class QuestionDataLayerTests {
    @Mock
    private DBConnection dbConnection;

    private QuestionDAO questionDAO;
    private UserDAO userDAO;
    private ExpertDAO expertDAO;

    private TestConnection testConnection = new TestConnection();

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
        expertDAO = new ExpertDbDAO(dbConnection);
        questionDAO = new QuestionDbDAO(dbConnection);
    }

    @Test
    public void testGetHints(){
        List<Hint> hints = questionDAO.getHints(1L);
        assertNotNull(hints);
        Hint h = hints.get(0);
        assertEquals(h.getHintText(),"made of calcium");
        h = hints.get(1);
        assertEquals(h.getHintText(),"have a laminar appearance");
    }

}
