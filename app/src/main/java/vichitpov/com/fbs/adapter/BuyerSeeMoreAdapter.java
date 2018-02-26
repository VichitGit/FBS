package vichitpov.com.fbs.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import vichitpov.com.fbs.R;

/**
 * Created by VichitPov on 2/26/18.
 */

public class BuyerSeeMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView title, address, price, date;
        private ImageView favorite, notification;

        public ProductViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textTitle);
            address = itemView.findViewById(R.id.textAddress);
            price = itemView.findViewById(R.id.textPrice);
            date = itemView.findViewById(R.id.textDate);
            favorite = itemView.findViewById(R.id.imageFavorite);
            notification = itemView.findViewById(R.id.imageNotification);


        }
    }

    class ProgressPagination extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        public ProgressPagination(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }

}
