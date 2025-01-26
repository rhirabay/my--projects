package hirabay.batch.chunk.mybatis.writer;

import hirabay.batch.chunk.mybatis.infrastructure.PaymentTransactionEntity;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WriterConfiguration {

    // cf. https://mybatis.org/spring/ja/batch.html#mybatisbatchitemwriter
    @Bean
    public MyBatisBatchItemWriter<PaymentTransactionEntity> writer(
            SqlSessionFactory sqlSessionFactory
    ) {
        return new MyBatisBatchItemWriterBuilder<PaymentTransactionEntity>()
                .sqlSessionFactory(sqlSessionFactory)
                .statementId("hirabay.batch.chunk.mybatis.infrastructure.PaymentTransactionMapper.update")
                .build();
    }
}
