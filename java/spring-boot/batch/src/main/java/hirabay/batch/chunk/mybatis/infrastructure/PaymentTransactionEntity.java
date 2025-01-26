package hirabay.batch.chunk.mybatis.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransactionEntity {
    private String id;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
