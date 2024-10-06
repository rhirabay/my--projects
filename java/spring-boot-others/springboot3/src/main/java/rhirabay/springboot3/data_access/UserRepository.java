package rhirabay.springboot3.data_access;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

interface UserRepository extends CrudRepository<User, String> {
    List<User> findAll();
}
