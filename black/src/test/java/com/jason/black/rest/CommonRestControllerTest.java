package com.jason.black.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * CommonRestController Tester.
 *
 * @author fuyongde
 * @version 1.0
 * @since <pre>4 12, 2017</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonRestControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void before() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getToken()
     */
    @Test
    public void testGetToken() throws Exception {
        this.mvc.perform(get("/api/commons/token"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


} 
