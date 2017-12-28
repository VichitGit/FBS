package vichitpov.com.fbs.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.model.CategoriesModel;

/**
 * Created by VichitPov on 12/27/2017.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<CategoriesModel> categoriesList;
    private Context context;

    public CategoryAdapter(List<CategoriesModel> categoriesList, Context context) {
        this.categoriesList = categoriesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_layout_main_category, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        CategoriesModel categoriesModel = categoriesList.get(position);
        holder.name.setText(categoriesModel.getName());
        holder.countPost.setText(categoriesModel.getCountPost() + " Post");


    }

    @Override
    public int getItemCount() {
        if (categoriesList != null) {
            return categoriesList.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, countPost;

        MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.text_title);
            countPost = itemView.findViewById(R.id.text_post_count);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
