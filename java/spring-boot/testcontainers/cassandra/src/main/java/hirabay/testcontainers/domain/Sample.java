package hirabay.testcontainers.domain;

import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Table("t_sample")
public class Sample {
    @PrimaryKey private String key;
    private String value;
}
