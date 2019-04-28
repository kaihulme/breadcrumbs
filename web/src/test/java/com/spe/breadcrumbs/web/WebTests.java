package com.spe.breadcrumbs.web;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.entity.Meeting;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.User;
import com.spe.breadcrumbs.web.controller.MainController;
import net.bytebuddy.asm.Advice;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//TODO provide a mock db for these tests
//class for making sure whether web mappings are working
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestUserConfig.class
)
@AutoConfigureMockMvc
public class WebTests {
    @Autowired
    private MockMvc mockMvc;
    private DBConnection dbConnection = new DBConnection();
    private QuestionDAO questionDAO = new QuestionDbDAO(dbConnection);
    private UserDAO userDAO = new UserDbDAO(dbConnection);
    private ExpertDAO expertDAO = new ExpertDbDAO(dbConnection);
    private MeetingDAO meetingDAO = new MeetingDbDAO(dbConnection);


    @Autowired
    private MainController mainController;

    private String localhost = "http://localhost";

//    @BeforeEach
//    void setup(WebApplicationContext wac) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//    }

    @Test
    public void testRedirectsToLoginPage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl(localhost + "/login"));
    }

    @Test
    @WithUserDetails("user@test.com")
    public void testParticipantsPage() throws Exception{
        this.mockMvc.perform(get("/participants"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("jackSmith@hotmail.co.uk")
    public void testAccountPage() throws Exception{
        this.mockMvc.perform(get("/account"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("user@test.com")
    public void testManagementPageFails() throws Exception{
        this.mockMvc.perform(get("/management"))
                .andExpect(status().is(403));
    }

    @Test
    @WithUserDetails("jackSmith@hotmail.co.uk")
    public void testManagementPageSucceeds() throws Exception{
        this.mockMvc.perform(get("/management"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("jackSmith@hotmail.co.uk")
    public void testQuestionPagesAreLoading() throws Exception{
        List<Question> questions = questionDAO.getAllQuestions();
        Question q = questions.get(0);
        this.mockMvc.perform(get("/management/breadcrumb/" + q.getId()))
                .andExpect(status().isOk());

    }

    @Test
    @WithUserDetails("jackSmith@hotmail.co.uk")
    public void testUserPagesAreLoading() throws Exception{
        List<User> users = userDAO.getAllUsers();
        User u = users.get(0);
        this.mockMvc.perform(get("/management/user/" + u.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("jackSmith@hotmail.co.uk")
    public void testExpertPagesAreLoading() throws Exception{
        List<Expert> experts = expertDAO.getAllExperts();
        Expert e = experts.get(0);
        this.mockMvc.perform(get("/management/expert/" + e.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("jackSmith@hotmail.co.uk")
    public void testMeetingPagesAreLoading() throws Exception{
        List<Meeting> meetings = meetingDAO.getMeetings();
        Meeting m = meetings.get(0);
        this.mockMvc.perform(get("/management/meeting/" + m.getUser().getId() + "&" + m.getExpert().getId()))
                .andExpect(status().isOk());
    }


}