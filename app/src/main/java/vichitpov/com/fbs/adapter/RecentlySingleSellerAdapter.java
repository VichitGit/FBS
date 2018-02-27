package vichitpov.com.fbs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.ConvertDate;
import vichitpov.com.fbs.constant.Url;
import vichitpov.com.fbs.retrofit.response.ProductResponse;

/**
 * Created by VichitPov on 2/26/18.
 */

public class RecentlySingleSellerAdapter extends RecyclerView.Adapter<RecentlySingleSellerAdapter.ProductViewHolder> {

    private Context context;
    private List<ProductResponse.Data> productList;

    public RecentlySingleSellerAdapter(Context context, List<ProductResponse.Data> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_layout_seller_single_page, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ProductResponse.Data productResponse = productList.get(position);

        String priceFrom = productResponse.getPrice().get(0).getMin().substring(0, productResponse.getPrice().get(0).getMin().indexOf("."));
        String priceTo = productResponse.getPrice().get(0).getMax().substring(0, productResponse.getPrice().get(0).getMax().indexOf("."));

        holder.title.setText(productResponse.getTitle());
        holder.price.setText(priceFrom + "$" + " - " + priceTo + "$");

        if (productResponse.getProductimages() != null && productResponse.getCreateddate() != null && productResponse.getContactaddress() != null) {

            String subDate = productResponse.getCreateddate().getDate().substring(0, 10);
            String dateConverted = ConvertDate.dateConverter(subDate);
            holder.date.setText("Posted: " + dateConverted);
            holder.address.setText(productResponse.getContactaddress());

            Log.e("pppp", Url.BASE_URL + productResponse.getProductimages().get(0));

            Picasso.with(context)
                    .load(Url.BASE_URL + productResponse.getProductimages().get(0))
                    .resize(200, 200)
                    .centerCrop()
                    .error(R.drawable.ic_unavailable)
                    .into(holder.thumbnail);

        }
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView title, price, date, address;
        private ImageView thumbnail, notification, favorite;

        ProductViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textTitle);
            price = itemView.findViewById(R.id.textPrice);
            date = itemView.findViewById(R.id.textDate);
            address = itemView.findViewById(R.id.textAddress);
            thumbnail = itemView.findViewById(R.id.imageThumbnail);
            favorite = itemView.findViewById(R.id.imageFavorite);
            notification = itemView.findViewById(R.id.imageNotification);

        }
    }
}
