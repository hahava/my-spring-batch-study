package me.kalin.batch.job;

import lombok.RequiredArgsConstructor;
import me.kalin.batch.listener.CommonJobExecutionListener;
import me.kalin.batch.model.UserRegistration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;

@Configuration
@RequiredArgsConstructor
public class UserRegistrationInsertingJob {
    private static final int CHUNK_SIZE = 10;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CommonJobExecutionListener commonJobExecutionListener;
    private final DataSource dataSource;

    @Value("${user.registration.path}")
    private String savingFilePath;

    @Bean
    public Job addUserRegistrationJob() {
        return jobBuilderFactory
                .get("addUserRegistrationJob")
                .listener(JobListenerFactoryBean.getListener(new CommonJobExecutionListener()))
                .start(userRegistrationSteps())
                .build();
    }

    @Bean
    public Step userRegistrationSteps() {
        return stepBuilderFactory
                .get("userRegistrationStep")
                .<UserRegistration, UserRegistration>chunk(CHUNK_SIZE)
                .reader(readCsvFile(null))
                .writer(insertUserRegistration())
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<UserRegistration> insertUserRegistration() {
        return new JdbcBatchItemWriterBuilder<UserRegistration>()
                .dataSource(dataSource)
                .beanMapped()
                .sql("INSERT INTO USER_REGISTRATION " +
                        "(ID, FIRST_NAME, LAST_NAME, COMPANY, ADDRESS, CITY, STATE, ZIP, COUNTRY, URL, PHONE_NUMBER, FAX)" +
                        "VALUES" +
                        "(DEFAULT, :firstName, :lastName, :company, :address, :city, :state, :zip, :country, :url, :phoneNumber, :fax)"
                )
                .build();
    }

    @StepScope
    @Bean
    public FlatFileItemReader<UserRegistration> readCsvFile(@Value("#{jobParameters['fileName']}") String fileName) {
        return new FlatFileItemReaderBuilder<UserRegistration>()
                .name("readCsv")
                .encoding(StandardCharsets.UTF_8.name())
                .resource(new FileSystemResource(savingFilePath.concat(fileName)))
                .linesToSkip(1)
                .delimited()
                .delimiter(DelimitedLineTokenizer.DELIMITER_COMMA)
                .names(UserRegistration.headerName().toArray(new String[0]))
                .fieldSetMapper(fieldSet -> UserRegistration.builder()
                        .id(Long.parseLong(fieldSet.readString("id")))
                        .firstName(fieldSet.readString("firstName"))
                        .lastName(fieldSet.readString("lastName"))
                        .company(fieldSet.readString("company"))
                        .address(fieldSet.readString("address"))
                        .city(fieldSet.readString("city"))
                        .state(fieldSet.readString("state"))
                        .zip(fieldSet.readString("zip"))
                        .country(fieldSet.readString("country"))
                        .url(fieldSet.readString("url"))
                        .phoneNumber(fieldSet.readString("phoneNumber"))
                        .fax(fieldSet.readString("fax"))
                        .build())
                .build();
    }
}
