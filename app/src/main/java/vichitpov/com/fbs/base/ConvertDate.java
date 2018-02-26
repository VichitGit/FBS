package vichitpov.com.fbs.base;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by imac on 2/26/18.
 */

public class ConvertDate {

    private static String[] monthEnglish = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Doc"};

    //convert date time to date
    public static String dateConverter(String date) {
        if (date != null) {
            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTime dt = formatter.parseDateTime(date);
            return dt.getDayOfMonth() + "-" + monthEnglish[dt.getMonthOfYear() - 1] + "-" + dt.getYear();
        }
        return null;
    }

}
