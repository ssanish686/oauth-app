package com.anish.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorResponse {

    private String error;
    @JsonProperty("error_description")
    private String errorDescription;
}
