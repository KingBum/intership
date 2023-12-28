package com.spring.finaldemo.config;

import com.spring.finaldemo.entity.EventData;
import com.spring.finaldemo.repository.EventDataRepository;
import com.spring.finaldemo.service.ContractService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.web3j.protocol.core.methods.response.Log;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class ScanBatchConfig {

    private final ContractService contractService;
    private final EventDataRepository eventDataRepository;

    @Bean
    @StepScope
    public ItemReader<Log> readerLog(
            @Value("#{jobParameters[startBlock]}") String startBlock,
            @Value("#{jobParameters[endBlock]}") String endBlock)
            throws IOException, InterruptedException {
        BigInteger startBlockBigInt = new BigInteger(startBlock);
        BigInteger endBlockBigInt = new BigInteger(endBlock);
        List<Log> logs = contractService.scanBlockRange(startBlockBigInt, endBlockBigInt);
        logs.forEach(log -> System.out.println("Event: " + log));
        return new ListItemReader<>(logs);
    }


    @Bean
    public ItemProcessor<Log, EventData> processorLog() {
        return log -> {
            String eventSignature = log.getTopics().get(0);
            if (eventSignature.equals(contractService.getMINT_EVENT_SIGNATURE())) {
                contractService.handleMintEvent(log, log.getBlockNumber());
            } else if (eventSignature.equals(contractService.getTRANSFER_EVENT_SIGNATURE())) {
                contractService.handleTransferEvent(log, log.getBlockNumber());
            } else if (eventSignature.equals(contractService.getBURN_EVENT_SIGNATURE())) {
                contractService.handleBurnEvent(log, log.getBlockNumber());
            } else {
                // Unknown event
                System.out.println("Unknown Event - Signature: " + eventSignature);
            }
                return null; // or handle as needed
        };
    }

    @Bean
    public ItemWriter<EventData> writerLog() {
        RepositoryItemWriter<EventData> writer = new RepositoryItemWriter<>();
        writer.setRepository(eventDataRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Job saveEventJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws IOException, InterruptedException {
        return new JobBuilder("saveEventJob", jobRepository)
                .start(step2(jobRepository, transactionManager, "10283242", "10284501"))
                .build();
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                      @Value("#{jobParameters[startBlock]}") String startBlock,
                      @Value("#{jobParameters[endBlock]}") String endBlock) throws IOException, InterruptedException {
        return new StepBuilder("step2", jobRepository)
                .<Log, EventData>chunk(10,transactionManager )// Chỉnh kiểu dữ liệu ở đây
                .reader(readerLog(startBlock, endBlock))
                .processor(processorLog())
                .writer(writerLog())
                .build();
    }

}
