package vichitpov.com.fbs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.model.UserModel;

/**
 * Created by VichitPov on 1/1/2018.
 */

public class UserSoldAdapter extends RecyclerView.Adapter<UserSoldAdapter.RecentPostViewHolder> {

    private Context context;
    private List<UserModel> postList;

    public UserSoldAdapter(Context context, List<UserModel> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public RecentPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_user_sold, parent, false);
        return new RecentPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecentPostViewHolder holder, int position) {
        UserModel userModel = postList.get(position);
        holder.title.setText(userModel.getTitle());
        holder.address.setText("Address: " + userModel.getAddress());
        holder.price.setText("Price: " + userModel.getPrice() + "$");

    }

    @Override
    public int getItemCount() {
        if (postList != null)
            return postList.size();
        return 0;
    }

    class RecentPostViewHolder extends RecyclerView.ViewHolder {
        private TextView title, address, price;

        RecentPostViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.text_title);
            address = itemView.findViewById(R.id.text_address);
            price = itemView.findViewById(R.id.text_price);


        }
    }

}
