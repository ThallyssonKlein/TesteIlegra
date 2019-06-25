package business.validator;

import org.joda.time.DateTime;
import persistence.entity.Log;

public class VerifyIfLogIsOfTheWeekValidator {

    public boolean validate(Log log, int week) throws NullPointerException{
        int day = new DateTime(log.getWhenAccessed()).getDayOfMonth();
        if(day <= 7 && week == 1){
            return true;
        }else if(day <= 14 && week == 2){
            return true;
        }else if(day <= 21 && week == 3){
            return true;
        }else if(day <= 28 && week == 4){
            return true;
        }else{
            return false;
        }
    }
}
