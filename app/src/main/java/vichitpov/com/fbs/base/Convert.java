package vichitpov.com.fbs.base;


import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by imac on 2/26/18.
 */

public class Convert {

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

    public static String subStringDate(String date) {
        return date.substring(0, 10);
    }

    public static String priceConvert(String priceFrom, String priceTo) {

        String from = String.valueOf(priceFrom.indexOf("."));
        String to = String.valueOf(priceTo.indexOf("."));

        Log.e("pppp from", priceFrom);
        Log.e("pppp to", to);

        return from + "$" + " - " + to + "$";


    }

}
