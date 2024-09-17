package com.szniloycoder.foodapp.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.szniloycoder.foodapp.AddressActivity;
import com.szniloycoder.foodapp.HelpActivity;
import com.szniloycoder.foodapp.LoginActivity;
import com.szniloycoder.foodapp.Models.UserModel;
import com.szniloycoder.foodapp.MyOrdersActivity;
import com.szniloycoder.foodapp.Password_ManagerActivity;
import com.szniloycoder.foodapp.Privacy_policyActivity;
import com.szniloycoder.foodapp.ProfileActivity;
import com.szniloycoder.foodapp.R;
import com.szniloycoder.foodapp.SettingsActivity;
import com.szniloycoder.foodapp.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

     FirebaseAuth auth;
     FragmentProfileBinding binding;
     Activity context;
     FirebaseDatabase database;
     FirebaseUser firebaseUser;
     DatabaseReference reference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        setUserData();

        // Set up button click listeners
        binding.btnLogOut.setOnClickListener(v -> showAlertDialog());
        binding.btnProfile.setOnClickListener(v -> startActivity(new Intent(context, ProfileActivity.class)));
        binding.btnPasswordManager.setOnClickListener(v -> startActivity(new Intent(context, Password_ManagerActivity.class)));
        binding.btnPrivacy.setOnClickListener(v -> startActivity(new Intent(context, Privacy_policyActivity.class)));
        binding.btnSettings.setOnClickListener(v -> startActivity(new Intent(context, SettingsActivity.class)));
        binding.btnAddressManager.setOnClickListener(v -> startActivity(new Intent(context, AddressActivity.class)));
        binding.btnMyOrdr.setOnClickListener(v -> startActivity(new Intent(context, MyOrdersActivity.class)));
        binding.btnHelp.setOnClickListener(v -> startActivity(new Intent(context, HelpActivity.class)));
    }

    private void showAlertDialog() {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.logout_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        AppCompatButton btnYesLog = dialogView.findViewById(R.id.btnYesLog);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        AppCompatButton btnCancelLog = dialogView.findViewById(R.id.btnCancelLog);

        btnYesLog.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(context, LoginActivity.class));
        });

        btnCancelLog.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void setUserData() {
        reference = database.getReference().child("user").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel model = snapshot.getValue(UserModel.class);

                if (model != null) {
                    binding.userProNameTxt.setText(model.getUserName());

                    if ("default".equals(model.getImageUrl())) {
                        binding.proImgView.setImageResource(R.drawable.profile);
                    } else {
                        Glide.with(context).load(model.getImageUrl()).into(binding.proImgView);
                    }
                } else {
                    throw new AssertionError("User model is null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle potential errors here
            }
        });
    }
}