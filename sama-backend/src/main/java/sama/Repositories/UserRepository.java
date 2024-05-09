package sama.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.Entities.User;

public interface UserRepository extends MongoRepository<User, String>{
}
