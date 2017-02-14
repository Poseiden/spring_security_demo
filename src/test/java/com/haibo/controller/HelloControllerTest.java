package com.haibo.controller;

import com.haibo.config.UserPrivilege;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void should_return_success_when_request_for_hello_admin_with_admin_authority() throws Exception{
        List<String> privilegeCodes = new ArrayList<String>(){{ add("ROLE_ADMIN");}};
        UserPrivilege userPrivilege = new UserPrivilege("test", privilegeCodes);
        SecurityContextHolder.getContext().setAuthentication(
                new PreAuthenticatedAuthenticationToken(userPrivilege , null)
        );

        mockMvc.perform(get("/hello/admin"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_access_denied_when_request_for_hello_admin_with_user_authority() throws Exception{
        expectedException.expect(NestedServletException.class);
        expectedException.expectMessage("Request processing failed; nested exception is org.springframework.security.access.AccessDeniedException: Access is denied");

        List<String> privilegeCodes = new ArrayList<String>() {{ add("ROLE_USER"); }};
        UserPrivilege userPrivilege = new UserPrivilege("test", privilegeCodes);
        SecurityContextHolder.getContext().setAuthentication(
                new PreAuthenticatedAuthenticationToken(userPrivilege, null)
        );

        mockMvc.perform(get("/hello/admin"))
                .andExpect(status().isForbidden());
    }
}