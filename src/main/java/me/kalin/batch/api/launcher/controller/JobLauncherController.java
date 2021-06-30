package me.kalin.batch.api.launcher.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kalin.batch.api.launcher.model.JobRequest;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

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

    @PostMapping("/jobs")
    public ExitStatus launchJob(@RequestBody JobRequest jobRequest) {
        try {
            return jobLauncher
                    .run(applicationContext.getBean(jobRequest.getJobName(), Job.class), jobRequest.getJobParameters())
                    .getExitStatus();
        } catch (NullPointerException ne) {
            log.error(jobRequest.getJobName().concat(" not exists"));
            return exitFail(ne);
        } catch (Exception e) {
            log.error(e.getMessage());
            return exitFail(e);
        }
    }

    private ExitStatus exitFail(Exception exception) {
        return new ExitStatus(ExitStatus.FAILED.toString(), exception.getMessage());
    }
}
