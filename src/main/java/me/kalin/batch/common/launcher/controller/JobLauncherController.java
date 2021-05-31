package me.kalin.batch.common.launcher.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JobLauncherController {
    private final ApplicationContext applicationContext;
    private final JobLauncher jobLauncher;

    @GetMapping("/jobs")
    public List<String> getJobs() {
        return Arrays.asList(applicationContext.getBeanNamesForType(Job.class));
    }

    @PostMapping("/job/{jobName}")
    public ExitStatus launchJob(@PathVariable String jobName) {
        try {
            return jobLauncher
                    .run(Optional.of(applicationContext.getBean(jobName, Job.class)).get(), new JobParameters())
                    .getExitStatus();
        } catch (Exception e) {
            log.error(e.getMessage());
            return exitFail(e);
        }
    }

    private ExitStatus exitFail(Exception exception) {
        return new ExitStatus(ExitStatus.FAILED.toString(), exception.getMessage());
    }
}
