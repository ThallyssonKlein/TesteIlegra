package persistence.repository;

import org.springframework.stereotype.Repository;
import persistence.entity.Log;

import java.util.List;

@Repository
public interface LogRepositoryCustom {

    public Log findLessAccessedLog();

    public List<Log> findOrderByFrequence();

    public List<Integer> findAllRegions();
}
