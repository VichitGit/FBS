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

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.Convert;
import vichitpov.com.fbs.base.Retrofit;
import vichitpov.com.fbs.callback.MyOnClickListener;
import vichitpov.com.fbs.constant.Url;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.ui.activities.DetailProductActivity;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by VichitPov on 2/26/18.
 */

public class RecentlySingleSellerAdapter extends RecyclerView.Adapter<RecentlySingleSellerAdapter.ProductViewHolder> {

    private Context context;
    private List<ProductResponse.Data> productList;
    private MyOnClickListener myOnClickListener;

    public RecentlySingleSellerAdapter(Context context) {
        this.context = context;
        productList = new ArrayList<>();
    }

    public void addItem(List<ProductResponse.Data> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_layout_seller_single_page, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductResponse.Data productResponse = productList.get(position);

        String priceFrom = productResponse.getPrice().get(0).getMin().substring(0, productResponse.getPrice().get(0).getMin().indexOf("."));
        String priceTo = productResponse.getPrice().get(0).getMax().substring(0, productResponse.getPrice().get(0).getMax().indexOf("."));

        holder.title.setText(productResponse.getTitle());
        holder.price.setText(priceFrom + "$" + " - " + priceTo + "$");

        if (productResponse.getProductimages() != null && productResponse.getCreateddate() != null && productResponse.getContactaddress() != null) {

            holder.address.setText(productResponse.getContactaddress());

            if (productResponse.getProductimages().size() != 0) {
                Picasso.with(context)
                        .load(productResponse.getProductimages().get(0))
                        .resize(200, 200)
                        .centerCrop()
                        .error(R.drawable.ic_unavailable)
                        .into(holder.thumbnail);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }


//    public void setOnCLickListener(MyOnClickListener myOnClickListener) {
//        if (productList != null) {
//            this.myOnClickListener = myOnClickListener;
//        }
//    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView title, price, address;
        private ImageView thumbnail;

        ProductViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textTitle);
            price = itemView.findViewById(R.id.textPrice);
            address = itemView.findViewById(R.id.textAddress);
            thumbnail = itemView.findViewById(R.id.imageThumbnail);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("productList", productList.get(getAdapterPosition()));
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                UserInformationManager userInformationManager = UserInformationManager.getInstance(context.getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
                if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
                    Retrofit.countView(userInformationManager.getUser().getAccessToken(), productList.get(getAdapterPosition()).getId());
                }
            });
        }
    }
}
