package com.practice.demo.utils;

import com.practice.demo.data.Response;
import com.practice.demo.data.Status;

public class ResponseHandler {

    public static Response getResponse(Object obj, Status status){
        return new Response(obj,status);
    }

    public static Status getReponseStatus(){
        return new Status(200, "Request processed.");
    }
}
