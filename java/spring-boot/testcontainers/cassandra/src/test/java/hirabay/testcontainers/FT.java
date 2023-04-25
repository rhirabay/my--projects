package hirabay.testcontainers;

import hirabay.testcontainers.domain.Sample;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Slf4j
@Testcontainers
@SpringBootTest
class FT {
	@Autowired
	private CassandraOperations cassandraOperations;

	@Container
	public static CassandraContainer<?> cassandra = new CassandraContainer<>("cassandra:3.11.2")
			.withInitScript("initial.cql")
			.withExposedPorts(9042);

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.cassandra.keyspace-name", () -> "sample");
		registry.add("spring.cassandra.contact-points", () -> "%s:%d".formatted(cassandra.getContactPoint().getHostName(), cassandra.getContactPoint().getPort()));
		registry.add("spring.cassandra.local-datacenter", () -> cassandra.getLocalDatacenter());
	}

	@Test
	void test() {
		var selected = cassandraOperations.select("SELECT * from sample.t_sample", Sample.class);
		log.info("selected: {}", selected);
	}

}
