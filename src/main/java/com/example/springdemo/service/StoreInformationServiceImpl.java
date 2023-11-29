package com.example.springdemo.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.springdemo.entity.StoreInformation;
import com.example.springdemo.repository.StoreInformationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StoreInformationServiceImpl implements StoreInformationService {

    
    private StoreInformationRepository storeInformationRepository;
    private final Logger logger = LoggerFactory.getLogger(StoreInformationServiceImpl.class);
    
    // Constructor injection for the repository
    public StoreInformationServiceImpl(StoreInformationRepository storeInformationRepository) {
        this.storeInformationRepository = storeInformationRepository;
    }

    @Override
    public List<StoreInformation> getAllStores() {
    	logger.info("Getting all stores");
        return (List<StoreInformation>) storeInformationRepository.findAll();
    }

    @Override
    public Optional<StoreInformation> getStoreById(int id) {
        return storeInformationRepository.findById(id);
    }

    @Override
    public StoreInformation createStore(StoreInformation store) {
        return storeInformationRepository.save(store);
    }

    @Override
    public StoreInformation updateStore(int id, StoreInformation store) {
        store.setId(id);
        return storeInformationRepository.save(store);
    }

    @Override
    public void deleteStore(int id) {
        storeInformationRepository.deleteById(id);;
    }
    
    @Override
    public Page<StoreInformation> findStoresWithPagination(Pageable pageable) {
        return storeInformationRepository.findAll(pageable);
    }
    
    @Override
    public List<StoreInformation> searchStoresByName(String name) {
        return storeInformationRepository.findByNameContainingIgnoreCase(name);
    }
    
    @Override
    public List<StoreInformation> findAllByOrderByNameAsc() {
        return storeInformationRepository.findAllByOrderByNameAsc();
    }

    @Override
    public List<StoreInformation> findAllByOrderByNameDesc() {
        return storeInformationRepository.findAllByOrderByNameDesc();
    }
}
