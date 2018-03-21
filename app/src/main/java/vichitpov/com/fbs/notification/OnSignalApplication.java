package vichitpov.com.fbs.notification;

import android.app.Application;
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
    }

    class OneSignalNotificationOpenHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            JSONObject data = result.notification.payload.additionalData;
            Toast.makeText(OnSignalApplication.this, "Open notification", Toast.LENGTH_SHORT).show();
            if (data != null) {

            }
        }
    }
}
