package rhirabay.repository;

import org.springframework.data.repository.CrudRepository;
import rhirabay.model.User;

public interface UserRepository extends CrudRepository<User, String> {}
