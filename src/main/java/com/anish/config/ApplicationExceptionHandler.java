package com.anish.config;


import com.anish.dto.response.ErrorResponse;
import com.anish.exception.UnknownAuthorityException;
import com.anish.exception.UserAlreadyExistsException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public final ResponseEntity<ErrorResponse> handleException(UserAlreadyExistsException e)
    {
        ErrorResponse response = new ErrorResponse();
        response.setError("user_exists");
        response.setErrorDescription(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnknownAuthorityException.class)
    public final ResponseEntity<ErrorResponse> handleException(UnknownAuthorityException e)
    {
        ErrorResponse response = new ErrorResponse();
        response.setError("unknown_authority");
        response.setErrorDescription(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ErrorResponse> handleException(Exception e)
    {
        ErrorResponse response = new ErrorResponse();
        response.setError("internal_error");
        response.setErrorDescription(e.getMessage() != null? e.getMessage() : HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  @NonNull HttpHeaders  headers,
                                                                  @NonNull HttpStatus status,
                                                                  @NonNull WebRequest request)
    {
        ErrorResponse response = new ErrorResponse();
        response.setError("invalid_request");
        response.setErrorDescription(e.getFieldError() != null? e.getFieldError().getDefaultMessage()
                : HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
