package vichitpov.com.fbs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.constant.AnyConstant;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.ui.activities.DetailProductActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by VichitPov on 2/25/18.
 */

public class RecentlySingleBuyerAdapter extends RecyclerView.Adapter<RecentlySingleBuyerAdapter.MyViewHolder> {

    private Context context;
    private List<ProductResponse.Data> productList;

    public RecentlySingleBuyerAdapter(Context context, List<ProductResponse.Data> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_layout_buyer_single_page, parent, false);
        return new MyViewHolder(view);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ProductResponse.Data productResponse = productList.get(position);

        String priceFrom = productResponse.getPrice().get(0).getMin().substring(0, productResponse.getPrice().get(0).getMin().indexOf("."));
        String priceTo = productResponse.getPrice().get(0).getMax().substring(0, productResponse.getPrice().get(0).getMax().indexOf("."));

        holder.title.setText(productResponse.getTitle());
        holder.price.setText(priceFrom + "$" + " - " + priceTo + "$");
        Picasso.with(context)
                .load(R.mipmap.ic_launcher)
                .resize(200, 200)
                .centerCrop()
                .error(R.drawable.ic_unavailable)
                .into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView title, price;
        private ImageView thumbnail;

        MyViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textTitle);
            price = itemView.findViewById(R.id.textPrice);
            thumbnail = itemView.findViewById(R.id.imageThumbnail);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailProductActivity.class);
            intent.putExtra(AnyConstant.PRODUCT_LIST, productList.get(getAdapterPosition()));
            intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }
    }
}
