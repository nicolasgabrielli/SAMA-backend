package sama.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sama.Entities.User;
import sama.Services.UserService;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getUAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(String id){
        return userService.getUserById(id);
    }

    @PostMapping
    public User saveUser(User user){
        return userService.save(user);
    }

    @DeleteMapping
    public void deleteUser(String id){
        userService.deleteById(id);
    }
}
