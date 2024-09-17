package com.szniloycoder.foodapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.szniloycoder.foodapp.databinding.ActivitySignUpBinding;

import java.util.HashMap;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;

    FirebaseAuth auth;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    ProgressDialog dialog;
    String email;
    String password;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        currentUser = auth.getCurrentUser();

        ProgressDialog progressDialog = new ProgressDialog(this);
        this.dialog = progressDialog;
        progressDialog.setTitle("Creating your account");
        this.dialog.setMessage("Please wait...");

        binding.btnLogIn.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        binding.btnSignUpAcct.setOnClickListener(view -> {
            userName = binding.userNameTxt.getText().toString();
            email = binding.emlUserTxt.getText().toString();
            password = binding.passUserTxt.getText().toString();

            if (this.email.isEmpty()) {
                this.binding.emlUserTxt.setError("Please enter your email address.");
            } else if (this.password.isEmpty()) {
                this.binding.passUserTxt.setError("Please enter your password.");
                this.binding.passUserTxt.setError(null);
            } else if (this.userName.isEmpty()) {
                this.binding.userNameTxt.setError("Please enter your name");
            } else {
                uploadData();
                this.binding.emlUserTxt.setError(null);
                this.binding.passUserTxt.setError(null);
                this.binding.userNameTxt.setError(null);
            }

        });

    }


    private void uploadData() {
        this.dialog.show();
        this.auth.createUserWithEmailAndPassword(
                        binding.emlUserTxt.getText().toString(),
                        this.binding.passUserTxt.getText().toString())
                .addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        userName = binding.userNameTxt.getText().toString();
                        email = binding.emlUserTxt.getText().toString();
                        password = "";

                        String uid = (Objects.requireNonNull(auth.getCurrentUser())).getUid();

                        HashMap<String, Object> userSignup = new HashMap<>();
                        userSignup.put("userName", this.userName);
                        userSignup.put("email", this.email);
                        userSignup.put("id", uid);
                        userSignup.put("imageUrl", "default");
                        userSignup.put("OnlineStatus", false);
                        userSignup.put("addressTitle", "");
                        userSignup.put("address", "");
                        userSignup.put("phone", "");


                        database.getReference().child("user")
                                .child((Objects.requireNonNull((task.getResult())
                                        .getUser())).getUid()).setValue(userSignup)
                                .addOnCompleteListener(task1 -> {

                                    if (task1.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                        finish();
                                        return;
                                    }
                                    Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

                                });
                        return;
                    }
                    Toast.makeText(SignUpActivity.this,
                            Objects.requireNonNull(task.getException()).getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                });

    }
}