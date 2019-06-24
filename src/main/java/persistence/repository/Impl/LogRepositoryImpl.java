package persistence.repository.Impl;

import org.springframework.data.domain.Sort;
import persistence.entity.Log;
import persistence.repository.LogRepository;

import java.util.List;

public abstract class LogRepositoryImpl implements LogRepository {

    @Override
    public List<Log> findAllOrderDesc(){
        return this.findAll(new Sort(Sort.Direction.DESC));
    }
}
