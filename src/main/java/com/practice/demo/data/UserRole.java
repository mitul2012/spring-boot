package com.practice.demo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class UserRole {

    @Id
    private Integer userId;
    private String role;
}
