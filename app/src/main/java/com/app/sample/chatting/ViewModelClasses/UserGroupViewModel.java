package com.app.sample.chatting.ViewModelClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class UserGroupViewModel implements Serializable {
    private String date;

    private String snippet;
    private int photo;
    public long UserId;
    public String UserName;
    public int  AdminId;
    public Boolean Status;
    private ArrayList<FriendsViewModel> friends = new ArrayList<>();

    public UserGroupViewModel() {
        this.UserId = UserId;
        this.date = date;
        this.UserName = UserName;
        this.snippet = snippet;
        this.photo = photo;
        this.friends = friends;
    }

    public long getId() {
        return UserId;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return UserName;
    }

    public String getSnippet() {
        return snippet;
    }

    public int getPhoto() {
        return photo;
    }

    public ArrayList<FriendsViewModel> getFriends() {
        return friends;
    }
    public String getMember() {
        if (friends.size() > 100) {
            return "100+ sembers";
        }
        return (friends.size() + 1) + " members";
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public long getUserId() {
        return UserId;
    }

    public void setUserId(long userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getAdminId() {
        return AdminId;
    }

    public void setAdminId(int adminId) {
        AdminId = adminId;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public void setFriends(ArrayList<FriendsViewModel> friends) {
        this.friends = friends;
    }

}
