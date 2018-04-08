package vichitpov.com.fbs.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.adapter.NotificationAdapter;
import vichitpov.com.fbs.model.NotificationModel;
import vichitpov.com.fbs.sqlite.NotificationHelper;

public class NotificationActivity extends AppCompatActivity {
    private NotificationHelper notificationHelper;
    private RecyclerView recyclerView;
    private ImageView imageBack;
    private NotificationAdapter adapter;
    private LinearLayout linearNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        notificationHelper = new NotificationHelper(this);

        initView();
        setUpRecyclerView();
        setUpNotification();

        imageBack.setOnClickListener(view -> finish());


    }

    private void setUpNotification() {
        if (notificationHelper.getNotificationList().size() == 0) {
            linearNotification.setVisibility(View.VISIBLE);
        } else {
            linearNotification.setVisibility(View.GONE);
            adapter.addItem(notificationHelper.getNotificationList());
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
