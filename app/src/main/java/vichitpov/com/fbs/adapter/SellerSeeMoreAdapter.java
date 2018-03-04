package vichitpov.com.fbs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.Convert;
import vichitpov.com.fbs.callback.OnLoadMore;
import vichitpov.com.fbs.constant.Url;
import vichitpov.com.fbs.retrofit.response.ProductResponse;

/**
 * Created by VichitPov on 2/27/18.
 */

public class SellerSeeMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_ITEM = 1;
    private static final int VIEW_PROGRESS = 0;

    public static final int gridLayoutManager = 10;
    public static final int linearLayoutManager = 15;

    private List<ProductResponse.Data> productList;
    private Context context;
    private OnLoadMore onLoadMore;
    private int checkLayoutManager;

    private int visibleThreshold = 3;
    private boolean loading = false;

    public SellerSeeMoreAdapter(Context context, RecyclerView recyclerView, int checkLayoutCheck) {
        this.productList = new ArrayList<>();
        this.context = context;
        this.checkLayoutManager = checkLayoutCheck;

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
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    //get position items in LayoutManager
                    int totalItemCount = gridLayoutManager.getItemCount();
                    int lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();

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

    public void clearList() {
        this.productList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return (productList.get(position) != null) ? VIEW_ITEM : VIEW_PROGRESS;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;

        if (viewType == VIEW_ITEM) {
            if (checkLayoutManager == linearLayoutManager) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_seller_see_more, parent, false);
                vh = new ProductViewHolder(view);
            } else if (checkLayoutManager == gridLayoutManager) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_user_post_sold, parent, false);
                vh = new ProductGridViewHolder(view);
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
            if (productResponse.getProductimages().size() != 0) {
                Picasso.with(context)
                        .load(Url.BASE_URL + productResponse.getProductimages().get(0))
                        .resize(200, 200)
                        .centerCrop()
                        .error(R.drawable.ic_unavailable)
                        .into(productViewHolder.thumbnail);
            }


        } else if (holder instanceof ProductGridViewHolder) {
            ProductGridViewHolder productGridViewHolder = (ProductGridViewHolder) holder;

            String priceFrom = productResponse.getPrice().get(0).getMin().substring(0, productResponse.getPrice().get(0).getMin().indexOf("."));
            String priceTo = productResponse.getPrice().get(0).getMax().substring(0, productResponse.getPrice().get(0).getMax().indexOf("."));

            productGridViewHolder.textTitle.setText(productResponse.getTitle());
            productGridViewHolder.textPrice.setText(priceFrom + "$" + " - " + priceTo + "$");
            productGridViewHolder.textStatus.setText(productResponse.getStatus());
            productGridViewHolder.textAddress.setText(productResponse.getContactaddress());

            if (productResponse.getProductimages().size() != 0) {
                Picasso.with(context)
                        .load(Url.BASE_URL + productResponse.getProductimages().get(0))
                        .resize(200, 200)
                        .centerCrop()
                        .error(R.drawable.ic_unavailable)
                        .into(productGridViewHolder.thumbnail);
            }

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


    class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView title, address, price, date;
        private ImageView favorite, notification, thumbnail;

        ProductViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textTitle);
            address = itemView.findViewById(R.id.textAddress);
            price = itemView.findViewById(R.id.textPrice);
            date = itemView.findViewById(R.id.textDate);
            thumbnail = itemView.findViewById(R.id.imageThumbnail);
            favorite = itemView.findViewById(R.id.imageFavorite);
            notification = itemView.findViewById(R.id.imageNotification);

        }
    }

    class ProductGridViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView textTitle, textAddress, textPrice, textEdit, textDelete, textStatus;

        ProductGridViewHolder(View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.textTitle);
            textAddress = itemView.findViewById(R.id.textAddress);
            textPrice = itemView.findViewById(R.id.textPrice);
            textEdit = itemView.findViewById(R.id.textEdit);
            textDelete = itemView.findViewById(R.id.textDelete);
            textStatus = itemView.findViewById(R.id.textStatus);
            thumbnail = itemView.findViewById(R.id.imageThumbnail);
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
