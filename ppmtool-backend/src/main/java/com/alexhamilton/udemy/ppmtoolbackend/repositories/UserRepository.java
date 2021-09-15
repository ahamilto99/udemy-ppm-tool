package com.alexhamilton.udemy.ppmtoolbackend.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alexhamilton.udemy.ppmtoolbackend.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
