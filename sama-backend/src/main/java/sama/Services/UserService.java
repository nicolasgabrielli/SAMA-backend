package sama.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sama.Entities.User;
import sama.Repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).get();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
