package com.spring.finaldemo.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransferRequest {
    private String to;
    private BigInteger value;

    // Getters and setters

}