package rhirabay.springboot3.data_access;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/data-access/jakarta/users")
    public List<User> all() {
        return userRepository.findAll();
    }
}
