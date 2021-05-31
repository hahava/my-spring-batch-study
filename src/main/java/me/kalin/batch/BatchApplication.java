package me.kalin.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@EnableBatchProcessing
@SpringBootApplication
public class BatchApplication {
    @Value("${spring.batch.job.names:NONE}")
    private String jobNames;

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }

    @PostConstruct
    public void validateJobNames() {
        if (jobNames.isEmpty() || jobNames.equals("NONE")) {
//            throw new IllegalStateException("Need --spring.batch.job.names=${jobNames} ...");
        }
    }
}
