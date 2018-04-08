package vichitpov.com.fbs.notification;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import vichitpov.com.fbs.sqlite.NotificationHelper;
import vichitpov.com.fbs.ui.activities.SplashScreenActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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
                .setNotificationReceivedHandler(new OneSignalNotificationReceivedHandler())
                .init();

    }

    class OneSignalNotificationOpenHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            JSONObject data = result.notification.payload.additionalData;
            //Log.e("pppp", "OneSignalNotificationOpenHandler");

            if (data != null) {
                //Log.e("pppp", "OneSignalNotificationOpenHandler: " + data.toString());
                String postId = data.optString("postId", null);
                Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                intent.putExtra("postId", postId);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    class OneSignalNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {

        @Override
        public void notificationReceived(OSNotification notification) {
            JSONObject data = notification.payload.additionalData;

            if (data != null) {
                //Log.e("pppp", "OneSignalNotification title: " + notification.payload.body);
                //Log.e("pppp", "OneSignalNotificationReceivedHandler: " + data.toString());
                String id = data.optString("postId", null);
                Log.e("pppp", "id: "+ id);
                String title = notification.payload.body;
                String notificationType = data.optString("notificationType", null);

                NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
                notificationHelper.insertNotification(id, title, notificationType);
            }
        }
    }
}
