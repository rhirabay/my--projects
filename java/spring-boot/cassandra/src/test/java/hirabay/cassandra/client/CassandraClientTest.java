package hirabay.cassandra.client;

//import org.testcontainers.containers.CassandraContainer;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.oss.driver.api.core.CqlSession;
import hirabay.cassandra.domain.Sample;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class CassandraClientTest {
    @Container
    public static final CassandraContainer cassandra
            = (CassandraContainer) new CassandraContainer("cassandra:3.11.2").withExposedPorts(9042);

    private CassandraTemplate template;

    @BeforeEach
    void setup() {
        Cluster cluster = Cluster.builder()
                .addContactPoints(cassandra.getHost())
//                .withoutJMXReporting()  // 付けないとエラー
                .build();
        try (Session session = cluster.connect()) {
            session.execute("""
                            CREATE KEYSPACE IF NOT EXISTS sample
                              WITH replication = {'class':'SimpleStrategy','replication_factor':'1'};
                    """);
        }

        CqlSession session = CqlSession
                .builder()
                .addContactPoint(cassandra.getContactPoint())
                .withLocalDatacenter(cassandra.getLocalDatacenter())
                .build();

        this.template = new CassandraTemplate(session);
    }

    @Test
    void test() {
        var actual = template.select("SELECT * FROM t_sample", Sample.class);

        System.out.println(actual);
    }
}