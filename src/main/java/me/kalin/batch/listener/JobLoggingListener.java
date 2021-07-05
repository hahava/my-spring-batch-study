package me.kalin.batch.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.BeforeJob;

@Slf4j
public class JobLoggingListener {
	private static final String START_MESSAGE = "%s is Beginning";
	private static final String END_MESSAGE = "%s has completed with th stats %s";

	@BeforeJob
	public void beforeJob(JobExecution jobExecution) {
		log.info(String.format(START_MESSAGE, jobExecution.getJobInstance().getJobName()));
	}

	@AfterJob
	public void afterJob(JobExecution jobExecution) {
		log.info(String.format(END_MESSAGE, jobExecution.getJobInstance().getJobName(), jobExecution.getStatus()));
	}
}
