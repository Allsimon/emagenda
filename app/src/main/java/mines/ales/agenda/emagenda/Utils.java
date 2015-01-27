package mines.ales.agenda.emagenda;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by a on 1/24/15.
 */
public class Utils {
    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
}
