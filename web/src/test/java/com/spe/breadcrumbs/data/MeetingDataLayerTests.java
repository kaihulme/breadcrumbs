package com.spe.breadcrumbs.data;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.entity.Meeting;
import com.spe.breadcrumbs.entity.User;
import com.spe.breadcrumbs.web.DBConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Time;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class MeetingDataLayerTests {
    @Mock
    private DBConnection dbConnection;

    private MeetingDAO meetingDAO;

    private TestConnection testConnection = new TestConnection();
    private Meeting testMeeting;
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
		UserDAO userDAO;
        ExpertDAO expertDAO;
        assertNotNull(dbConnection);
        when(dbConnection.getConnection()).thenReturn(testConnection.getConnection());
        meetingDAO = new MeetingDbDAO(dbConnection);
        userDAO = new UserDbDAO(dbConnection);
        expertDAO = new ExpertDbDAO(dbConnection);

        User u = userDAO.getUser(1L);
        Expert e = expertDAO.getExpert(1L);
        Time t = new Time(1557448200000L);
        String loc = "Meeting Room A";
        testMeeting = new Meeting(e,u,t,loc,false);
        assert(meetingDAO.createMeeting(testMeeting));
    }

    @Test
    public void testDeleteMeeting(){
        assertNotNull(meetingDAO.getMeeting(testMeeting.getUser().getId()));
        assert(meetingDAO.deleteMeeting(testMeeting.getUser().getId()));
        assertNull(meetingDAO.getMeeting(testMeeting.getUser().getId()));
    }

    @Test
    public void testUpdateMeeting(){
        assertEquals(testMeeting.getLocation(),"Meeting Room A");
        testMeeting.setLocation("Cafe");
        assert(meetingDAO.updateMeeting(testMeeting.getUser().getId(),testMeeting));
        Meeting m = meetingDAO.getMeeting(testMeeting.getUser().getId());
        assertNotNull(m);
        assertEquals(m.getLocation(),"Cafe");
    }
}
