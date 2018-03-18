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

//    @Override
//    public int getItemViewType(int position) {
//        if (imageList.get(position) != null) {
//            return IMAGE_TYPE;
//        } else if (imageList.size() == 0 ) {
//            return FOOTER_TYPE;
//        }
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view;
////        if (viewType == IMAGE_TYPE) {
////            Log.e("pppp", "IMAGE_TYPE");
////            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_image_list, parent, false);
////            return new ImageViewHolder(view);
////        } else if (viewType == FOOTER_TYPE) {
////            Log.e("pppp", "FOOTER_TYPE");
////            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_footer, parent, false);
////            return new FooterViewHolder(view);
////        } else {
////            return null;
////
////        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ImageModel imageModel = imageList.get(position);
//        if (holder instanceof ImageViewHolder) {
//            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
//            if (imageModel.getImagetype() == ImageModel.IMAGETYPE.URL) {
//                Log.e("pppp", "URL: " + imageModel.getImagePath());
//                Picasso.with(context)
//                        .load(imageModel.getImagePath())
//                        .into(imageViewHolder.thumbnail);
//            } else if (imageModel.getImagetype() == ImageModel.IMAGETYPE.URI) {
//                Log.e("pppp", "URI: " + imageModel.getImagePath());
//                Picasso.with(context)
//                        .load(new File(imageModel.getImagePath()))
//                        .into(imageViewHolder.thumbnail);
//            }
//        } else if (holder instanceof FooterViewHolder) {
//            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
//            footerViewHolder.footer.setImageResource(R.drawable.ic_add);
//
//
//        }
//
//
//    }
//
//
//    @Override
//    public int getItemCount() {
//
//        return imageList.size() + 1;
//    }

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
                    .error(R.drawable.ic_unavailable)
                    .into(holder.thumbnail);

        } else if (imageModel.getImageType().equals(ImageModel.URI)) {
            Picasso.with(context)
                    .load(new File(imageModel.getImagePath()))
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
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickDelete.setOnClick(getAdapterPosition(), getAdapterPosition());
                }
            });

        }
    }
}