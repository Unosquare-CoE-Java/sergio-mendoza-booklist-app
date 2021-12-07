package com.training.booklist.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Builder
@Setter
public class UserDto {
    public String firstName;
    public String lastName;
    public String country;
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    public Date registrationDate;
    public String username;
    public String password;
    public String role;
}
