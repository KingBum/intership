package com.spring.finaldemo.service;

import com.spring.finaldemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spring.finaldemo.entity.User;

import java.util.Optional; 

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> userDetail = repository.findByUsername(username);

		// Converting userDetail to UserDetails
		return userDetail.map(UserDetail::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
	}

} 
