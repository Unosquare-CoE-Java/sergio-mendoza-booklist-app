package com.training.booklist.dao;

import com.training.booklist.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDao extends CrudRepository<UserEntity, Long> {
    UserEntity getById(Long id);
    List<UserEntity> findAllByUsername(String username);
    UserEntity getByUsername(String username);
}
