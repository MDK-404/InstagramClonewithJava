package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.HashMap;
import java.util.Map;

public class UploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private EditText editTextDescription;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        imageView = findViewById(R.id.imageView);
        editTextDescription = findViewById(R.id.editTextDescription);
        Button buttonChooseImage = findViewById(R.id.buttonChooseImage);
        Button buttonPost = findViewById(R.id.buttonPost);

        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPost();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    public void uploadPost() {
        if (imageUri != null) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference("uploads");
            StorageReference fileReference = storageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

            fileReference.putFile(imageUri)
                         .addOnSuccessListener(taskSnapshot -> {
                             fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                 String imageUrl = uri.toString();
                                 String description = editTextDescription.getText().toString().trim();
                                 String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                 String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

                                 Map<String, Object> post = new HashMap<>();
                                 post.put("imageUrl", imageUrl);
                                 post.put("description", description);
                                 post.put("userId", userId);
                                 post.put("username", username);

                                 FirebaseFirestore.getInstance().collection("posts")
                                                  .add(post)
                                                  .addOnCompleteListener(task -> {
                                                      if (task.isSuccessful()) {
                                                          Toast.makeText(UploadActivity.this, "Post uploaded successfully", Toast.LENGTH_SHORT).show();
                                                          finish(); // Finish the UploadActivity
                                                          // Redirect user back to the main activity
                                                          Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                                                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                          startActivity(intent);
                                                      } else {
                                                          Toast.makeText(UploadActivity.this, "Failed to upload post", Toast.LENGTH_SHORT).show();
                                                          Log.e("UploadActivity", "Failed to upload post: " + task.getException().getMessage());
                                                      }
                                                  });
                             });
                         })
                         .addOnFailureListener(e -> {
                             Toast.makeText(UploadActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                             Log.e("UploadActivity", "Failed to upload image: " + e.getMessage());
                         });
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void Cancel(View view)
    {
        Intent intent1 = new Intent(UploadActivity.this, HomeFragment.class);
        startActivity(intent1);
    }
}
