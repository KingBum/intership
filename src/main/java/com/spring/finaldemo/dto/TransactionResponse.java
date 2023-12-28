package com.spring.finaldemo.dto;

import lombok.Data;
import org.web3j.protocol.core.methods.response.Transaction;

@Data
public class TransactionResponse {
    private String hash;
    // Add other fields you want to expose in the response

    public TransactionResponse(String hash) {
        this.hash = hash;
    }

    // Getters and setters for other fields (if any)

    public String getHash() {
        return hash;
    }
}
