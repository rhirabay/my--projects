package hirabay.batch.chunk.mybatis.reader;

import hirabay.batch.chunk.mybatis.infrastructure.PaymentTransactionEntity;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ReaderConfiguration {
    // https://mybatis.org/spring/ja/batch.html#mybatiscursoritemreader
    @Bean
    public MyBatisCursorItemReader<PaymentTransactionEntity> reader(
            SqlSessionFactory sqlSessionFactory
    ) {
        return new MyBatisCursorItemReaderBuilder<PaymentTransactionEntity>()
                .sqlSessionFactory(sqlSessionFactory)
                .queryId("hirabay.batch.chunk.mybatis.infrastructure.PaymentTransactionMapper.findByStatus")
                .parameterValues(Map.of(
                        "status", "0"
                ))
                .build();
    }
}
