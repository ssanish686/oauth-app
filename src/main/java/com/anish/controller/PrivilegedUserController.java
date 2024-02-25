package com.anish.controller;

import com.anish.dto.request.UserCreationRequestDto;
import com.anish.service.user.UserService;
import com.anish.validation.UserCreationValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/privileged-client")
@Slf4j
public class PrivilegedUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserCreationValidation userCreationValidation;

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserCreationRequestDto requestDto)
    {
        userCreationValidation.validate(requestDto);
        userService.createUser(requestDto);
        return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
    }

}
