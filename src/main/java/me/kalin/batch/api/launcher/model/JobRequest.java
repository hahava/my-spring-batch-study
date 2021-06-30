package me.kalin.batch.api.launcher.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;

import java.util.Properties;

import static java.util.stream.Collectors.toMap;

@Value
public class JobRequest {
    String jobName;
    Properties properties;

    @JsonIgnore
    public JobParameters getJobParameters() {
        return new JobParameters(properties.entrySet().stream()
                .collect(
                        toMap(
                                entity -> (String) entity.getKey(),
                                entity -> new JobParameter(entity.getValue().toString())
                        )
                )
        );
    }
}
