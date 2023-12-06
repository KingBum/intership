//package com.example.springdemo.service;
//
//import java.util.ArrayList;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.example.springdemo.entity.User;
//import com.example.springdemo.repository.UserRepository;
//
//@Service
//public class UserServiceImpl implements UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found: " + username);
//        }
//        return new org.springframework.security.core.userdetails.User(
//            user.getUsername(),
//            user.getPassword(),
//            new ArrayList<>()
//        );
//    }
//
//    @Override
//    public void saveUser(User user) {
//        userRepository.save(user);
//    }
//}