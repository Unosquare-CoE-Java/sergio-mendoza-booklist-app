package com.training.booklist.dao;

import com.training.booklist.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<UserEntity, Long> {
}
