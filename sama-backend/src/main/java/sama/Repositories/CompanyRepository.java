package sama.Repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sama.Entities.Company;

public interface CompanyRepository extends MongoRepository<Company, String>{
}
