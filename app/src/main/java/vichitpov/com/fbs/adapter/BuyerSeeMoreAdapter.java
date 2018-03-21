package vichitpov.com.fbs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.Convert;
import vichitpov.com.fbs.callback.OnClickDelete;
import vichitpov.com.fbs.callback.OnLoadMore;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.ui.activities.DetailProductActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by VichitPov on 2/26/18.
 */

public class BuyerSeeMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_ITEM = 1;
    private static final int VIEW_PROGRESS = 0;
    public static final int PRODUCT_POSTED_BUY = 3;
    public static final int PRODUCT_VIEW = 4;

    private List<ProductResponse.Data> productList;
    private Context context;
    private OnLoadMore onLoadMore;
    private int checkTypeProductView;

    private int visibleThreshold = 3;
    private boolean loading = false;
    private OnClickDelete onClickDelete;

    public BuyerSeeMoreAdapter(Context context, RecyclerView recyclerView, int checkTypeProductView) {
        this.context = context;
        this.checkTypeProductView = checkTypeProductView;
        productList = new ArrayList<>();

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //get position items in LayoutManager
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        loading = true;
                        onLoadMore.setOnLoadMore();
                    }
                }
            });
        }
    }

    public void onLoaded() {
        loading = false;
    }

    public boolean isLoading() {
        return loading;
    }

    public void addProgressBar() {
        productList.add(null);
        notifyItemInserted(productList.size() - 1);
    }


    public void onLoadMore(OnLoadMore onLoadMore) {

        this.onLoadMore = onLoadMore;

    }

    //after progressBar show, it will remove progressBar
    public void removeProgressBar() {
        productList.remove(productList.size() - 1);
        notifyItemRemoved(productList.size() - 1);
    }

    public void addMoreItems(List<ProductResponse.Data> articleList) {
        this.productList.addAll(articleList);
        notifyDataSetChanged();
    }

    public void refreshData(int position) {
        this.productList.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, productList.size());
    }


    @Override
    public int getItemViewType(int position) {
        return (productList.get(position) != null) ? VIEW_ITEM : VIEW_PROGRESS;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh = null;

        if (viewType == VIEW_ITEM) {
            if (checkTypeProductView == PRODUCT_VIEW) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_buyer_see_more, parent, false);
                vh = new ProductViewHolder(view);
            } else if (checkTypeProductView == PRODUCT_POSTED_BUY) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_user_post_buy, parent, false);
                vh = new ProductUserPostedBuyViewHolder(view);

            }
        } else if (viewType == VIEW_PROGRESS) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layour_progress_pagination, parent, false);
            vh = new ProgressPagination(view);
        }
        return vh;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductResponse.Data productResponse = productList.get(position);

        if (holder instanceof ProductViewHolder) {
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            productViewHolder.title.setText(productResponse.getTitle());

            String priceFrom = productResponse.getPrice().get(0).getMin().substring(0, productResponse.getPrice().get(0).getMin().indexOf("."));
            String priceTo = productResponse.getPrice().get(0).getMax().substring(0, productResponse.getPrice().get(0).getMax().indexOf("."));

            productViewHolder.price.setText(priceFrom + "$" + " - " + priceTo + "$");

            if (productResponse.getCreateddate() != null) {
                String subDate = productResponse.getCreateddate().getDate().substring(0, 10);
                String dateConverted = Convert.dateConverter(subDate);
                productViewHolder.date.setText("Posted: " + dateConverted);
            }

            if (productResponse.getContactaddress() != null) {
                productViewHolder.address.setText(productResponse.getContactaddress());
            }


        } else if (holder instanceof ProductUserPostedBuyViewHolder) {
            ProductUserPostedBuyViewHolder postedBuyViewHolder = (ProductUserPostedBuyViewHolder) holder;

            String priceFrom = productResponse.getPrice().get(0).getMin().substring(0, productResponse.getPrice().get(0).getMin().indexOf("."));
            String priceTo = productResponse.getPrice().get(0).getMax().substring(0, productResponse.getPrice().get(0).getMax().indexOf("."));

            if (productResponse.getContactaddress() != null) {
                postedBuyViewHolder.textAddress.setText(productResponse.getContactaddress());
            }
            postedBuyViewHolder.textTitle.setText(productResponse.getTitle());
            postedBuyViewHolder.textPrice.setText(priceFrom + "$" + " - " + priceTo + "$");
            postedBuyViewHolder.textStatus.setText(productResponse.getStatus());

        } else if (holder instanceof ProgressPagination) {
            ProgressPagination progressPagination = (ProgressPagination) holder;
            progressPagination.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();
        }
        return 0;
    }

    public void setOnDeleteClick(OnClickDelete onDeleteClick) {
        this.onClickDelete = onDeleteClick;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView title, address, price, date;
        private ImageView favorite, notification;

        ProductViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textTitle);
            address = itemView.findViewById(R.id.textAddress);
            price = itemView.findViewById(R.id.textPrice);
            date = itemView.findViewById(R.id.textDate);

        }
    }

    class ProductUserPostedBuyViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle, textAddress, textPrice, textEdit, textDelete, textStatus;

        ProductUserPostedBuyViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textAddress = itemView.findViewById(R.id.textAddress);
            textPrice = itemView.findViewById(R.id.textPrice);
            textEdit = itemView.findViewById(R.id.textEdit);
            textDelete = itemView.findViewById(R.id.textDelete);
            textStatus = itemView.findViewById(R.id.textStatus);


            textDelete.setOnClickListener(view -> onClickDelete.setOnClick(productList.get(getAdapterPosition()).getId(), getAdapterPosition()));

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("productList", productList.get(getAdapterPosition()));
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            });

        }
    }

    class ProgressPagination extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        ProgressPagination(View itemView) {
            super(itemView);

            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }
}
