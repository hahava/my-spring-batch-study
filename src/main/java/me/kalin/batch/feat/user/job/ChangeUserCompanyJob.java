package me.kalin.batch.feat.user.job;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class ChangeUserCompanyJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job changeUserCompany() {
        return jobBuilderFactory
                .get("changeUserCompany")
                .start(changeUserCompanyStep())
                .build();
    }

    @Bean
    public Step changeUserCompanyStep() {
        return stepBuilderFactory
                .get("changeUserCompanyStep")
                .<Map<String, Object>, Map<String, Object>>chunk(10)
                .reader(readUserCompany())
                .writer(updateUserCompany())
                .build();
    }

    @Bean
    public JdbcCursorItemReader<Map<String, Object>> readUserCompany() {
        return new JdbcCursorItemReaderBuilder<Map<String, Object>>()
                .name("readUserCompany")
                .dataSource(dataSource)
                .sql("SELECT id FROM USER_REGISTRATION WHERE COMPANY = 'Samsung'")
                .rowMapper((resultSet, i) -> Map.of("id", resultSet.getObject("id")))
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Map<String, Object>> updateUserCompany() {
        return new JdbcBatchItemWriterBuilder<Map<String, Object>>()
                .dataSource(dataSource)
                .sql("UPDATE USER_REGISTRATION SET COMPANY = 'SDS' WHERE id = :id")
                .columnMapped()
                .build();
    }
}
