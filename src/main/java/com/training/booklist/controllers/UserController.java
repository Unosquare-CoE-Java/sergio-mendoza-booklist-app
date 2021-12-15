package com.training.booklist.controllers;

import com.training.booklist.dto.LoginDto;
import com.training.booklist.dto.ResponseDto;
import com.training.booklist.dto.UserDto;
import com.training.booklist.entities.AuthorityEntity;
import com.training.booklist.entities.UserEntity;
import com.training.booklist.filter.JWTTokenGeneratorFilter;
import com.training.booklist.filter.constants.SecurityConstants;
import com.training.booklist.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService users;

    @Autowired
    private AuthenticationManager authenticationManagerBean;

    @RequestMapping("/users")
    public List<UserEntity> getAllUsers() {
        return users.getAllUsers();
    }

    @RequestMapping(method = RequestMethod.POST, value="/sign-up")
    @PostMapping(
            value = "/saveUser",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveUser(@RequestBody UserDto user) {
            users.saveUser(user);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{id}")
    public void updateUser(@RequestBody UserEntity user, @PathVariable Long id) {
        users.updateUser(id, user);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{id}/permissions")
    public void addAuthority(@RequestBody AuthorityEntity authority, @PathVariable Long id) {
        users.addAuthority(id, authority);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        users.deleteUser(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{userId}/book/{bookId}")
    public void addBook(@PathVariable Long bookId, @PathVariable Long userId) {
        users.addBook(bookId, userId);
    }

    @PostMapping("/signin")
    // Authenticate user credentials and provides JWT
    public ResponseDto authenticateUser(@RequestBody LoginDto loginDto){
        // Authenticate user
        Authentication authentication = authenticationManagerBean.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate token
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder().setIssuer("Sergio Mendoza").setSubject("JWT Token")
                .claim("username", authentication.getName())
                .claim("authorities", JWTTokenGeneratorFilter.populateAuthorities(authentication.getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 30000))
                .signWith(key).compact();

        // Persist token to DB
        users.addToken(loginDto.getUsername(), jwt);

        // Added to make testing in Postman easier
        ResponseDto response = new ResponseDto();
        response.setToken(jwt);
        return response;
    }
}
