package vichitpov.com.fbs.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.NotificationAdapter;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.sqlite.NotificationHelper;

public class NotificationActivity extends AppCompatActivity {
    private NotificationHelper notificationHelper;
    private RecyclerView recyclerView;
    private ImageView imageBack;
    private NotificationAdapter adapter;
    private LinearLayout linearNotification;
    private UserInformationManager userInformationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        userInformationManager = UserInformationManager.getInstance(getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
        notificationHelper = new NotificationHelper(this);

        initView();
        setUpRecyclerView();
        setUpNotification();

        imageBack.setOnClickListener(view -> finish());
    }

    private void setUpNotification() {
        if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
            if (notificationHelper.getNotificationList().size() == 0) {
                linearNotification.setVisibility(View.VISIBLE);
            } else {
                linearNotification.setVisibility(View.GONE);
                adapter.addItem(notificationHelper.getNotificationList());
            }
        }
    }

    private void setUpRecyclerView() {
        adapter = new NotificationAdapter(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler);
        imageBack = findViewById(R.id.imageBack);
        linearNotification = findViewById(R.id.linearNotification);
        linearNotification.setVisibility(View.GONE);
    }
}
