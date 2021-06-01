package me.kalin.batch.common.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

@Slf4j
public class CommonJobExecutionListener {
    @BeforeJob
    public void beforeJob(JobExecution jobExecution) {
        log.info(jobExecution.toString());
    }

    @AfterJob
    public void afterJob(JobExecution jobExecution) {
        log.info(jobExecution.toString());
    }
}
