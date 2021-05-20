package me.kalin.batch.feat.user.job;

import lombok.RequiredArgsConstructor;
import me.kalin.batch.feat.user.listener.UserRegistrationWriterListener;
import me.kalin.batch.feat.user.job.reader.UserRegistrationReader;
import me.kalin.batch.feat.user.model.UserRegistration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class UserJob {
    private static final int CHUNK_SIZE = 10;
    private static final int SAMPLE_USER_SIZE = 100;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Value("${user.registration.path}")
    private String sampleFilePath;

    @Bean
    public Job writeUserRegistrationInfos() {
        return jobBuilderFactory
                .get("writeUserRegistrationInfo")
                .start(userRegistrationStep())
                .build();
    }

    @Bean
    public Step userRegistrationStep() {
        return stepBuilderFactory
                .get("userRegistrationStep")
                .<UserRegistration, UserRegistration>chunk(CHUNK_SIZE)
                .reader(new UserRegistrationReader(SAMPLE_USER_SIZE))
                .writer(writeToCsv())
                .listener(new UserRegistrationWriterListener<>())
                .build();
    }

    @Bean
    public FlatFileItemWriter<UserRegistration> writeToCsv() {
        return new FlatFileItemWriterBuilder<UserRegistration>()
                .name("writeToCsv")
                .encoding(StandardCharsets.UTF_8.name())
                .resource(new FileSystemResource(sampleFilePath))
                .append(true)
                .lineAggregator(new PassThroughLineAggregator<>())
                .headerCallback(writer -> writer.write(String.join(",", UserRegistration.headerName())))
                .build();
    }
}
