package vichitpov.com.fbs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.callback.OnClickSingle;
import vichitpov.com.fbs.model.CategoryHeaderModel;
import vichitpov.com.fbs.preference.UserInformationManager;
import vichitpov.com.fbs.ui.activities.SettingsActivity;
import vichitpov.com.fbs.ui.activities.login.StartLoginActivity;
import vichitpov.com.fbs.ui.activities.profile.FavoriteActivity;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by VichitPov on 2/25/18.
 */

public class CategoryHeaderAdapter extends RecyclerView.Adapter<CategoryHeaderAdapter.MyViewHolder> {

    private List<CategoryHeaderModel> arrayList;
    private Context context;
    private OnClickSingle onClickSingle;

    public CategoryHeaderAdapter(Context context, List<CategoryHeaderModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_layout_category_header, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CategoryHeaderModel model = arrayList.get(position);
        holder.name.setText(model.getName());
        holder.icon.setImageResource(model.getIcon());

    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public void setOnClickListener(OnClickSingle onClickListener) {
        this.onClickSingle = onClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView icon;

        MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textName);
            icon = itemView.findViewById(R.id.imageIcon);

            itemView.setOnClickListener(view -> {
                if (getAdapterPosition() == 2) {
                    onClickSingle.setOnClick();
                } else if (getAdapterPosition() == 1) {
                    UserInformationManager userInformationManager = UserInformationManager.getInstance(context.getSharedPreferences(UserInformationManager.PREFERENCES_USER_INFORMATION, MODE_PRIVATE));

                    if (userInformationManager.getUser().getAccessToken().equals("N/A")) {
                        Intent intent = new Intent(context, StartLoginActivity.class);
                        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, FavoriteActivity.class);
                        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                } else if (getAdapterPosition() == 0) {
                    Intent intent = new Intent(context, SettingsActivity.class);
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }
    }


}
