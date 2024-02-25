package com.anish.mock;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
@EnableTransactionManagement
public class OauthApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBearerToken() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token")
                .header("Authorization", "Basic dGVzdC1hZG1pbjphZG1pbjEyMw==")//test-admin:admin123
                .header("Content-Type", "application/x-www-form-urlencoded").content("grant_type=client_credentials")).andReturn();
        String response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> responseMap = objectMapper.readValue(response, new TypeReference<HashMap<String, String>>(){});
        assertNotNull(responseMap.get("access_token"),"Access Token should not be null");
    }
}
