package hirabay.cassandra.client;

import hirabay.cassandra.domain.Sample;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CassandraClient {
    private final CassandraTemplate template;

    public List<Sample> findAll() {
        return template.select("SELECT * FROM t_sample", Sample.class);
    }
}
