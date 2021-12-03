package com.training.booklist.services;

import com.training.booklist.dto.UserDto;
import com.training.booklist.entities.UserEntity;
import java.util.List;

public interface Users {
    List<UserEntity> getAllUsers();
    void saveUser(UserDto user);
    void updateUser(Long id, UserEntity user);
    void deleteUser(Long id);
    void addBook(Long bookId, Long userid);
}
