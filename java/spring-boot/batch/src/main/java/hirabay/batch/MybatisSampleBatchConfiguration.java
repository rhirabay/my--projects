package hirabay.batch;

import hirabay.batch.chunk.mybatis.infrastructure.PaymentTransactionEntity;
import hirabay.batch.chunk.mybatis.processor.UpdateStatusProcessor;
import hirabay.batch.tasklet.CheckAllTransactionTasklet;
import hirabay.batch.tasklet.HelloWorldTasklet;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
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
public class MybatisSampleBatchConfiguration {
    @Bean
    public Step checkAllTransactionStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            CheckAllTransactionTasklet checkAllTransactionTasklet
    ) {
        return new StepBuilder("checkAllTransactionStep", jobRepository)
                .tasklet(checkAllTransactionTasklet, transactionManager)
                .build();
    }

    @Bean
    public Step updateStatusStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            MyBatisCursorItemReader<PaymentTransactionEntity> reader,
            UpdateStatusProcessor processor,
            MyBatisBatchItemWriter<PaymentTransactionEntity> writer
    ) {
        return new StepBuilder("updateStatusStep", jobRepository)
                .<PaymentTransactionEntity, PaymentTransactionEntity>chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Job mybatisSampleJob(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            Step checkAllTransactionStep,
            Step updateStatusStep
    ) {
        return new JobBuilder("mybatisSampleJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(checkAllTransactionStep)
                .next(updateStatusStep)
                .next(checkAllTransactionStep)
                .build();
    }
}
