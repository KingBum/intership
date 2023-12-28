package com.spring.finaldemo.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.finaldemo.controller.ContractController;
import com.spring.finaldemo.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Value;


import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Configuration
public class ContractConfig {

    @Autowired
    private Environment env;

    @Value("${spring.final.address_contract}")
    private String address_contract;

    @Value("${spring.final.socket_rpc}")
    private String socket_rpc;

    @Value("${spring.final.priv_key}")
    private String priv_key;

    @Value("${spring.final.tatum_key}")
    private String tatum_key;

    @Bean
    public Web3j web3j() throws ConnectException {
        WebSocketService webSocketService = new WebSocketService(socket_rpc, true);
        webSocketService.connect();
        return Web3j.build(webSocketService);
    }

    @Bean
    public String contractAddress() {
        return address_contract;
    }


    @Bean
    public Credentials credentials() {
        return Credentials.create(priv_key);
    }

    @Bean
    public StaticGasProvider gasProvider() {
        BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L); // Set your desired gas price
        BigInteger gasLimit = BigInteger.valueOf(6_300_000); // Set your desired gas limit
        return new StaticGasProvider(gasPrice, gasLimit);
    }


    public StaticGasProvider getGasEstimate(String from, String to , String value) throws IOException, InterruptedException {
        var httpClient = HttpClient.newBuilder().build();

        var payload = String.join("\n"
                , "{"
                , " \"from\": \"" + from + "\","
                , " \"to\": \"" + to + "\","
                , " \"amount\": \"" + value + "\""
                , "}"
        );

        var host = "https://api.tatum.io";
        var pathname = "/v3/bsc/gas";
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .uri(URI.create(host + pathname ))
                .header("Content-Type", "application/json")
                .header("x-api-key", tatum_key)
                .build();

        var response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body());
        BigInteger gasLimit = new BigInteger(jsonNode.get("gasLimit").asText());
        BigInteger gasPrice = new BigInteger(jsonNode.get("gasPrice").asText());
        return new StaticGasProvider(gasPrice, gasLimit);
    }
}
