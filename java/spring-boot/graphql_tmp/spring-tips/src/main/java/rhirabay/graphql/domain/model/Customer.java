package rhirabay.graphql.domain.model;

import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
public class Customer {
    @Id
    Integer id;
    String name;
}