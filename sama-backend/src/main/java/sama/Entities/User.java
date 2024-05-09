package sama.Entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "user")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private String role;
    private List<String> companies;

    public User() {

    }
}
