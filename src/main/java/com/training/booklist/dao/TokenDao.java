package com.training.booklist.dao;

import com.training.booklist.entities.TokenEntity;
import com.training.booklist.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface TokenDao extends CrudRepository<TokenEntity, Long> {
    boolean existsByUser(UserEntity user);
}
