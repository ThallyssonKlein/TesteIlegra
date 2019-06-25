package controller;

import business.Region;
import business.service.LogService;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricsController {

    private final LogService logService;

    @Autowired
    public MetricsController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/metrics")
    public String metrics(@RequestParam(required = false) int day, @RequestParam(required = false) int week, @RequestParam(required = false) int year){
        Gson gsonObject = new Gson();
        String toReturn = gsonObject.toJson(new Metric("Top 3 Most Accessed Urls of the World", logService.findTop3MostAccessedUrlsOfTheWorld()));
        for(Region r : logService.findTop3MostAccessedUrlsGroupedByRegion()){
            String code = String.valueOf(r.getRegionCode());
            toReturn += gsonObject.toJson(new Metric("Top 3 Most Accessed Urls of the region " + code, r.getTop3MostAccessedUrls()));
        }
        toReturn += gsonObject.toJson(new Metric("Url with less access of the world", new String[]{logService.findUrlWithLessAccessOfTheWorld()}));
        if(day > 0){
            toReturn += gsonObject.toJson(new Metric("Top 3 Most Accessed Urls of the day " + String.valueOf(day), logService.findTop3UrlMostAccessedOfDay(day)));
        }
        if(week > 0){
            toReturn += gsonObject.toJson(new Metric("Top 3 Most Accessed Urls of the week " + String.valueOf(week), logService.findTop3UrlMostAccessedOfTheWeek(week)));
        }
        if(year > 0){
            toReturn += gsonObject.toJson(new Metric("Top 3 Most Accessed Urls of the year " + String.valueOf(year), logService.findTop3UrlMostAccessedOfYear(year)));
        }
        toReturn += gsonObject.toJson(new Metric("The minute with more access in all urls is", new String[]{logService.findTheMinuteWithMoreAccessInAllUrls()}));
        return toReturn;
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class Metric{
    private String name;
    private String[] values;
}
