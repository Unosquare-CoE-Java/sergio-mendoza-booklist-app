package com.training.booklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScans({@ComponentScan("com.training.booklist.controllers"),@ComponentScan("com.training.booklist.config"),@ComponentScan("com.training.booklist.services")})
@EntityScan("com.training.booklist.entities")
@EnableJpaRepositories("com.training.booklist.dao")
public class BookListApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookListApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

   @Bean
    public HttpFirewall allowUrlSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }
}
