package rhirabay.repository;

import org.springframework.data.repository.CrudRepository;
import rhirabay.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
}
