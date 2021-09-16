package com.alexhamilton.udemy.ppmtoolbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alexhamilton.udemy.ppmtoolbackend.domain.User;
import com.alexhamilton.udemy.ppmtoolbackend.exceptions.UsernameAlreadyExistsException;
import com.alexhamilton.udemy.ppmtoolbackend.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public User saveUser(User newUser) {
		try {
			newUser.setUsername(newUser.getUsername());
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			newUser.setConfirmPassword("");
			
			return userRepository.save(newUser);
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '" + newUser.getUsername() + "' already exists");
		}
	}

}
