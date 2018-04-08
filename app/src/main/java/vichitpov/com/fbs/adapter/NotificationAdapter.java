package vichitpov.com.fbs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.model.NotificationModel;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<NotificationModel> notificationList;
    private Context context;


    public NotificationAdapter(Context context) {
        this.context = context;
        notificationList = new ArrayList<>();
    }

    public void addItem(List<NotificationModel> notificationList) {
        this.notificationList = notificationList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel notificationModel = notificationList.get(position);
        holder.title.setText(notificationModel.getTitle());

    }

    @Override
    public int getItemCount() {
        if (notificationList != null)
            return notificationList.size();
        return 0;
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        NotificationViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
        }
    }
}
