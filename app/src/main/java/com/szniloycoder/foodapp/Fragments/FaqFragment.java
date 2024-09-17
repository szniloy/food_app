package com.szniloycoder.foodapp.Fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szniloycoder.foodapp.Adapters.FaqAdapter;
import com.szniloycoder.foodapp.Models.FAQ_itemModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.FragmentFaqBinding;

import java.util.ArrayList;

public class FaqFragment extends Fragment {

     FragmentFaqBinding binding;
     Activity context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentFaqBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
    }

    private void setupRecyclerView() {
        binding.recyFaq.setLayoutManager(new LinearLayoutManager(context));

        ArrayList<FAQ_itemModel> faqList = new ArrayList<>();
        faqList.add(new FAQ_itemModel(
                "How do I place an order?",
                "Browse our menu, add items to your cart, choose your delivery or pickup option, and follow the checkout steps."
        ));
        faqList.add(new FAQ_itemModel(
                "How long will it take to get my food?",
                "Delivery times vary depending on your location and the restaurant's preparation time."
        ));

        binding.recyFaq.setAdapter(new FaqAdapter(faqList));
    }
}