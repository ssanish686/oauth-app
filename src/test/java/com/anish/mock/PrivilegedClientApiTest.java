package com.anish.mock;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
// "addFilers = false" disable spring security for Mock Mvc
@EnableTransactionManagement
public class PrivilegedClientApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateUserApi() throws Exception {
        MockHttpServletResponse servletResponse =  this.mockMvc.perform(MockMvcRequestBuilders.post("/privileged-client/create-user")
                        .header("Content-Type","application/json")
                        .content("{\"userName\" : \"anishSS\", \"password\" : \"$Anish123456789\", \"roles\" : [\"USER_ROLE\"]}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse();
        String response = servletResponse.getContentAsString();
        Assertions.assertEquals("SUCCESS",response);
    }

    @Test
    public void testCreateUserWithInvalidUsername() throws Exception {
        MockHttpServletResponse servletResponse =  this.mockMvc.perform(MockMvcRequestBuilders.post("/privileged-client/create-user")
                        .header("Content-Type","application/json")
                        .content("{\"userName\" : \"$anishSS\", \"password\" : \"$Anish123456789\", \"roles\" : [\"USER_ROLE\"]}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse();
        String response = servletResponse.getContentAsString();
        Assertions.assertTrue(response.contains("UserName must be of 6 length with no special characters"),
                "Input Username is not validated");
    }

    @Test
    public void testCreateUserWithInvalidPassword() throws Exception {
        MockHttpServletResponse servletResponse =  this.mockMvc.perform(MockMvcRequestBuilders.post("/privileged-client/create-user")
                        .header("Content-Type","application/json")
                        .content("{\"userName\" : \"anishSS\", \"password\" : \"Anish123\", \"roles\" : [\"USER_ROLE\"]}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse();
        String response = servletResponse.getContentAsString();
        Assertions.assertTrue(response.contains("Password must be min 10 length, containing at least 1 uppercase, 1 lowercase, 1 special character and 1 digit"),
                "Input Password is not validated");
    }

    @Test
    public void testCreateUserWithEmptyRoles() throws Exception {
        MockHttpServletResponse servletResponse =  this.mockMvc.perform(MockMvcRequestBuilders.post("/privileged-client/create-user")
                        .header("Content-Type","application/json")
                        .content("{\"userName\" : \"anishSS\", \"password\" : \"$Anish123456789\", \"roles\" : []}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse();
        String response = servletResponse.getContentAsString();
        Assertions.assertTrue(response.contains("Roles must not be empty"),"Input Roles are not validated");
    }

}
