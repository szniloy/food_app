package com.szniloycoder.foodapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.szniloycoder.foodapp.Fragments.ChatFragment;
import com.szniloycoder.foodapp.Fragments.ExploreFragment;
import com.szniloycoder.foodapp.Fragments.FavouriteFragment;
import com.szniloycoder.foodapp.Fragments.HomeFragment;
import com.szniloycoder.foodapp.Fragments.ProfileFragment;
import com.szniloycoder.foodapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    HomeFragment homeFragment;
    ExploreFragment exploreFragment;
    FavouriteFragment favouriteFragment;
    ChatFragment chatsFragment;
    ProfileFragment profileFragment;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));


        // Initialize fragments
        homeFragment = new HomeFragment();
        favouriteFragment = new FavouriteFragment();
        exploreFragment = new ExploreFragment();
        chatsFragment = new ChatFragment();
        profileFragment = new ProfileFragment();

        // Check if user is logged in
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }


        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.homeFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, homeFragment).commit();
            }
            if (item.getItemId() == R.id.exploreFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, exploreFragment).commit();
            }
            if (item.getItemId() == R.id.favouriteFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, favouriteFragment).commit();
            }
            if (item.getItemId() == R.id.chatFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, chatsFragment).commit();
            }
            if (item.getItemId() != R.id.profileFragment) {
                return true;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout, profileFragment).commit();
            return true;
        });

        // Set default fragment
        binding.bottomNavigation.setSelectedItemId(R.id.homeFragment);
    }
}