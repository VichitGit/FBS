package vichitpov.com.fbs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import vichitpov.com.fbs.retrofit.response.ProductResponse;

/**
 * Created by VichitPov on 3/23/18.
 */

//public class StatusProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private List<ProductResponse.Data> productList;
//    private Context context;
//
//    private static final int BUY_TYPE = 1;
//    private static final int SELL_TYPE = 2;
//
//    public StatusProductAdapter(Context context) {
//        Log.e("", "");
//        this.context = context;
//        productList = new ArrayList<>();
//    }
//
//    public void addItem(List<ProductResponse.Data> productList) {
//        this.productList = productList;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (productList.get(position).getType().equals("buy")) {
//            return BUY_TYPE;
//
//        } else if (productList.get(position).getType().equals("sell")) {
//            return SELL_TYPE;
//        }
//
//        return 0;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder vh = null;
//        if (viewType == BUY_TYPE) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_expired, parent, false);
//            vh = new BuyViewHolder(view);
//        } else if (viewType == SELL_TYPE) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_expired, parent, false);
//            vh = new SellViewHolder(view);
//        }
//
//        return vh;
//
//    }
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ProductResponse.Data productResponse = productList.get(position);
//
//        if (holder instanceof BuyViewHolder) {
//            BuyViewHolder buyViewHolder = (BuyViewHolder) holder;
//
//            String priceFrom = productResponse.getPrice().get(0).getMin();
//            String priceTo = productResponse.getPrice().get(0).getMax();
//
//            int priceSubFrom = Integer.parseInt(priceFrom.substring(priceFrom.lastIndexOf(".") + 1));
//            int priceSubTo = Integer.parseInt(priceTo.substring(priceTo.lastIndexOf(".") + 1));
//
//            if (priceSubFrom == 0) {
//                priceFrom = productResponse.getPrice().get(0).getMin().substring(0, productResponse.getPrice().get(0).getMax().indexOf("."));
//            }
//
//            if (priceSubTo == 0) {
//                priceTo = productResponse.getPrice().get(0).getMax().substring(0, productResponse.getPrice().get(0).getMin().indexOf("."));
//
//            }
//
//            if (productResponse.getContactaddress() != null) {
//                buyViewHolder.textAddress.setText(productResponse.getContactaddress());
//            }
//            buyViewHolder.textTitle.setText(productResponse.getTitle());
//            buyViewHolder.textPrice.setText(priceFrom + "$" + " - " + priceTo + "$");
//            buyViewHolder.textStatus.setText(productResponse.getStatus());
//
//        } else if (holder instanceof SellViewHolder) {
//            SellViewHolder sellViewHolder = (SellViewHolder) holder;
//
//            String priceFrom = productResponse.getPrice().get(0).getMin();
//            String priceTo = productResponse.getPrice().get(0).getMax();
//
//            int priceSubFrom = Integer.parseInt(priceFrom.substring(priceFrom.lastIndexOf(".") + 1));
//            int priceSubTo = Integer.parseInt(priceTo.substring(priceTo.lastIndexOf(".") + 1));
//
//            if (priceSubFrom == 0) {
//                priceFrom = productResponse.getPrice().get(0).getMin().substring(0, productResponse.getPrice().get(0).getMax().indexOf("."));
//            }
//
//            if (priceSubTo == 0) {
//                priceTo = productResponse.getPrice().get(0).getMax().substring(0, productResponse.getPrice().get(0).getMin().indexOf("."));
//
//            }
//
//            sellViewHolder.textTitle.setText(productResponse.getTitle());
//            sellViewHolder.textPrice.setText(priceFrom + "$" + " - " + priceTo + "$");
//            sellViewHolder.textStatus.setText(productResponse.getStatus());
//            sellViewHolder.textAddress.setText(productResponse.getContactaddress());
//
//            if (productResponse.getProductimages().size() != 0) {
//                Picasso.with(context)
//                        .load(productResponse.getProductimages().get(0))
//                        .resize(200, 200)
//                        .centerCrop()
//                        .error(R.drawable.ic_unavailable)
//                        .into(sellViewHolder.thumbnail);
//            }
//        }
//
//    }
//
//    @Override
//    public int getItemCount() {
//        if (productList != null) {
//            return productList.size();
//        }
//        return 0;
//    }
//
//
//    class BuyViewHolder extends RecyclerView.ViewHolder {
//        private TextView textTitle, textAddress, textPrice, textEdit, textDelete, textStatus;
//        private RelativeLayout relativeAction;
//
//        public BuyViewHolder(View itemView) {
//            super(itemView);
//
//            textTitle = itemView.findViewById(R.id.textTitle);
//            textAddress = itemView.findViewById(R.id.textAddress);
//            textPrice = itemView.findViewById(R.id.textPrice);
//            textEdit = itemView.findViewById(R.id.textEdit);
//            textDelete = itemView.findViewById(R.id.textDelete);
//            textStatus = itemView.findViewById(R.id.textStatus);
//            relativeAction = itemView.findViewById(R.id.relativeAction);
//            relativeAction.setVisibility(View.GONE);
//
//        }
//    }
//
//    class SellViewHolder extends RecyclerView.ViewHolder {
//
//        private ImageView thumbnail;
//        private RelativeLayout relativeAction;
//        private TextView textTitle, textAddress, textPrice, textStatus;
//
//        SellViewHolder(View itemView) {
//            super(itemView);
//
//            textTitle = itemView.findViewById(R.id.textTitle);
//            textAddress = itemView.findViewById(R.id.textAddress);
//            textPrice = itemView.findViewById(R.id.textPrice);
//            textStatus = itemView.findViewById(R.id.textStatus);
//            thumbnail = itemView.findViewById(R.id.imageThumbnail);
//            relativeAction = itemView.findViewById(R.id.relativeAction);
//            relativeAction.setVisibility(View.GONE);
//
//        }
//    }
//
//}


public class StatusProductAdapter extends RecyclerView.Adapter<StatusProductAdapter.ExpiredViewHolder> {
    private List<ProductResponse.Data> productList;
    private Context context;


    public StatusProductAdapter(Context context) {
        productList = new ArrayList<>();
        this.context = context;
    }

    public void addItem(List<ProductResponse.Data> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ExpiredViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_expired, parent, false);
        return new ExpiredViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ExpiredViewHolder holder, int position) {
        ProductResponse.Data productResponse = productList.get(position);

        if (productResponse.getType().equals("buy")) {
            holder.thumbnail.setVisibility(View.GONE);
        } else if (productResponse.getType().equals("sell")) {
            holder.thumbnail.setVisibility(View.VISIBLE);
            if (productResponse.getProductimages().size() != 0) {
                Picasso.with(context)
                        .load(productResponse.getProductimages().get(0))
                        .resize(200, 200)
                        .centerCrop()
                        .error(R.drawable.ic_unavailable)
                        .into(holder.thumbnail);
            }
        }

        String priceFrom = productResponse.getPrice().get(0).getMin();
        String priceTo = productResponse.getPrice().get(0).getMax();

        int priceSubFrom = Integer.parseInt(priceFrom.substring(priceFrom.lastIndexOf(".") + 1));
        int priceSubTo = Integer.parseInt(priceTo.substring(priceTo.lastIndexOf(".") + 1));

        if (priceSubFrom == 0) {
            priceFrom = productResponse.getPrice().get(0).getMin().substring(0, productResponse.getPrice().get(0).getMax().indexOf("."));
        }

        if (priceSubTo == 0) {
            priceTo = productResponse.getPrice().get(0).getMax().substring(0, productResponse.getPrice().get(0).getMin().indexOf("."));

        }

        holder.textTitle.setText(productResponse.getTitle());
        holder.textPrice.setText(priceFrom + "$" + " - " + priceTo + "$");
        holder.textStatus.setText(productResponse.getStatus());
        holder.textAddress.setText(productResponse.getContactaddress());

    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    class ExpiredViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView textTitle, textAddress, textPrice, textStatus, textDate;

        ExpiredViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textAddress = itemView.findViewById(R.id.textAddress);
            textPrice = itemView.findViewById(R.id.textPrice);
            textDate = itemView.findViewById(R.id.textDate);
            textStatus = itemView.findViewById(R.id.textStatus);
            thumbnail = itemView.findViewById(R.id.imageThumbnail);
        }
    }
}
