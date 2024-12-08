package hirabay.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@EnableBatchProcessing(
        dataSourceRef = "metaDataSource",
        transactionManagerRef = "metaTransactionManager")
@RequiredArgsConstructor
public class BatchConfiguration {

    @Bean
    public HelloWorldTasklet helloWorldTasklet() {
        return new HelloWorldTasklet();
    }

    @Bean
    public Step helloWorldStep(
            JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("helloWorldStep", jobRepository)
                .tasklet(helloWorldTasklet(), transactionManager)
                .build();
    }

    @Bean
    public Job helloWorldJob(
            JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("helloWorldJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .flow(helloWorldStep(jobRepository, transactionManager))
                .end()
                .build();
    }

    @StepScope
    public static class HelloWorldTasklet implements Tasklet {
        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
                throws Exception {
            log.info("Hello world");

            return RepeatStatus.FINISHED;
        }
    }
}
