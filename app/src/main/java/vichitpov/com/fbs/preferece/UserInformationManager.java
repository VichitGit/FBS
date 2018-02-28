package vichitpov.com.fbs.preferece;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import vichitpov.com.fbs.model.UserInFormationModel;
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


    public void saveInformation(UserInformationResponse userResponse) {

//        editor.putString(TYPE, user.getType()).commit();
//        editor.putString(ID, user.getId()).commit();
//        editor.putString(FIRST_NAME, user.getFirstName()).commit();
//        editor.putString(LAST_NAME, user.getLastName()).commit();
//        editor.putString(EMAIL, user.getEmail()).commit();
//        editor.putString(PHONE, user.getPhone()).commit();
//        editor.putString(GENDER, user.getGender()).commit();
//        editor.putString(ADDRESS, user.getAddress()).commit();
//        editor.putString(CITY, user.getCity()).commit();
//        editor.putString(PROFILE, user.getProfile()).commit();
//        editor.putString(ROLE, user.getRole()).commit();
//        editor.putString(STATUS, user.getStatus()).commit();
//        editor.putString(TOTAL_POST_BUY, user.getTotalBuyer()).commit();
//        editor.putString(TOTAL_POST_SELL, user.getTotalSeller()).commit();
//        editor.putString(SIGN_UP_DATE, user.getSignUpDate()).commit();

        editor.putString(TYPE, userResponse.getData().getType()).commit();
        editor.putString(ID, String.valueOf(userResponse.getData().getId())).commit();
        editor.putString(FIRST_NAME, userResponse.getData().getFirstname()).commit();
        editor.putString(LAST_NAME, userResponse.getData().getLastname()).commit();
        editor.putString(EMAIL, userResponse.getData().getEmail()).commit();
        editor.putString(PHONE, userResponse.getData().getPhone()).commit();
        editor.putString(GENDER, userResponse.getData().getGender()).commit();
        editor.putString(ADDRESS, userResponse.getData().getAddress()).commit();
        editor.putString(CITY, userResponse.getData().getCity()).commit();
        editor.putString(PROFILE, userResponse.getData().getProfilepicture()).commit();
        editor.putString(ROLE, userResponse.getData().getRole()).commit();
        editor.putString(STATUS, userResponse.getData().getStatus()).commit();
        editor.putString(TOTAL_POST_BUY, String.valueOf(userResponse.getData().getTotalpostsbuy())).commit();
        editor.putString(TOTAL_POST_SELL, String.valueOf(userResponse.getData().getTotalpostssell())).commit();
        editor.putString(SIGN_UP_DATE, String.valueOf(userResponse.getData().getSignupdate())).commit();
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

    public UserInFormationModel getUser() {

        UserInFormationModel user = new UserInFormationModel();
        user.setId(preferences.getString(ID, "N/A"));
        user.setType(preferences.getString(TYPE, "N/A"));
        user.setFirstName(preferences.getString(FIRST_NAME, "N/A"));
        user.setLastName(preferences.getString(LAST_NAME, "N/A"));
        user.setEmail(preferences.getString(EMAIL, "N/A"));
        user.setPhone(preferences.getString(PHONE, "N/A"));
        user.setGender(preferences.getString(GENDER, "N/A"));
        user.setAddress(preferences.getString(TYPE, "N/A"));
        user.setCity(preferences.getString(CITY, "N/A"));
        user.setProfile(preferences.getString(PROFILE, "N/A"));
        user.setRole(preferences.getString(ROLE, "N/A"));
        user.setStatus(preferences.getString(STATUS, "N/A"));
        user.setTotalBuyer(preferences.getString(TOTAL_POST_BUY, "N/A"));
        user.setTotalSeller(preferences.getString(TOTAL_POST_SELL, "N/A"));
        user.setAccessToken(preferences.getString(ACCESS_TOKEN, "N/A"));

        return user;

    }
}
