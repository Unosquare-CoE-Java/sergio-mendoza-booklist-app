package com.training.booklist.config;

import com.training.booklist.filter.JWTTokenGeneratorFilter;
import com.training.booklist.filter.JWTTokenValidatorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .csrf().disable()// For testing using Postman, will keep it commented when no needed
                .addFilterBefore(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                //.anyRequest().authenticated() This line is a default, not needed now
/*
                //Not using any of this until I implement a Log In API
                .mvcMatchers(HttpMethod.POST,"/books").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.GET,"/books").permitAll()
                .mvcMatchers(HttpMethod.PUT,"/books").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/books").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/categories").authenticated()
                .mvcMatchers(HttpMethod.POST,"/categories").authenticated()
                .mvcMatchers(HttpMethod.PUT,"/categories").authenticated()
                .mvcMatchers(HttpMethod.PUT,"/users").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/users").hasRole("ADMIN")
                .mvcMatchers(HttpMethod.POST,"/users").hasAnyRole("USER", "ADMIN")
*/
                .mvcMatchers(HttpMethod.POST,"/books").authenticated()
                .mvcMatchers(HttpMethod.GET,"/books").permitAll()
                .mvcMatchers(HttpMethod.PUT,"/books").authenticated()
                .mvcMatchers(HttpMethod.DELETE,"/books").authenticated()
                .mvcMatchers(HttpMethod.DELETE,"/categories").authenticated()
                .mvcMatchers(HttpMethod.POST,"/categories").authenticated()
                .mvcMatchers(HttpMethod.PUT,"/categories").authenticated()
                .mvcMatchers(HttpMethod.PUT,"/users").authenticated()
                .mvcMatchers(HttpMethod.DELETE,"/users").authenticated()
                .mvcMatchers(HttpMethod.POST,"/users").authenticated()
                .mvcMatchers(HttpMethod.POST,"/sign-up").permitAll()
                .and()
                .formLogin().and()
                .httpBasic();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        UserDetails user = User.withUsername("training").password(passwordEncoder().encode("Password1")).roles("ADMIN").build();
        userDetailsService.createUser(user);
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
