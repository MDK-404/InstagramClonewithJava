package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> postList;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        Post post = postList.get(position);
        holder.bind(post);

        holder.imageViewLike.setOnClickListener(view -> {
            // Toggle like state
            post.setLiked(!post.isLiked());

            if (post.isLiked()) {
                holder.imageViewLike.setImageResource(R.drawable.ic_filled_favorite_24); // Set liked icon
                // Update like count or perform other actions on like
            } else {
                holder.imageViewLike.setImageResource(R.drawable.ic_outline_favorite_border_24); // Set unliked icon
                // Update like count or perform other actions on unlike
            }
        });

        // Comment button handling
        holder.imageViewComment.setOnClickListener(view -> {


            // Handle comment click, open comment section, etc.
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewPost;
        private TextView textViewUsername;
        private TextView textViewDescription;
        private ImageView imageViewLike;
        private ImageView imageViewComment;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPost = itemView.findViewById(R.id.imageViewPost);
            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            imageViewLike = itemView.findViewById(R.id.imageViewLike);
            imageViewComment = itemView.findViewById(R.id.imageViewComment);
        }

        public void bind(Post post) {
            textViewUsername.setText(post.getUsername());
            textViewDescription.setText(post.getDescription());

            // Load the post image using Glide
            Glide.with(itemView.getContext())
                 .load(post.getImageUrl())
                 .placeholder(R.drawable.placeholder_image) // Placeholder image resource
                 .error(R.drawable.placeholder_image) // Error image resource if the load fails
                 .into(imageViewPost);
        }
    }
}
