import java.util.Calendar;
import java.util.Date;

/**
 * Created by liyangbd on 2018/9/11.
 */
public class datatest {
    private static final String[] WEEK = {"SUN", "MON", "TUE", "WED", "THU", "FRI",
            "SAT"};

    public String getWeekDay(Date dateStr) {
        Calendar c = Calendar.getInstance();
        c.setTime(dateStr);
        int intweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        return WEEK[intweek];
    }

    public static void main(String[] args) {
        datatest datatest = new datatest();
        Date dd = new Date();
        String week = datatest.getWeekDay(dd);
        System.out.println(week);
    }
}
