package com.training.booklist.dao;

import com.training.booklist.entities.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityDao extends CrudRepository<AuthorityEntity, Integer> {
}
