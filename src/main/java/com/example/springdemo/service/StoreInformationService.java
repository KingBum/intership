package com.example.springdemo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.springdemo.entity.StoreInformation;

@Service
public interface StoreInformationService {
    List<StoreInformation> getAllStores();
    public Optional<StoreInformation> getStoreById(int id);
    public StoreInformation createStore(StoreInformation store);
    public StoreInformation updateStore(int id, StoreInformation store);
    public void deleteStore(int id);
    
    public Page<StoreInformation> findStoresWithPagination(Pageable pageable);
	List<StoreInformation> searchStoresByName(String name);
	List<StoreInformation> findAllByOrderByNameAsc();
	List<StoreInformation> findAllByOrderByNameDesc();
 
   
}
