package com.training.booklist.services;

import com.training.booklist.dto.UserDto;
import com.training.booklist.entities.AuthorityEntity;
import com.training.booklist.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface Users {
    List<UserEntity> getAllUsers();
    void saveUser(UserDto user);
    void updateUser(Long id, UserEntity user);
    void deleteUser(Long id);
    void addBook(Long bookId, Long userid);
    UserDetails loadUserByUsername(String username);
    void addToken(String username, String jwtToken);
    void addAuthority(Long id, AuthorityEntity authority);
}
