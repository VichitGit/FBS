package vichitpov.com.fbs.notification;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by VichitPov on 3/20/18.
 */

public class OnSignalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationOpenedHandler(new OneSignalNotificationOpenHandler())
                .init();

        Log.e("pppp", "OnSignalApplication");
    }

    class OneSignalNotificationOpenHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            JSONObject data = result.notification.payload.additionalData;

            Log.e("pppp", "OneSignalNotificationOpenHandler");

            if (data != null) {

                Log.e("ppppp", data.toString());
                String postId = data.optString("postId", null);
                String notificationType = data.optString("notificationType", null);
                String postCreate = data.optString("postCreate", null);

                Toast.makeText(OnSignalApplication.this, "Clicked notification", Toast.LENGTH_SHORT).show();
                Log.e("pppp", postId + " | " + notificationType + " | " + postCreate);
            }
        }
    }
}
