package sama.Repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import sama.Entities.Report;

public interface ReportRepository extends MongoRepository<Report, String> {
}
