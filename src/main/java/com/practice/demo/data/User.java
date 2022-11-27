package com.practice.demo.data;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@ToString

@Document(collection = "user")
public class User {

    @Id
    private Integer id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private List<UserRole> roles;

}
