package com.training.booklist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// For testing using Postman, will keep it commented when no needed
                .authorizeRequests()
                //.anyRequest().authenticated() This line is a default, not needed now
                .antMatchers(HttpMethod.POST,"books").authenticated()
                .antMatchers(HttpMethod.POST,"users").authenticated()
                .antMatchers(HttpMethod.POST,"categories").authenticated()
                .antMatchers(HttpMethod.PUT,"books").authenticated()
                .antMatchers(HttpMethod.PUT,"users").authenticated()
                .antMatchers(HttpMethod.PUT,"categories").authenticated()
                .antMatchers(HttpMethod.DELETE,"books").authenticated()
                .antMatchers(HttpMethod.DELETE,"users").authenticated()
                .antMatchers(HttpMethod.DELETE,"categories").authenticated()
                .antMatchers(HttpMethod.POST,"sign-up").permitAll()
                .and()
                .formLogin().and()
                .httpBasic();

    }
/*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("training")
                .password("Password1")
                .authorities("admin")
                .and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        UserDetails user = User.withUsername("training").password("Password1").authorities("admin").build();
        userDetailsService.createUser(user);
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
