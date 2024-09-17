package com.szniloycoder.foodapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.szniloycoder.foodapp.Models.FAQ_itemModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.ItemFaqBinding;

import java.util.ArrayList;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.FaqViewHolder> {

    private ArrayList<FAQ_itemModel> faqList;

    public FaqAdapter(ArrayList<FAQ_itemModel> faqList) {
        this.faqList = faqList;
    }


    @NonNull
    @Override
    public FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq, parent, false);
        return new FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqViewHolder holder, int position) {

        FAQ_itemModel faqItem = faqList.get(position);
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
        return faqList.size();
    }

    public static class FaqViewHolder extends RecyclerView.ViewHolder {
        ItemFaqBinding binding;

        public FaqViewHolder(View itemView) {
            super(itemView);
            this.binding = ItemFaqBinding.bind(itemView);
        }
    }
}
