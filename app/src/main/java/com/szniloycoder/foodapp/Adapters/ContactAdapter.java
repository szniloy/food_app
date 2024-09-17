package com.szniloycoder.foodapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.szniloycoder.foodapp.Models.ContactModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.ItemFaqBinding;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.viewHolder>{

    private ArrayList<ContactModel> contactList;

    public ContactAdapter(ArrayList<ContactModel> contactList) {
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new viewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        ContactModel faqItem = contactList.get(position);
        holder.binding.question.setText(faqItem.getQuestion());
        holder.binding.answer.setText(faqItem.getAnswer());
        holder.binding.answer.setVisibility(faqItem.isExpanded() ? View.VISIBLE : View.GONE);

        holder.binding.question.setOnClickListener(v -> {
            faqItem.setExpanded(!faqItem.isExpanded());
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        ItemFaqBinding binding;

        public viewHolder(View itemView) {
            super(itemView);
            this.binding = ItemFaqBinding.bind(itemView);
        }
    }
}
