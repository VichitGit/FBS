package vichitpov.com.fbs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.Retrofit;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.retrofit.response.FavoriteResponse;
import vichitpov.com.fbs.retrofit.response.ProductResponse;
import vichitpov.com.fbs.ui.activities.DetailProductActivity;
import vichitpov.com.fbs.ui.activities.MainActivity;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by VichitPov on 3/9/18.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private List<ProductResponse.Data> favoritesList;
    private Context context;

    public FavoriteAdapter(Context context) {
        favoritesList = new ArrayList<>();
        this.context = context;
    }

    public void addItem(List<ProductResponse.Data> favoritesList) {
        this.favoritesList = favoritesList;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_layout_top_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        ProductResponse.Data favoriteResponse = favoritesList.get(position);
        if (favoriteResponse != null) {
            holder.title.setText(favoriteResponse.getTitle());

            String priceFrom = favoriteResponse.getPrice().get(0).getMin().substring(0, favoriteResponse.getPrice().get(0).getMin().indexOf("."));
            String priceTo = favoriteResponse.getPrice().get(0).getMax().substring(0, favoriteResponse.getPrice().get(0).getMax().indexOf("."));

            holder.price.setText(priceFrom + "$" + " - " + priceTo + "$");
            if (favoriteResponse.getProductimages() != null && favoriteResponse.getCreateddate() != null && favoriteResponse.getContactaddress() != null) {


                if (favoriteResponse.getProductimages().size() != 0) {
                    Picasso.with(context)
                            .load(favoriteResponse.getProductimages().get(0))
                            .resize(200, 200)
                            .centerCrop()
                            .error(R.drawable.ic_unavailable)
                            .into(holder.thumbnail);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (favoritesList != null)
            return favoritesList.size();
        return 0;
    }

    class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private TextView title, price;
        private ImageView thumbnail;

        FavoriteViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textTitle);
            price = itemView.findViewById(R.id.textPrice);
            thumbnail = itemView.findViewById(R.id.imageThumbnail);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, DetailProductActivity.class);
                intent.putExtra("productList", favoritesList.get(getAdapterPosition()));
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

                UserInformationManager userInformationManager = UserInformationManager.getInstance(context.getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));
                if (!userInformationManager.getUser().getAccessToken().equals("N/A")) {
                    Retrofit.countView(userInformationManager.getUser().getAccessToken(), favoritesList.get(getAdapterPosition()).getId());
                }

            });

//            more.setOnClickListener(view -> {
//                PopupMenu popup = new PopupMenu(context, view);
//                popup.inflate(R.menu.menu_popup_menu);
//                popup.show();
//                popup.setOnMenuItemClickListener(item -> {
//                    if (item.getItemId() == R.id.popFavorite) {
//                        Toast.makeText(context, "Add to favorite", Toast.LENGTH_SHORT).show();
//                    } else if (item.getItemId() == R.id.popNotification) {
//                        Toast.makeText(context, "Send notification", Toast.LENGTH_SHORT).show();
//                    }
//                    return false;
//                });
//            });


        }
    }
}
