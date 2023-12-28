package com.spring.finaldemo.service;

import com.spring.finaldemo.contract.SimpleToken;
import com.spring.finaldemo.entity.EventData;
import com.spring.finaldemo.repository.EventDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.websocket.WebSocketService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.StaticGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class ContractService {

    private final SimpleToken simpleToken;

    public ContractService(Web3j web3j, Credentials credentials, String contractAddress, StaticGasProvider gasProvider) {
        this.simpleToken = SimpleToken.load(contractAddress, web3j, credentials, gasProvider);
    }

    private final String MINT_EVENT_SIGNATURE = EventEncoder.encode(SimpleToken.MINT_EVENT);
    private final String TRANSFER_EVENT_SIGNATURE = EventEncoder.encode(SimpleToken.TRANSFER_EVENT);
    private final String BURN_EVENT_SIGNATURE = EventEncoder.encode(SimpleToken.BURN_EVENT);

    @Autowired
    private EventDataRepository eventDataRepository;

    @Value("${spring.final.address_contract}")
    private String address_contract;

    @Value("${spring.final.socket_rpc}")
    private String socket_rpc;


    public List<Log> Socket() throws ConnectException, InterruptedException {
        WebSocketService webSocketService = new WebSocketService(socket_rpc, true);
        webSocketService.connect();
        Web3j web3j = Web3j.build(webSocketService);

        List<Log> logs = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch latch = new CountDownLatch(100);

        EthFilter filter = new EthFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                Arrays.asList(address_contract));

        web3j.ethLogFlowable(filter).subscribe(txHash -> {
            System.out.println("New block mined: " + txHash.getBlockNumber());
            web3j.ethGetTransactionReceipt(txHash.getTransactionHash()).sendAsync().thenAccept(receipt -> {
                if (receipt.getTransactionReceipt().isPresent()) {
                    for (Log log : receipt.getTransactionReceipt().get().getLogs()) {
                        String eventSignature = log.getTopics().get(0);
                        logs.add(log);
                        if (eventSignature.equals(MINT_EVENT_SIGNATURE)) {
                            handleMintEvent(log, log.getBlockNumber());
                        } else if (eventSignature.equals(TRANSFER_EVENT_SIGNATURE)) {
                            handleTransferEvent(log, log.getBlockNumber());
                        } else if (eventSignature.equals(BURN_EVENT_SIGNATURE)) {
                            handleBurnEvent(log, log.getBlockNumber());
                        } else {
                            System.out.println("Unknown Event - Signature: " + eventSignature);
                        }
                    }
                }
                latch.countDown(); // Signal that logs are collected
            });
        });

        latch.await(20, TimeUnit.SECONDS);
        return logs;
    }



    public List<Log> scanBlockRange(BigInteger startBlock, BigInteger endBlock) throws IOException, InterruptedException {
        WebSocketService webSocketService = new WebSocketService(socket_rpc, true);
        webSocketService.connect();
        Web3j web3j = Web3j.build(webSocketService);

        EthFilter filter = new EthFilter(
                new DefaultBlockParameterNumber(startBlock),
                new DefaultBlockParameterNumber(endBlock),
                Arrays.asList(address_contract));

        List<Log> logs = Collections.synchronizedList(new ArrayList<>());
        CountDownLatch latch = new CountDownLatch(100);

        web3j.ethLogFlowable(filter).subscribe(txHash -> {
            System.out.println("New block mined: " + txHash.getTopics().get(0));
            web3j.ethGetTransactionReceipt(txHash.getTransactionHash()).sendAsync().thenAccept(receipt -> {
                if (receipt.getTransactionReceipt().isPresent()) {
                    for (Log log : receipt.getTransactionReceipt().get().getLogs()) {
                        System.out.println("Event - Signature: " + log);
                        logs.add(log);
                    }
                }
                latch.countDown(); // Signal that logs are collected
            });
        });

        // Wait for the logs to be collected or timeout after 5 seconds
        webSocketService.close();
        latch.await(20, TimeUnit.SECONDS);

        return logs;
    }


    public void handleMintEvent(Log log, BigInteger block) {
        String to = SimpleToken.getMintEventFromLog(log).to;
        BigInteger value = SimpleToken.getMintEventFromLog(log).value;
        System.out.println("Mint Event - To: " + to + ", Value: " + value);
        saveEventData(block,"Mint", to, null, value);
        System.out.println("Done");
    }

    public void handleTransferEvent(Log log, BigInteger block) {
        String from = SimpleToken.getTransferEventFromLog(log).from;
        String to = SimpleToken.getTransferEventFromLog(log).to;
        BigInteger value = SimpleToken.getTransferEventFromLog(log).value;
        System.out.println("Transfer Event - From: " + from + ", To: " + to + ", Value: " + value);
        saveEventData(block,"Transfer", from, to, value);
        System.out.println("Done");
    }

    public void handleBurnEvent(Log log, BigInteger block) {
        String from = SimpleToken.getBurnEventFromLog(log).from;
        BigInteger value = SimpleToken.getBurnEventFromLog(log).value;
        System.out.println("Burn Event - From: " + from + ", Value: " + value);
        saveEventData(block,"Burn", from, null, value);
        System.out.println("Done");
    }

    private void saveEventData(BigInteger block, String eventType, String from, String to, BigInteger value) {
        EventData eventData = new EventData();
        eventData.setId(block);
        eventData.setEventType(eventType);
        eventData.setFrom(from);
        eventData.setTo(to);
        eventData.setValue(value);
        try {
            eventDataRepository.save(eventData);
            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BigInteger getBalance(String address) throws Exception {
        return simpleToken.balanceOf(address).send();
    }

    public void mint(String to, BigInteger value) throws Exception {
        simpleToken.mint(to, value).send();
    }

    public void transfer(String to, BigInteger value) throws Exception {
        simpleToken.transfer(to, value).send();
    }

    public void burn(BigInteger value) throws Exception {
        simpleToken.burn(value).send();
    }

    public String getTRANSFER_EVENT_SIGNATURE() {
        return TRANSFER_EVENT_SIGNATURE;
    }

    public String getMINT_EVENT_SIGNATURE() {
        return MINT_EVENT_SIGNATURE;
    }

    public String getBURN_EVENT_SIGNATURE() {
        return BURN_EVENT_SIGNATURE;
    }
}
