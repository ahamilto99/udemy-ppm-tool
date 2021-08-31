package com.alexhamilton.udemy.ppmtoolbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexhamilton.udemy.ppmtoolbackend.domain.Backlog;

@Repository
public interface BacklogRepository extends JpaRepository<Backlog, Long> {

}
