package com.anish;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

public class TestUtil {

    public static String getBearerToken(final MockMvc mockMvc) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/token").header("Authorization", "Basic dGVzdC1hZG1pbjphZG1pbjEyMw==")
                .header("Content-Type", "application/x-www-form-urlencoded").content("grant_type=client_credentials")).andReturn();
        result.getResponse();
        String response = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> responseMap = objectMapper.readValue(response, new TypeReference<HashMap<String, String>>(){});
        return responseMap.get("access_token");

    }
}
