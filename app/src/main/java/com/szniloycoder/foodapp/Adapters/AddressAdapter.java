package com.szniloycoder.foodapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.szniloycoder.foodapp.Models.AddressModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.AddressViewBinding;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.viewHolder> {

    Context context;
    ArrayList<AddressModel> list;
    private int selectedPosition = -1;

    public AddressAdapter(Context context, ArrayList<AddressModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, @SuppressLint("RecyclerView") int position) {

        AddressModel model = list.get(position);

        // Set address title and description
        holder.binding.addrsTitleTxt.setText(model.getAddrTitle());
        holder.binding.adrsTxt.setText(model.getAddNewAddrs());

        // Set radio button state and listener
        holder.binding.btnSelectAddress.setChecked(position == selectedPosition);
        holder.binding.btnSelectAddress.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedPosition = position;
                notifyDataSetChanged(); // Notify adapter to refresh all items
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public AddressModel getSelectedAddress() {
        if (selectedPosition != -1) {
            return list.get(selectedPosition);
        }
        return null;
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        AddressViewBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = AddressViewBinding.bind(itemView);
        }
    }
}
