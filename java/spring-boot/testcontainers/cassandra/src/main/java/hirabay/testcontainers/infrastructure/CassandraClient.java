package hirabay.testcontainers.infrastructure;

import hirabay.testcontainers.domain.Sample;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CassandraClient {
    private final CassandraTemplate cassandraTemplate;

    public List<Sample> findAll() {
        return cassandraTemplate.select("""
            SELECT *
            FROM sample.t_sample
            """, Sample.class);
    }
}
