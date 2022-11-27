package com.practice.demo.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private Object payload;
    private Status status;
}
