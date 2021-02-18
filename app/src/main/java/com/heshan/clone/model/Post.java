package com.heshan.clone.model;

public class Post {
    private static String PostId;
    private  String ImageUrl;
    private  String Description;
    private  String publisher;
    private String Title;
    private String ContactNo;
    private String Location;

    public Post() {
    }

    public Post(String postId, String imageUrl, String description, String publisher, String title, String contactNo, String location) {
        PostId = postId;
        ImageUrl = imageUrl;
        Description = description;
        this.publisher = publisher;
        Title = title;
        ContactNo = contactNo;
        Location = location;
    }

    public static String getPostId() { return PostId; }

    public void setPostId(String postId) {
        PostId = postId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}

