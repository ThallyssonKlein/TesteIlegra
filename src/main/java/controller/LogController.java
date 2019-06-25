package controller;

import business.service.LogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import persistence.entity.Log;

@RestController
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("/log")
    public void newLog(@RequestBody Log log){
        logService.addFrequenceOrSave(log);
    }
}
