package vichitpov.com.fbs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.base.BitmapTransform;
import vichitpov.com.fbs.callback.OnClickDelete;
import vichitpov.com.fbs.model.ImageModel;

/**
 * Created by VichitPov on 3/18/18.
 */

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {
    private List<ImageModel> imageList;
    private Context context;
    private OnClickDelete onClickDelete;


    public ImageListAdapter(Context context) {
        this.context = context;
        imageList = new ArrayList<>();
    }

    public void addItem(List<ImageModel> imageList) {
        this.imageList = imageList;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_image_list, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ImageModel imageModel = imageList.get(position);
        if (imageModel.getImageType().equals(ImageModel.URL)) {
            Picasso.with(context)
                    .load(imageModel.getImagePath())
                    .transform(new BitmapTransform(BitmapTransform.MAX_WIDTH, BitmapTransform.MAX_HEIGHT))
                    .resize(BitmapTransform.size, BitmapTransform.size)
                    .centerCrop()
                    .error(R.drawable.ic_unavailable)
                    .into(holder.thumbnail);

        } else if (imageModel.getImageType().equals(ImageModel.URI)) {
            Picasso.with(context)
                    .load(new File(imageModel.getImagePath()))
                    .transform(new BitmapTransform(BitmapTransform.MAX_WIDTH, BitmapTransform.MAX_HEIGHT))
                    .resize(BitmapTransform.size, BitmapTransform.size)
                    .centerCrop()
                    .error(R.drawable.ic_unavailable)
                    .into(holder.thumbnail);
        }
    }

    @Override
    public int getItemCount() {
        if (imageList != null) {
            return imageList.size();
        }
        return 0;
    }

    public void refreshData(int position) {
        this.imageList.remove(position);
        this.notifyItemRemoved(position);
        this.notifyItemRangeChanged(position, imageList.size());
    }

    public void onClickRemovePhoto(OnClickDelete onClickDelete) {
        this.onClickDelete = onClickDelete;
    }


    class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        ImageView remove;

        ImageViewHolder(View itemView) {
            super(itemView);

            thumbnail = itemView.findViewById(R.id.imageThumbnail);
            remove = itemView.findViewById(R.id.imageRemove);
            remove.setOnClickListener(view -> onClickDelete.setOnClick(getAdapterPosition(), getAdapterPosition()));

        }
    }
}