package hirabay.testcontainers.infrastructure;

import hirabay.testcontainers.domain.Sample;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CassandraClient {
    private final CassandraTemplate cassandraTemplate;

    public List<Sample> findAll() {
        return cassandraTemplate.select(
                """
                SELECT *
                FROM sample.t_sample
                """,
                Sample.class);
    }
}
