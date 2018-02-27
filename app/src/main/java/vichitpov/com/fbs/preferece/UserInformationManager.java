package vichitpov.com.fbs.preferece;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import vichitpov.com.fbs.retrofit.response.UserInformationResponse;

/**
 * Created by VichitPov on 2/28/18.
 */

public class UserInformationManager {

    public static final String PREFERENCES_USER_INFORMATION = "PREFERENCES_USER_INFORMATION";
    private static final String TYPE = "TYPE";
    private static final String ID = "ID";
    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String LAST_NAME = "LAST_NAME";
    private static final String EMAIL = "EMAIL";
    private static final String PHONE = "PHONE";
    private static final String GENDER = "GENDER";
    private static final String ADDRESS = "ADDRESS";
    private static final String CITY = "CITY";
    private static final String PROFILE = "PROFILE";
    private static final String ROLE = "ROLE";
    private static final String STATUS = "STATUS";
    private static final String TOTAL_POST_BUY = "TOTAL_POST_BUY";
    private static final String TOTAL_POST_SELL = "TOTAL_POST_SELL";
    private static final String SIGN_UP_DATE = "SIGN_UP_DATE";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static UserInformationManager INSTANCE = null;

    @SuppressLint("CommitPrefEdits")
    public UserInformationManager(SharedPreferences preferences) {
        this.preferences = preferences;
        this.editor = preferences.edit();
    }

    public static synchronized UserInformationManager getInstance(SharedPreferences preferences) {
        if (INSTANCE == null) {
            INSTANCE = new UserInformationManager(preferences);
        }
        return INSTANCE;
    }

    public void saveAccessToken(String accessToken) {
        editor.putString(ACCESS_TOKEN, accessToken).commit();
    }

    public void deleteAccessToken() {
        editor.remove(ACCESS_TOKEN).commit();
    }


    public void saveInformation(UserInformationResponse user) {
        editor.putString(TYPE, user.getData().getType()).commit();
        editor.putString(ID, String.valueOf(user.getData().getId())).commit();
        editor.putString(FIRST_NAME, user.getData().getFirstname()).commit();
        editor.putString(LAST_NAME, user.getData().getLastname()).commit();
        editor.putString(EMAIL, user.getData().getEmail()).commit();
        editor.putString(PHONE, user.getData().getPhone()).commit();
        editor.putString(GENDER, user.getData().getGender()).commit();
        editor.putString(ADDRESS, user.getData().getAddress()).commit();
        editor.putString(CITY, user.getData().getCity()).commit();
        editor.putString(PROFILE, user.getData().getProfilepicture()).commit();
        editor.putString(ROLE, user.getData().getRole()).commit();
        editor.putString(STATUS, user.getData().getStatus()).commit();
        editor.putString(TOTAL_POST_BUY, String.valueOf(user.getData().getTotalpostsbuy())).commit();
        editor.putString(TOTAL_POST_SELL, String.valueOf(user.getData().getTotalpostssell())).commit();
        editor.putString(SIGN_UP_DATE, String.valueOf(user.getData().getSignupdate())).commit();
    }


    public void deleteUserInformation() {

        editor.remove(TYPE).commit();
        editor.remove(ID).commit();
        editor.remove(FIRST_NAME).commit();
        editor.remove(LAST_NAME).commit();
        editor.remove(EMAIL).commit();
        editor.remove(PHONE).commit();
        editor.remove(GENDER).commit();
        editor.remove(ADDRESS).commit();
        editor.remove(CITY).commit();
        editor.remove(PROFILE).commit();
        editor.remove(ROLE).commit();
        editor.remove(STATUS).commit();
        editor.remove(TOTAL_POST_BUY).commit();
        editor.remove(TOTAL_POST_SELL).commit();
        editor.remove(SIGN_UP_DATE).commit();
    }

    public UserInformationResponse.Data getUser() {

        UserInformationResponse.Data user = new UserInformationResponse.Data();
        user.setId(Integer.parseInt(preferences.getString(ID, "Null")));
        user.setType(preferences.getString(TYPE, "Null"));
        user.setFirstname(preferences.getString(FIRST_NAME, "Null"));
        user.setLastname(preferences.getString(LAST_NAME, "Null"));
        user.setEmail(preferences.getString(EMAIL, "Null"));
        user.setPhone(preferences.getString(PHONE, "Null"));
        user.setGender(preferences.getString(GENDER, "Null"));
        user.setAddress(preferences.getString(TYPE, "Null"));
        user.setCity(preferences.getString(CITY, "Null"));
        user.setProfilepicture(preferences.getString(PROFILE, "Null"));
        user.setRole(preferences.getString(ROLE, "Null"));
        user.setStatus(preferences.getString(STATUS, "Null"));
        user.setTotalpostsbuy(Integer.parseInt(preferences.getString(TOTAL_POST_BUY, "Null")));
        user.setTotalpostssell(Integer.parseInt(preferences.getString(TOTAL_POST_SELL, "Null")));
        user.setType(preferences.getString(TYPE, "Null"));

        return user;

    }

}
