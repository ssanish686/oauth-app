package com.anish.controller;

import com.anish.dto.request.UserCreationRequestDto;
import com.anish.service.user.UserService;
import com.anish.validation.UserCreationValidation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PrivilegedUserControllerTest {

    @InjectMocks
    private PrivilegedUserController test;
    @Mock
    private UserService userService;
    @Mock
    private UserCreationValidation userCreationValidation;


    @Test
    public void testCreateUser()
    {
        ResponseEntity<String>  response  = test.createUser(createStub());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody());
    }

    private UserCreationRequestDto createStub()
    {
        UserCreationRequestDto dto = new UserCreationRequestDto();
        dto.setPassword("password");
        dto.setUserName("username");
        dto.setRoles(List.of("role"));
        return dto;
    }

}
