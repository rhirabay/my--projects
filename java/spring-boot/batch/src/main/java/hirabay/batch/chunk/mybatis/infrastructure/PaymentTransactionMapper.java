package hirabay.batch.chunk.mybatis.infrastructure;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentTransactionMapper {
    List<PaymentTransactionEntity> findAll();
    List<PaymentTransactionEntity> findByStatus(String status);
    void update(PaymentTransactionEntity paymentTransactionEntity);
}
