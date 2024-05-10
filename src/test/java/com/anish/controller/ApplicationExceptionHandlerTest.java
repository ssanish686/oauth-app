package com.anish.controller;

import com.anish.config.ApplicationExceptionHandler;
import com.anish.dto.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ApplicationExceptionHandlerTest {

    @InjectMocks
    private ApplicationExceptionHandler test;


    @Test
    public void testExceptionResponse()
    {
        String error = "ERROR";
        ResponseEntity<ErrorResponse> errorResponse = test.handleException(new Exception(error));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,errorResponse.getStatusCode());
        assertEquals(error,errorResponse.getBody().getErrorDescription());
        assertEquals("internal_error",errorResponse.getBody().getError());
    }

    @Test
    public void testEmptyExceptionResponse()
    {
        ResponseEntity<ErrorResponse> errorResponse = test.handleException(new Exception());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,errorResponse.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),errorResponse.getBody().getErrorDescription());
        assertEquals("internal_error",errorResponse.getBody().getError());
    }

}
