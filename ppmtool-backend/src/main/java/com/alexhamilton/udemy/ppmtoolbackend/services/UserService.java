package com.alexhamilton.udemy.ppmtoolbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alexhamilton.udemy.ppmtoolbackend.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

}
