package rhirabay.graphql.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Order {
    @Id
    private Integer id;
    private Integer customerId;
}