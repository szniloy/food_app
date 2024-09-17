package com.szniloycoder.foodapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.szniloycoder.foodapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ActivityLoginBinding binding;
    FirebaseUser currentUser;
    ProgressDialog dialog;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        auth = FirebaseAuth.getInstance();
        currentUser = auth.getCurrentUser();


        ProgressDialog progressDialog = new ProgressDialog(this);
        this.dialog = progressDialog;
        progressDialog.setTitle("Log in your account");
        this.dialog.setMessage("Please wait...");

        binding.btnSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            intent.putExtra("fromSignup", true);
            startActivity(intent);
        });

        if (this.currentUser != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

//        binding.btnForgetPass.setOnClickListener(view -> {
//            Intent intent = new Intent(this, ForgetPassActivity.class);
//            startActivity(intent);
//        });

        binding.btnLogInAcct.setOnClickListener(view -> {
            email = binding.emailTxt.getText().toString();
            password = binding.passwordTxt.getText().toString();

            if (this.email.isEmpty()) {
                this.binding.emailTxt.setError("Please enter your email address.");
            } else if (this.password.isEmpty()) {
                this.binding.passwordTxt.setError("Please enter your password.");
                this.binding.emailTxt.setError( null);
            } else {
                uploadData();
                this.binding.emailTxt.setError(null);
                this.binding.passwordTxt.setError( null);
            }
        });

    }

    private void uploadData() {
        this.dialog.show();
        this.auth.signInWithEmailAndPassword(this.binding.emailTxt.getText().toString(),
                        this.binding.passwordTxt.getText().toString())
                .addOnCompleteListener(task -> {

                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        return;
                    }
                    Toast.makeText(LoginActivity.this, "Incorrect Username and password", Toast.LENGTH_SHORT).show();

                }).addOnFailureListener(e ->
                        Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show());

    }


}