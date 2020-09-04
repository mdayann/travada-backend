package com.travada.backend.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class BaseResponse {
    private HttpStatus status;
    private Object data;
    private String message;
}
