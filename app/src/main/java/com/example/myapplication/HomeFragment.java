package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Post> postList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_posts);
        fetchPostsFromFirestore();
        return view;
    }

    private void fetchPostsFromFirestore() {
        FirebaseFirestore.getInstance().collection("posts")
                         .get()
                         .addOnSuccessListener(queryDocumentSnapshots -> {
                             for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                 Post post = documentSnapshot.toObject(Post.class);
                                 postList.add(post);
                             }
                             updateRecyclerView();
                         })
                         .addOnFailureListener(e -> {
                             Toast.makeText(requireContext(), "Failed to fetch posts", Toast.LENGTH_SHORT).show();
                         });
    }

    private void updateRecyclerView() {
        if (isAdded() && getContext() != null)
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        PostAdapter postAdapter = new PostAdapter(postList);
        recyclerView.setAdapter(postAdapter);
    }}
}
