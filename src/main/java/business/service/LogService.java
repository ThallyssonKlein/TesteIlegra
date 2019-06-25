package business.service;

import business.Region;
import business.validator.VerifyIfLogIsOfTheWeekValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.entity.Log;
import persistence.repository.LogRepository;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LogService {

    private final LogRepository logRepository;
    private final VerifyIfLogIsOfTheWeekValidator verifyIfLogIsOfTheWeekValidator;

    @Autowired
    public LogService(LogRepository logRepository, VerifyIfLogIsOfTheWeekValidator verifyIfLogIsOfTheWeekValidator){
        this.logRepository = logRepository;
        this.verifyIfLogIsOfTheWeekValidator = verifyIfLogIsOfTheWeekValidator;
    }

    public void addFrequenceOrSave(Log log){
        Optional<Log> logFound = logRepository.findById(log.getId());
        if(logFound.isPresent()){
            logFound.get().addFrequence();
        }else{
            logRepository.save(log);
        }
    }
    public String[] findTop3MostAccessedUrlsOfTheWorld(){
        Log[] logsSortedsBySequence = logRepository.findOrderByFrequence();
        return new String[]{logsSortedsBySequence[0].getUrl(), logsSortedsBySequence[1].getUrl(), logsSortedsBySequence[2].getUrl()};
    }

    public List<Region> findTop3MostAccessedUrlsGroupedByRegion(){
        List<Integer> regionsOfUrls = logRepository.findAllRegions();
        List<Region> toReturn = new ArrayList<>();
        for(Integer i : regionsOfUrls){
            List<Log> top3OfThisRegion = logRepository.findAllByRegion(i);
            toReturn.add(new Region(i,
                    new String[]{top3OfThisRegion.get(0).getUrl(),
                            top3OfThisRegion.get(1).getUrl(),
                            top3OfThisRegion.get(2).getUrl()}));
        }
        return toReturn;
    }

    public String findUrlWithLessAccessOfTheWorld(){
        return logRepository.findLessAccessedLog().getUrl();
    }

    public String[] findTop3UrlMostAccessedOfDay(int day){
        List<Log> logList = logRepository.findLogsOfTheDay(day);
        return new String[]{logList.get(0).getUrl(), logList.get(1).getUrl(),
                            logList.get(2).getUrl()};
    }

    public String[] findTop3UrlMostAccessedOfYear(int year){
        List<Log> logList = logRepository.findLogsOfTheYear(year);
        return new String[]{logList.get(0).getUrl(), logList.get(1).getUrl(),
                logList.get(2).getUrl()};
    }

    public String[] findTop3UrlMostAccessedOfTheWeek(int week) throws ArrayIndexOutOfBoundsException{
        if(week <= 4 && week > 0){
            List<Log> logsOfTheCurrentMonth = logRepository.findLogsOfTheMonth(Util.getMonthOfTheYear());
            List<Log> logsOfTheWeek = new ArrayList<>();
            for(int i = 0; i <= logsOfTheCurrentMonth.size(); ++i){
                if(verifyIfLogIsOfTheWeekValidator.validate(logsOfTheCurrentMonth.get(i), week)){
                    logsOfTheWeek.add(logsOfTheCurrentMonth.get(i));
                }
            }
            return new String[]{logsOfTheWeek.get(0).getUrl(), logsOfTheWeek.get(1).getUrl(), logsOfTheWeek.get(2).getUrl()};
        }else{
            throw new InvalidParameterException();
        }
    }

    public String findTheMinuteWithMoreAccessInAllUrls(){
        LastMinute lastMinute = new LastMinute();
        for(int i = 0; i<=60; ++i){
            List<Log> logsOfTheMinute = logRepository.findLogsOfTheMinute(i);
            if(lastMinute.getAccessCount() < logsOfTheMinute.size()){
                lastMinute.setMinuteNumber(i);
                lastMinute.setAccessCount(logsOfTheMinute.size());
            }
        }
        return String.valueOf(lastMinute.getMinuteNumber());
    }
}

@Getter
@Setter
@NoArgsConstructor
class LastMinute{
    private int minuteNumber;
    private int accessCount;
}