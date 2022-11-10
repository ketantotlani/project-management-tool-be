package com.ketan.ppmtool.repositories;

import com.ketan.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {

    User findByUsername(String username);
    User getById(Long id);
}
