package controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import persistence.entity.Log;
import persistence.repository.LogRepository;

@RestController
public class IngestController {

    private final LogRepository logRepository;

    public IngestController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @PostMapping("/log")
    Log newLog(@RequestBody Log log){
        return logRepository.save(log);
    }
}
