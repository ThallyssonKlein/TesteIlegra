package business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.entity.Log;
import persistence.repository.LogRepository;

import java.util.Collections;
import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository){
        this.logRepository = logRepository;
    }

    public String[] findTop3MostAccessedUrlsOfTheWorld(){

    }
}
