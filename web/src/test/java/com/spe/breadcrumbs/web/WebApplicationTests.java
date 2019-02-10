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

public class WebApplicationTests {
	@Mock
	private UserDAO userDAO;

	@Autowired
	private MockMvc mvc;
	@Test
	public void contextLoads() {
	}

	public void testUsersNotNull() throws Exception{
		this.mvc.perform(get("/api/users"))
				.andExpect(status().isOk())
				.andExpect(content().string("f"));
	}

}
