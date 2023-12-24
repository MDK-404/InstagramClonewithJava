package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class UserProfile extends AppCompatActivity {

    private TextView textViewUsername;
    private RecyclerView recyclerViewPosts;
    private List<Post> userPostsList;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                startActivity(new Intent(UserProfile.this, HomeFragment.class));
                return true;
            } else if (itemId == R.id.nav_search) {
                // Handle Search icon click - Show Search Fragment
                return true;
            } else if (itemId == R.id.nav_upload) {
                startActivity(new Intent(UserProfile.this, UploadActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(UserProfile.this, UserProfile.class));
                return true;
            }
            return false;
        });

        textViewUsername = findViewById(R.id.textViewUsername);
        recyclerViewPosts = findViewById(R.id.recyclerViewPosts);
        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));

        userPostsList = new ArrayList<>();
        postAdapter = new PostAdapter(userPostsList);
        recyclerViewPosts.setAdapter(postAdapter);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            String username = currentUser.getDisplayName();
            textViewUsername.setText(username);

            // Fetch and populate user's posts
            fetchUserPosts(userId);
        }

        // Handle logout button click
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(view -> logoutUser());
    }

    private void fetchUserPosts(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
          .whereEqualTo("userId", userId)
          .get()
          .addOnSuccessListener(queryDocumentSnapshots -> {
              userPostsList.clear();
              for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                  Post post = document.toObject(Post.class);
                  if (post != null) {
                      userPostsList.add(post);
                  }
              }
              postAdapter.notifyDataSetChanged(); // Notify adapter after updating the list
          })
          .addOnFailureListener(e -> {
              // Handle failure to fetch user posts
          });
    }

    private void logoutUser() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(UserProfile.this, LoginScreen.class));
        finish();
    }
}
