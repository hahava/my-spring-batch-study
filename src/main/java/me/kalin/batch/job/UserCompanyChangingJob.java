package me.kalin.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import me.kalin.batch.listener.JobLoggingListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class UserCompanyChangingJob {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job changeUserCompany() {
        return jobBuilderFactory
                .get("changeUserCompany")
                .listener(JobListenerFactoryBean.getListener(new JobLoggingListener()))
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
    public JdbcPagingItemReader<Map<String, Object>> readUserCompany() {
        return new JdbcPagingItemReaderBuilder<Map<String, Object>>()
            .name("readUserCompany")
            .dataSource(dataSource)
            .queryProvider(sqlPagingQueryProviderFactoryBean())
            .rowMapper((resultSet, i) -> Map.of("id", resultSet.getObject("id")))
            .pageSize(10)
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

    @Bean
    @SneakyThrows
    public PagingQueryProvider sqlPagingQueryProviderFactoryBean() {
        SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
        factoryBean.setSelectClause("SELECT id");
        factoryBean.setFromClause("FROM USER_REGISTRATION");
        factoryBean.setWhereClause("WHERE COMPANY = 'Samsung'");
        factoryBean.setSortKey("id");
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }
}
