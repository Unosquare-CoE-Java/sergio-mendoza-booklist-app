package com.training.booklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScans({@ComponentScan("com.training.booklist.controllers"),@ComponentScan("com.training.booklist.config"),@ComponentScan("com.training.booklist.services")})
@EntityScan("com.training.booklist.entities")
@EnableJpaRepositories("com.training.booklist.dao")
public class BookListApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookListApplication.class, args);
    }

}
