package com.spring.finaldemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "eventdata")
public class EventData {
    @Id
    private BigInteger id;

    private String eventType;

    @Column(name = "fromAddress")
    private String from;

    @Column(name = "toAddress")
    private String to;
    private BigInteger value;

    // Constructors, getters, setters
}
