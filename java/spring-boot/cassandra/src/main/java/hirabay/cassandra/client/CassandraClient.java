package hirabay.cassandra.client;

import hirabay.cassandra.domain.Sample;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CassandraClient {
    private final CassandraTemplate template;

    public List<Sample> findAll() {
        return template.select("SELECT * FROM t_sample", Sample.class);
    }
}
