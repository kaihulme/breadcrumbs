package com.spe.breadcrumbs.data;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.entity.Hint;
import com.spe.breadcrumbs.entity.Meeting;
import com.spe.breadcrumbs.entity.User;
import com.spe.breadcrumbs.web.DBConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Time;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HintDataLayerTests {

    @Mock
    private DBConnection dbConnection;

    private HintDAO hintDAO;
    private QuestionDAO questionDAO;

    private List<Hint> hints;
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
        hintDAO = new HintDbDAO(dbConnection);
        questionDAO = new QuestionDbDAO(dbConnection);
        hints = questionDAO.getHints(1L);
    }

    @Test
    public void testGetHintByCode(){
        Hint h = hintDAO.getHintByCode("HR4FE");
        assertEquals(h.getHintText(),"made of calcium");
    }
}
