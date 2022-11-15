package com.greedy.pilala.exception.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@ToString
public class ApiExceptionDto {
    private int state;
    private String message;

    public ApiExceptionDto(HttpStatus status, String message){
        this.state = status.value();
        this.message = message;
    }
}
