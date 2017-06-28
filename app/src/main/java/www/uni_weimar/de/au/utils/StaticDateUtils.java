package www.uni_weimar.de.au.utils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by nazar on 28.06.17.
 */

public class StaticDateUtils {
    public static Map<String, Integer> months = new HashMap<>();

    static {
        months.put("Jan", Calendar.JANUARY);
        months.put("Feb", Calendar.FEBRUARY);
        months.put("Mar", Calendar.MARCH);
        months.put("Apr", Calendar.APRIL);
        months.put("May", Calendar.MAY);
        months.put("Jun", Calendar.JUNE);
        months.put("Jul", Calendar.JULY);
        months.put("Aug", Calendar.AUGUST);
        months.put("Sep", Calendar.SEPTEMBER);
        months.put("Oct", Calendar.OCTOBER);
        months.put("Nov", Calendar.NOVEMBER);
        months.put("Dec", Calendar.DECEMBER);
    }


}
