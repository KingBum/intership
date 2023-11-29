package com.example.springdemo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springdemo.entity.StoreInformation;

public interface StoreInformationRepository extends JpaRepository<StoreInformation, Integer> {

	List<StoreInformation> findByNameContainingIgnoreCase(String name);
    // Additional custom queries can be added here
	
	List<StoreInformation> findAllByOrderByNameAsc();
    
    List<StoreInformation> findAllByOrderByNameDesc();

}