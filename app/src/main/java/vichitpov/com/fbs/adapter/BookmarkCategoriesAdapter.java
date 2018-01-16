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
import vichitpov.com.fbs.model.BookmarkModel;

/**
 * Created by VichitPov on 1/15/2018.
 */

public class BookmarkCategoriesAdapter extends RecyclerView.Adapter<BookmarkCategoriesAdapter.MyViewHolder> {
    private List<BookmarkModel> bookmarkList;
    private Context context;

    public BookmarkCategoriesAdapter(List<BookmarkModel> bookmarkList, Context context) {
        this.bookmarkList = bookmarkList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_bookmark_categories, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        BookmarkModel bookmarkModel = bookmarkList.get(position);
        holder.textTitle.setText(bookmarkModel.getTitle());
        holder.imageIcon.setImageResource(bookmarkModel.getIcon());
    }

    @Override
    public int getItemCount() {
        if (bookmarkList != null) {
            return bookmarkList.size();
        }
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageIcon, imageDelete;
        private TextView textTitle;

        MyViewHolder(View itemView) {
            super(itemView);
            imageIcon = itemView.findViewById(R.id.image_bookmark_category);
            textTitle = itemView.findViewById(R.id.text_title);


        }
    }
}
