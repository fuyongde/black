package com.jason.black.rest;

import com.jason.black.domain.param.RegisterParam;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        this.mvc.perform(get("/api/users/FBJEE40EB").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name").value("fuyongde"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * Method: register(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, UriComponentsBuilder uriBuilder)
     */
    @Test
    @Transactional
    @Rollback
    public void testRegister() throws Exception {
        RegisterParam registerParam = new RegisterParam();
        registerParam.setUsername(RandomStringUtils.random(8));
        registerParam.setPassword(RandomStringUtils.random(8));

        MvcResult tokenResult = mvc.perform(get("/api/commons/token")).andReturn();
        String token = tokenResult.getResponse().getHeader("Token");

        this.mvc.perform(post("/api/users")
                .header("Token", token)
                .content(registerParam.toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    @Rollback
    public void testAuthCode() throws Exception {
        this.mvc.perform(get("/api/users/FBJEE40EB/auth").param("email", "fuyongde@foxmail.com"))
                .andExpect(status().isNoContent())
                .andDo(MockMvcResultHandlers.print());
    }

}
