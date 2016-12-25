package za.co.dladle.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import za.co.dladle.Sandbox;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Created by prady on 11/26/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Sandbox.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                new UserController()
        ).build();
    }

    @Test
    public void getHello() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/hello")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().string("hello"));
    }

}
