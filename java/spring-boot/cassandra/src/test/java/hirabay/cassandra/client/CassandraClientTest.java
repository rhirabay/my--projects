package hirabay.cassandra.client;

import com.datastax.oss.driver.api.core.CqlSession;
import hirabay.cassandra.domain.Sample;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.cassandra.core.CassandraTemplate;

class CassandraClientTest {
//    private CassandraTemplate template;
//    private static CqlSession session;
//
    @Test
    void test() throws Exception {
        System.out.println("start cassandra");
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        System.out.println("get cassandra session");
        var session = EmbeddedCassandraServerHelper.getSession();
        System.out.println("load cql");
        new CQLDataLoader(session).load(new ClassPathCQLDataSet("sample.cql", "sample"));

        var template = new CassandraTemplate(session);
        var actual = template.select("SELECT * FROM t_sample", Sample.class);

        System.out.println(actual);
    }
}