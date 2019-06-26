package business.service;

import business.Region;
import business.validator.VerifyIfLogIsOfTheWeekValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import business.service.util.Util;
import org.joda.time.DateTime;
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
    private final VerifyIfLogIsOfTheWeekValidator verifyIfLogIsOfTheWeekValidator = new VerifyIfLogIsOfTheWeekValidator();

    @Autowired
    public LogService(LogRepository logRepository){
        this.logRepository = logRepository;
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
        List<Log> logsSortedsBySequence = logRepository.findOrderByFrequence();
        return new String[]{logsSortedsBySequence.get(0).getUrl(),
                logsSortedsBySequence.get(1).getUrl(), logsSortedsBySequence.get(2).getUrl()};
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
        List<Log> logsOrderByFrequence = logRepository.findOrderByFrequence();
        List<Log> logsOfThisDay = new ArrayList<>();
        DateTime dateTimeObj;
        for(Log l : logsOrderByFrequence){
            dateTimeObj = new DateTime(l.getWhenAccessed());
            if(dateTimeObj.getDayOfMonth() == day){
                logsOfThisDay.add(l);
            }
        }
        return new String[]{logsOfThisDay.get(0).getUrl(), logsOfThisDay.get(1).getUrl(),
                logsOfThisDay.get(2).getUrl()};
    }

    public String[] findTop3UrlMostAccessedOfYear(int year){
        List<Log> logsOrderByFrequence = logRepository.findOrderByFrequence();
        List<Log> logsOfThisYear = new ArrayList<>();
        DateTime dateTimeObj;
        for(Log l : logsOrderByFrequence){
            dateTimeObj = new DateTime(l.getWhenAccessed());
            if(dateTimeObj.getYear() == year){
                logsOfThisYear.add(l);
            }
        }
        return new String[]{logsOfThisYear.get(0).getUrl(), logsOfThisYear.get(1).getUrl(),
                logsOfThisYear.get(2).getUrl()};
    }

    public String[] findTop3UrlMostAccessedOfTheWeek(int week) throws ArrayIndexOutOfBoundsException{
        if(week <= 4 && week > 0){
            List<Log> logsOrderByFrequence = logRepository.findOrderByFrequence();
            List<Log> logsOfThisWeek = new ArrayList<>();
            DateTime dateTimeObj;
            for(Log l : logsOrderByFrequence){
                dateTimeObj = new DateTime(l.getWhenAccessed());
                if(dateTimeObj.getMonthOfYear() == Util.getMonthOfTheYear() &&
                        verifyIfLogIsOfTheWeekValidator.validate(l, week)){
                    logsOfThisWeek.add(l);
                }
            }
            return new String[]{logsOfThisWeek.get(0).getUrl(),
                    logsOfThisWeek.get(1).getUrl(), logsOfThisWeek.get(2).getUrl()};
        }else{
            throw new InvalidParameterException();
        }
    }

    public String findTheMinuteWithMoreAccessInAllUrls(){
        LastMinute lastMinute = new LastMinute();
        List<Log> logsOrderByFrequence = logRepository.findOrderByFrequence();
        for(int i = 0; i<=60; ++i){
            List<Log> logsOfThisMinute = new ArrayList<>();
            DateTime dateTimeObj;
            for(Log l : logsOrderByFrequence){
                dateTimeObj = new DateTime(l.getWhenAccessed());
                if(dateTimeObj.getMinuteOfHour() == i){
                    logsOfThisMinute.add(l);
                }
            }
            if(lastMinute.getAccessCount() < logsOfThisMinute.size()){
                lastMinute.setMinuteNumber(i);
                lastMinute.setAccessCount(logsOfThisMinute.size());
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