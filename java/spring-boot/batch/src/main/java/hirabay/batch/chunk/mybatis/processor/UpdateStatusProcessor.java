package hirabay.batch.chunk.mybatis.processor;

import hirabay.batch.chunk.mybatis.infrastructure.PaymentTransactionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class UpdateStatusProcessor implements ItemProcessor<PaymentTransactionEntity, PaymentTransactionEntity> {

    @Override
    public PaymentTransactionEntity process(PaymentTransactionEntity item) throws Exception {
        // ランダムで 1 ~ 9の数字を生成
        int randomStatus = (int) (Math.random() * 9) + 1;

        return item.toBuilder()
                .status(Integer.toString(randomStatus))
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
