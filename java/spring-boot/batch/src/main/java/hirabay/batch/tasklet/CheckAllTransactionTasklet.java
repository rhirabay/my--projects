package hirabay.batch.tasklet;

import hirabay.batch.chunk.mybatis.infrastructure.PaymentTransactionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@StepScope // Step実行時にBean生成させるためのアノテーション
@Component
@RequiredArgsConstructor
public class CheckAllTransactionTasklet implements Tasklet {
    private final PaymentTransactionMapper paymentTransactionMapper;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
            throws Exception {
        log.info("Check all transactions");
        paymentTransactionMapper.findAll().forEach(paymentTransactionEntity -> {
            log.info("- Payment info: {}", paymentTransactionEntity);
        });

        return RepeatStatus.FINISHED;
    }
}
