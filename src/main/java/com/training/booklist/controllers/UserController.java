package com.training.booklist.controllers;

import com.training.booklist.dto.UserDto;
import com.training.booklist.entities.UserEntity;
import com.training.booklist.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService users;

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

    @RequestMapping(method = RequestMethod.DELETE, value="/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        users.deleteUser(id);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/users/{userId}/book/{bookId}")
    public void addBook(@PathVariable Long bookId, @PathVariable Long userId) {
        users.addBook(bookId, userId);
    }
}
