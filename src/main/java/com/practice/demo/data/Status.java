package com.practice.demo.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Status{
    private Integer responseCode;
    private String description;
}
