package com.spring.finaldemo.controller;

import com.spring.finaldemo.dto.TransferRequest;
import com.spring.finaldemo.service.ContractService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
@RestController
@RequestMapping("/api/contract")
public class ContractController {

    private ContractService contractService;
    private final JobLauncher jobLauncher;

    @Autowired
    @Lazy
    @Qualifier("saveEventJob")
    private final Job saveEventJob;


    public ContractController(ContractService contractService, JobLauncher jobLauncher, Job saveEventJob) {
        this.contractService = contractService;
        this.jobLauncher = jobLauncher;
        this.saveEventJob = saveEventJob;
    }


    @GetMapping("/scanblock/{startBlock}/{endBlock}")
    public ResponseEntity<String> runBatchJob(@PathVariable BigInteger startBlock, @PathVariable BigInteger endBlock) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startBlock", startBlock.longValue())
                    .addLong("endBlock", endBlock.longValue())
                    .toJobParameters();

            jobLauncher.run(saveEventJob, jobParameters);

            return ResponseEntity.ok("Batch job started successfully.");
        } catch (JobExecutionException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error starting batch job.");
        }
    }

    @GetMapping("/balance/{address}")
    public BigInteger getBalance(@PathVariable String address) throws Exception {
        return contractService.getBalance(address);
    }

    @GetMapping("/scansocket")
    public ResponseEntity<String> scanSocket() throws Exception {
        contractService.Socket();
        return ResponseEntity.ok("Check your DB");
    }


    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest transferRequest) throws Exception {
        contractService.transfer(transferRequest.getTo(), transferRequest.getValue());
        return ResponseEntity.ok("Transfer successfully.");

    }

    @PostMapping("/mint")
    public ResponseEntity<String> mint(@RequestBody TransferRequest transferRequest) throws Exception {
        contractService.mint(transferRequest.getTo(), transferRequest.getValue());
        return ResponseEntity.ok("Transfer successfully.");
    }

    @PostMapping("/burn")
    public ResponseEntity<String> burn(@RequestBody TransferRequest transferRequest) throws Exception {
        contractService.burn(transferRequest.getValue());
        return ResponseEntity.ok("Transfer successfully.");
    }
}
