package persistence.repository.Impl;

import org.springframework.data.domain.Sort;
import persistence.entity.Log;
import persistence.repository.LogRepository;

import java.util.List;

public abstract class LogRepositoryImpl implements LogRepository {

    @Override
    public Log[] findOrderByFrequence(){
        Log[] toReturn = new Log[3];
        List<Log> logList = this.findAll(Sort.by("frequence").descending());
        toReturn[0] = logList.get(0);
        toReturn[1] = logList.get(1);
        toReturn[2] = logList.get(2);
        return toReturn;
    }
}
