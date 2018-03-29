package vichitpov.com.fbs.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vichitpov.com.fbs.R;
import vichitpov.com.fbs.retrofit.response.ProductResponse;

/**
 * Created by VichitPov on 3/17/18.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<ProductResponse.DataContact> dataContactsList;
    private Context context;

    public ContactAdapter(Context context) {
        this.context = context;
        dataContactsList = new ArrayList<>();
    }

    public void addItem(List<ProductResponse.DataContact> dataContactsList) {
        this.dataContactsList = dataContactsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custom_layout_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ProductResponse.DataContact dataContact = dataContactsList.get(position);
        holder.name.setText(dataContact.getFirstname() + " " + dataContact.getLastname());
        holder.phone.setText(dataContact.getPhone());

    }

    @Override
    public int getItemCount() {
        if (dataContactsList != null) {
            return dataContactsList.size();
        }
        return 0;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView name, phone;
        private ImageView call;

        ContactViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textName);
            phone = itemView.findViewById(R.id.textPhone);
            call = itemView.findViewById(R.id.imageCall);

            call.setOnClickListener(view -> {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.layout_pop_up_phone);
                dialog.show();
                TextView textCall = dialog.findViewById(R.id.textCall);
                TextView textSms = dialog.findViewById(R.id.textSms);

                textCall.setOnClickListener(view12 -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + dataContactsList.get(getAdapterPosition()).getPhone()));
                    context.startActivity(intent);
                    dialog.dismiss();
                });

                textSms.setOnClickListener(view1 -> {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",
                            dataContactsList.get(getAdapterPosition()).getPhone(), null)));
                    dialog.dismiss();
                });
            });
        }
    }
}
