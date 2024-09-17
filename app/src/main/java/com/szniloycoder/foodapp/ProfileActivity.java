package com.szniloycoder.foodapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.szniloycoder.foodapp.Models.UserModel;
import com.szniloycoder.foodapp.databinding.ActivityProfileBinding;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    private static final int IMAGE_REQ = 1;

     FirebaseAuth auth;
     FirebaseDatabase database;
     FirebaseUser fUser;
     StorageReference storageReference;
     DatabaseReference reference;
     StorageTask uploadTask;

     Uri imageUri;
     String userName;
     String email;
     String phone;

     ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setStatusBarColor(getResources().getColor(R.color.activity_color));

        initializeFirebase();
        setupListeners();
        setUserProfile();
    }

    private void initializeFirebase() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        fUser = auth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference().child("upproimg");
    }

    private void setupListeners() {
        binding.proImgView.setOnClickListener(view -> openImgFolder());
        binding.btnUpdateProfile.setOnClickListener(view -> updateProfile());
        binding.btnBack.setOnClickListener(view -> finish());
        binding.editProfile.setOnClickListener(view -> toggleEditMode());

        // Disable editing fields initially
        binding.nameTxt.setEnabled(false);
        binding.emlTxt.setEnabled(false);
        binding.phoneTxt.setEnabled(false);
    }

    private void toggleEditMode() {
        boolean isEditing = !binding.nameTxt.isEnabled();
        binding.nameTxt.setEnabled(isEditing);
        binding.phoneTxt.setEnabled(isEditing);

        int editIcon = isEditing ? R.drawable.edit_off : R.drawable.edit;
        binding.editProfile.setImageResource(editIcon);
        Toast.makeText(this, isEditing ? "Edit mode on" : "Edit mode off", Toast.LENGTH_SHORT).show();
    }

    private void setUserProfile() {
        String userId = fUser.getUid();
        if (userId != null) {
            database.getReference().child("user").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    UserModel userProfile = snapshot.getValue(UserModel.class);
                    if (userProfile != null) {
                        binding.nameTxt.setText(userProfile.getUserName());
                        binding.phoneTxt.setText(userProfile.getPhone());
                        binding.emlTxt.setText(userProfile.getEmail());

                        if ("default".equals(userProfile.getImageUrl())) {
                            binding.proImgView.setImageResource(R.drawable.profile);
                        } else {
                            Glide.with(ProfileActivity.this).load(userProfile.getImageUrl()).into(binding.proImgView);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle error
                }
            });
        }
    }

    private void updateProfile() {
        userName = binding.nameTxt.getText().toString().trim();
        email = binding.emlTxt.getText().toString().trim();
        phone = binding.phoneTxt.getText().toString().trim();
        updateUserProfile(userName, phone);
    }

    private void updateUserProfile(final String userName, final String phone) {
        String userId = fUser.getUid();
        if (userId != null) {
            final DatabaseReference userRef = database.getReference("user").child(userId);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    UserModel model = snapshot.getValue(UserModel.class);
                    if (model != null) {
                        model.setUserName(userName);
                        model.setPhone(phone);
                        userRef.setValue(model).addOnSuccessListener(aVoid ->
                                Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                        ).addOnFailureListener(e ->
                                Toast.makeText(ProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                        );
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Handle error
                }
            });
        }
    }

    private void openImgFolder() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQ);
    }

    private void uploadImage() {
        if (imageUri != null) {
            ProgressDialog pd = new ProgressDialog(this);
            pd.setMessage("Uploading");
            pd.show();

            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String mUri = task.getResult().toString();
                    reference = database.getReference().child("user").child(fUser.getUid());
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("imageUrl", mUri);
                    reference.updateChildren(map).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(ProfileActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProfileActivity.this, "Database update failed", Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    });
                } else {
                    Toast.makeText(ProfileActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQ && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            if (uploadTask == null || !uploadTask.isInProgress()) {
                uploadImage();
            } else {
                Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
            }
        }
    }
}