package hirabay.batch;

import hirabay.batch.tasklet.HelloWorldTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing(
        dataSourceRef = "metaDataSource",
        transactionManagerRef = "metaTransactionManager")
@RequiredArgsConstructor
public class BatchConfiguration {
    @Bean
    public Step helloWorldStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            HelloWorldTasklet helloWorldTasklet
    ) {
        return new StepBuilder("helloWorldStep", jobRepository)
                .tasklet(helloWorldTasklet, transactionManager)
                .build();
    }

    @Bean
    public Job helloWorldTaskletJob(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            Step helloWorldStep
    ) {
        return new JobBuilder("helloWorldTaskletJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(helloWorldStep)
                .build();
    }
}
