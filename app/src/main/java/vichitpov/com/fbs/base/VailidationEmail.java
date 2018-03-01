package vichitpov.com.fbs.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by VichitDeveloper on 3/1/18.
 */

public class VailidationEmail {

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
