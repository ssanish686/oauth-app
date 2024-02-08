package com.anish.dto.response;

import lombok.Data;

@Data
public class ErrorResponse {

    private int status;
    private String error;
}
