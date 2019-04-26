package com.spe.breadcrumbs.web;

import com.spe.breadcrumbs.dao.QuestionDAO;
import com.spe.breadcrumbs.dao.QuestionDbDAO;
import com.spe.breadcrumbs.entity.Question;
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

//class for making sure whether web mappings are working
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TestUserConfig.class
)
@AutoConfigureMockMvc
public class WebTests {
    @Autowired
    private MockMvc mockMvc;

    private QuestionDAO questionDAO = new QuestionDbDAO(new DBConnection());

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
        for(Question q: questions){
            this.mockMvc.perform(get("/management/breadcrumb/" + q.getId()))
                    .andExpect(status().isOk());
        }
    }


}
