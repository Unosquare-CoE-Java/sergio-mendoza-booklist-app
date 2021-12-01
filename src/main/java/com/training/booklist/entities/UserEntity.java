package com.training.booklist.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "FirstName")
    public String firstName;

    @Column(name = "LastName")
    public String lastName;

    @Column(name = "Country")
    public String country;

    @Column(name = "RegistrationDate")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Date registrationDate;

   // @Id
    @Column(name = "Username")
    //@GeneratedValue(strategy = GenerationType.TABLE)
    public String username;

    @Column(name = "Password")
    public String password;
}
