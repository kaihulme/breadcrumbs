package com.spe.breadcrumbs.web;

import com.spe.breadcrumbs.dao.*;
import com.spe.breadcrumbs.entity.Expert;
import com.spe.breadcrumbs.entity.Meeting;
import com.spe.breadcrumbs.entity.Question;
import com.spe.breadcrumbs.entity.User;
import com.spe.breadcrumbs.web.controller.MainController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//class for making sure whether web mappings are working
@RunWith(SpringJUnit4ClassRunner.class)
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
    private final String admin = "jackSmith@hotmail.co.uk";
    private final String basic = "EBach@Yahoo.com";

    @Test
    public void testRedirectsToLoginPage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl(localhost + "/login"));
    }

    @Test
    @WithUserDetails(basic)
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
    @WithUserDetails(basic)
    public void testManagementPageFails() throws Exception{
        this.mockMvc.perform(get("/management"))
                .andExpect(status().is(403));
    }

    @Test
    @WithUserDetails(admin)
    public void testManagementPageSucceeds() throws Exception{
        this.mockMvc.perform(get("/management"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(admin)
    public void testQuestionPagesAreLoading() throws Exception{
        List<Question> questions = questionDAO.getAllQuestions();
        Question q = questions.get(0);
        this.mockMvc.perform(get("/management/breadcrumb/" + q.getId()))
                .andExpect(status().isOk());

    }

    @Test
    @WithUserDetails(admin)
    public void testUserPagesAreLoading() throws Exception{
        List<User> users = userDAO.getAllUsers();
        User u = users.get(0);
        this.mockMvc.perform(get("/management/user/" + u.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(admin)
    public void testExpertPagesAreLoading() throws Exception{
        List<Expert> experts = expertDAO.getAllExperts();
        Expert e = experts.get(0);
        this.mockMvc.perform(get("/management/expert/" + e.getId()))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(admin)
    public void testMeetingPagesAreLoading() throws Exception{
        List<Meeting> meetings = meetingDAO.getMeetings();
        Meeting m = meetings.get(0);
        this.mockMvc.perform(get("/management/meeting/" + m.getUser().getId() + "&" + m.getExpert().getId()))
                .andExpect(status().isOk());
    }


}
