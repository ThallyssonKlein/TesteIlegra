package persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import persistence.entity.Log;

import java.util.List;

@Repository
public interface LogRepository extends MongoRepository<Log, Long> {

    public Log[] findOrderByFrequence();

    @Query("select l from logs l where l.region=?1 order by region desc")
    public List<Log> findAllByRegion(int region);

    @Query("select distinct l.region from logs")
    public List<Integer> findAllRegions();

    @Query("select 1 from logs l order by frequence asc")
    public Log findLessAccessedLog();

    @Query("select l from logs l where day(l.whenAccessed) ==?1 order by frequence desc")
    public List<Log> findLogsOfTheDay(int day);

    @Query("select l from logs l where year(l.whenAccessed) =?1 order by frequence desc")
    public List<Log> findLogsOfTheYear(int year);

    @Query("select l from logs l where month(l.whenAccessed) =?1 order by frequence desc")
    public List<Log> findLogsOfTheMonth(int month);

    @Query("select l from logs l where minute(l.whenAccessed) =?1 order by frequence desc")
    public List<Log> findLogsOfTheMinute(int minute);
}
