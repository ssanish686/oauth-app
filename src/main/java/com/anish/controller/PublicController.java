package com.anish.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @GetMapping("/get-key")
    public ResponseEntity<String> getKey()
    {
        return new ResponseEntity<>(jwtAccessTokenConverter.getKey().get("value"), HttpStatus.OK);
    }
}
