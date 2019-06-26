package persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import persistence.entity.Log;

import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<Log, Long>, LogRepositoryCustom{

    @Query("{region:?1}")
    public List<Log> findAllByRegion(int region);
}
