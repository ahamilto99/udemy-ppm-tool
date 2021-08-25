package com.alexhamilton.udemy.ppmtoolbackend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alexhamilton.udemy.ppmtoolbackend.domain.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
