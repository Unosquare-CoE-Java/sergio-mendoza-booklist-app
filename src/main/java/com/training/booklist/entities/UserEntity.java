package com.training.booklist.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

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

    @Column(name = "Username")
    public String username;

    @Column(name = "Password")
    public String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Users_Books",
            joinColumns = { @JoinColumn(name = "id")},
            inverseJoinColumns = { @JoinColumn(name = "Bookid")})
    public Set<BookEntity> books = new HashSet<>();

    public void addBook(BookEntity book) {
        this.books.add(book);
        book.getUsers().add(this);
    }
}
