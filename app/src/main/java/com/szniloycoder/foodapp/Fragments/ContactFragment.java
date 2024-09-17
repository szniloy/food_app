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

import com.szniloycoder.foodapp.Adapters.ContactAdapter;
import com.szniloycoder.foodapp.Models.ContactModel;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.databinding.FragmentContactBinding;

import java.util.ArrayList;

public class ContactFragment extends Fragment {

    ContactAdapter adapter;
     FragmentContactBinding binding;
     Activity context;
     ArrayList<ContactModel> faqList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        faqList = new ArrayList<>();
        binding.recyContact.setLayoutManager(new LinearLayoutManager(context));

        // Initialize the FAQ list
        faqList.add(new ContactModel("Email", "foodapp@gmail.com"));
        faqList.add(new ContactModel("Website", "https://www.foodapp.com"));
        faqList.add(new ContactModel("Facebook", "https://www.facebook.com/FoodApp"));
        faqList.add(new ContactModel("Instagram", "https://www.instagram.com/FoodApp"));

        // Set up the adapter
        adapter = new ContactAdapter(faqList);
        binding.recyContact.setAdapter(adapter);
    }
}