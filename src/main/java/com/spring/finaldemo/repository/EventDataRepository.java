package com.spring.finaldemo.repository;

import com.spring.finaldemo.entity.EventData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface EventDataRepository extends JpaRepository<EventData, BigInteger> {
}
