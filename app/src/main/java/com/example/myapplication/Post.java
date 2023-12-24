package com.example.myapplication;

public class Post {
    private String userId;
    private String username;
    private String imageUrl;
    private String description;
    private boolean liked; // Field to track if post is liked

    // Default constructor (required by Firebase)
    public Post() {
        // Empty constructor needed for Firestore
    }

    public Post(String userId, String username, String imageUrl, String description, boolean liked) {
        this.userId = userId;
        this.username = username;
        this.imageUrl = imageUrl;
        this.description = description;
        this.liked = liked;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
