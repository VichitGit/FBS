package vichitpov.com.fbs.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.InternetConnection;
import vichitpov.com.fbs.model.NotificationModel;
import vichitpov.com.fbs.retrofit.response.ProductPostedResponse;
import vichitpov.com.fbs.retrofit.service.ApiService;
import vichitpov.com.fbs.retrofit.service.ServiceGenerator;
import vichitpov.com.fbs.ui.activities.DetailProductActivity;
import vichitpov.com.fbs.ui.activities.MainActivity;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<NotificationModel> notificationList;
    private Context context;
    private ProgressDialog progressDialog;
    private ApiService apiService;


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
        if (!notificationModel.getPhone().isEmpty() && notificationModel.getPhone() != null) {
            holder.imagePhone.setVisibility(View.VISIBLE);
        } else {
            holder.imagePhone.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (notificationList != null)
            return notificationList.size();
        return 0;
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView imagePhone;

        NotificationViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            imagePhone = itemView.findViewById(R.id.imageCall);

            apiService = ServiceGenerator.createService(ApiService.class);
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...!");
            progressDialog.setCancelable(false);

            itemView.setOnClickListener(view -> getProductById(notificationList.get(getAdapterPosition()).getId()));
            imagePhone.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + notificationList.get(getAdapterPosition()).getPhone()));
                context.startActivity(intent);
            });
        }
    }


    private void getProductById(String id) {
        if (InternetConnection.isNetworkConnected(context)) {
            progressDialog.show();
            Call<ProductPostedResponse> call = apiService.getProductById(Integer.parseInt(id));
            call.enqueue(new Callback<ProductPostedResponse>() {
                @Override
                public void onResponse(Call<ProductPostedResponse> call, Response<ProductPostedResponse> response) {
                    if (response.isSuccessful()) {
                        //Log.e("pppp", "notification: " + response.body().toString());
                        progressDialog.dismiss();
                        Intent intent = new Intent(context, DetailProductActivity.class);
                        intent.putExtra("NotificationList", response.body().getData());
                        context.startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Connection Problem.", Toast.LENGTH_SHORT).show();
                        //Log.e("pppp", "notification: " + response.message() + " = " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ProductPostedResponse> call, Throwable t) {
                    t.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(context, "Server Problem.", Toast.LENGTH_SHORT).show();
                    //Log.e("pppp", "onFailure: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(context, "No Internet Connection.", Toast.LENGTH_SHORT).show();
        }

    }
}
