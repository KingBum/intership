package com.example.springdemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.springdemo.entity.StoreInformation;
import com.example.springdemo.repository.StoreInformationRepository;


import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/stores")
public class StoreRestController {
	
	
    @Autowired
    private StoreInformationRepository storeInformationRepository;
    
    
    
    @GetMapping("/")
    public List<StoreInformation> getAllStores() {
    	
        return (List<StoreInformation>) storeInformationRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreInformation> getStoreById(@PathVariable int id) {
        Optional<StoreInformation> storeOptional = storeInformationRepository.findById(id);
        return storeOptional.map(store -> new ResponseEntity<>(store, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<StoreInformation> createStore(@RequestBody StoreInformation store) {
        StoreInformation savedStore = storeInformationRepository.save(store);
        return new ResponseEntity<>(savedStore, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreInformation> updateStore(@PathVariable int id, @RequestBody StoreInformation store) {
        Optional<StoreInformation> existingStoreOptional = storeInformationRepository.findById(id);

        if (existingStoreOptional.isPresent()) {
            store.setId(id);
            StoreInformation updatedStore = storeInformationRepository.save(store);
            return new ResponseEntity<>(updatedStore, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable int id) {
        Optional<StoreInformation> storeOptional = storeInformationRepository.findById(id);

        if (storeOptional.isPresent()) {
            storeInformationRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/paged")
    public ResponseEntity<Page<StoreInformation>> getPaginatedStores(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<StoreInformation> storePage = storeInformationRepository.findAll(PageRequest.of(page, size));
        return new ResponseEntity<>(storePage, HttpStatus.OK);
    }
    
     
    @GetMapping("/search")
    public ResponseEntity<List<StoreInformation>> searchStoresByName(
            @RequestParam String name) {
        List<StoreInformation> result = storeInformationRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/sort")
    public ResponseEntity<List<StoreInformation>> getSortedStores(
            @RequestParam(defaultValue = "asc") String direction) {
        List<StoreInformation> sortedStores;

        if ("desc".equalsIgnoreCase(direction)) {
            sortedStores = storeInformationRepository.findAllByOrderByNameDesc();
        } else {
            sortedStores = storeInformationRepository.findAllByOrderByNameAsc();
        }

        return ResponseEntity.ok(sortedStores);
    }
}
