package com.jason.black.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * UserRestController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>十一月 13, 2016</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRestControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    /**
     * Method: getUserById(@PathVariable("id") String id)
     */
    @Test
    public void testGetUserById() throws Exception {
    }

    /**
     * Method: register(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, UriComponentsBuilder uriBuilder)
     */
    @Test
    public void testRegister() throws Exception {
        this.mvc.perform(post("/api/users")
                .param("username", "fuyongde")
                .param("password", "fuyongde")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated());
//                .andExpect(header().string("Location", "http://localhost/api/users/99999999"));
    }


} 
