package persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import persistence.entity.Log;

public interface LogRepository extends MongoRepository<Log, Long> {
}
