package vichitpov.com.fbs.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.model.CategoryHeaderModel;

/**
 * Created by VichitPov on 2/25/18.
 */

public class CategoryHeaderAdapter extends RecyclerView.Adapter<CategoryHeaderAdapter.MyViewHolder> {

    private List<CategoryHeaderModel> arrayList;
    private Context context;

    public CategoryHeaderAdapter(Context context, List<CategoryHeaderModel> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_layout_category_header, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

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

    class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView name;
        private ImageView icon;

        MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textName);
            icon = itemView.findViewById(R.id.imageIcon);
        }
    }


}
