package business.service.util;

import java.util.Calendar;

public class Util {

    private static Calendar calendar = Calendar.getInstance();

    public static int getMonthOfTheYear(){
        return calendar.get(Calendar.MONTH);
    }
}
