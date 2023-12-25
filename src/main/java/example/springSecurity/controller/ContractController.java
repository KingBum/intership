package example.springSecurity.controller;

import example.springSecurity.entity.TransferRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.crypto.Credentials;
import org.web3j.model.SimpleToken;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
@RestController
@RequestMapping("/api/contract")
public class ContractController {
    private final SimpleToken simpleToken;

    public ContractController(Web3j web3j, Credentials credentials, String contractAddress, StaticGasProvider gasProvider) {
        this.simpleToken = SimpleToken.load(contractAddress, web3j, credentials, gasProvider);
    }

    @GetMapping("/balance/{address}")
    public BigInteger getBalance(@PathVariable String address) throws Exception {
        return simpleToken.balanceOf(address).send();
    }

     @GetMapping("/name")
     public String getName() throws Exception {
         return simpleToken.name().send();
     }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest transferRequest) throws Exception {
        simpleToken.transfer(transferRequest.getTo(), transferRequest.getValue()).send();
        return ResponseEntity.ok("Transfer successfully.");

    }
}
