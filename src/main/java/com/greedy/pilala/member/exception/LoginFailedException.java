package com.greedy.pilala.member.exception;

public class LoginFailedException extends RuntimeException{

    public LoginFailedException(String msg){
        super(msg);
    }
}
