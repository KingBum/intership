package example.springSecurity.entity;

import java.math.BigInteger;

public class TransferRequest {
    private String to;
    private BigInteger value;

    // Getters and setters

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }
}