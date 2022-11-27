package com.practice.demo.controller;

import com.practice.demo.data.Response;
import com.practice.demo.data.Status;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    public Response handleBadCredentialsException(BadCredentialsException e){
        return new Response(null, new Status(401,"Authentication failed."));
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e){
        return new Response(null, new Status(500,"Could not process request please try again after sometime."));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public Response handleExpiredJwtException(ExpiredJwtException e){
        return new Response(null, new Status(402,"Security token expired."));
    }





}
