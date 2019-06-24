package controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import persistence.entity.Log;
import persistence.repository.LogRepository;

import javax.swing.text.html.Option;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@RestController
public class LogController {

    private final LogRepository logRepository;

    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @PostMapping("/log")
    public void newLog(@RequestBody Log log){
        Optional<Log> logFound = logRepository.findById(log.getId());
        if(logFound.isPresent()){
            logFound.get().addFrequence();
        }else{
            logRepository.save(log);
        }
    }
}
