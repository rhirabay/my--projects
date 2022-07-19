package rhirabay.graphql.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import rhirabay.graphql.domain.model.Customer;
import rhirabay.graphql.domain.model.Order;
import rhirabay.graphql.domain.repository.CustomerRepository;

import java.util.stream.Stream;

@Controller
@RequiredArgsConstructor
public class CustomerGraphqlController {
    private final CustomerRepository customerRepository;

    // @SchemaMapping(typeName = "Query", field = "customers")
    @QueryMapping
    Flux<Customer> customers() {
        return customerRepository.findAll();
    }

    @QueryMapping
    Flux<Customer> customersByName(@Argument String name) {
        return customerRepository.findByName(name);
    }

    @SchemaMapping(typeName = "Customer")
    Flux<Order> orders(Customer customer) {
        return Flux.fromStream(Stream.iterate(1, i -> ++i)
                .limit((long)(Math.random() * 10))
                .map(i -> {
                    var order = new Order();
                    order.setId(i);
                    order.setCustomerId(customer.getId());
                    return order;
                }));
    }

    @MutationMapping
    Mono<Customer> addCustomer(@Argument String name) {
        var customer = new Customer(null, name);
        return customerRepository.save(customer);
    }
}