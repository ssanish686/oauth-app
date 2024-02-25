package com.anish.controller;

import com.anish.config.ApplicationExceptionHandler;
import com.anish.dto.response.ErrorResponse;
import com.anish.exception.UnknownAuthorityException;
import com.anish.exception.UserAlreadyExistsException;
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
    public void testUserAlreadyExistsExceptionResponse()
    {
        String error = "ERROR";
        ResponseEntity<ErrorResponse> errorResponse = test.handleException(new UserAlreadyExistsException(error));
        assertEquals(HttpStatus.CONFLICT,errorResponse.getStatusCode());
        assertEquals(error,errorResponse.getBody().getErrorDescription());
        assertEquals("user_exists",errorResponse.getBody().getError());
    }

    @Test
    public void testUnknownAuthorityExceptionResponse()
    {
        String error = "ERROR";
        ResponseEntity<ErrorResponse> errorResponse = test.handleException(new UnknownAuthorityException(error));
        assertEquals(HttpStatus.BAD_REQUEST,errorResponse.getStatusCode());
        assertEquals(error,errorResponse.getBody().getErrorDescription());
        assertEquals("unknown_authority",errorResponse.getBody().getError());
    }

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
