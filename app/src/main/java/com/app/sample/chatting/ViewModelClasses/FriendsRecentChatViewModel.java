package com.app.sample.chatting.ViewModelClasses;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

public class FriendsRecentChatViewModel implements Serializable {

    private long senderId;
    private long RecieverId;
    private String SenderName;
    private String Text;
    private FriendsViewModel friend;

    public long getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(long messageStatus) {
        this.messageStatus = messageStatus;
    }

    private long messageStatus;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private String photo;

    public FriendsRecentChatViewModel(long senderId, long recieverId, String senderName, String text, FriendsViewModel friend) {
        this.senderId = senderId;
        RecieverId = recieverId;
        SenderName = senderName;
        Text = text;
        this.friend = friend;
    }


    public FriendsRecentChatViewModel(Context context, ArrayList<FriendsRecentChatViewModel> friendsRecentChatViewModels) {

    }

    public FriendsRecentChatViewModel() {
    }


    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getRecieverId() {
        return RecieverId;
    }

    public void setRecieverId(long recieverId) {
        RecieverId = recieverId;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public FriendsViewModel getFriend() {
        return friend;
    }

    public void setFriend(FriendsViewModel friend) {
        this.friend = friend;
    }
}
