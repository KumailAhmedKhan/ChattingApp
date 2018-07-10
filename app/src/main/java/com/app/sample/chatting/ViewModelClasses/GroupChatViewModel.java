package com.app.sample.chatting.ViewModelClasses;

import java.io.Serializable;

public class GroupChatViewModel implements Serializable {

    private long id;
    private String date;
    private FriendsViewModel friend;
    private String content;
    private boolean fromMe;

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    private String GroupName;

    public long getReceiverGroupId() {
        return ReceiverGroupId;
    }

    public void setReceiverGroupId(long receiverGroupId) {
        ReceiverGroupId = receiverGroupId;
    }

    private long ReceiverGroupId;

    public long getMessageID() {
        return MessageID;
    }

    public void setMessageID(long messageID) {
        MessageID = messageID;
    }

    public long getMessageStatus() {
        return MessageStatus;
    }

    public void setMessageStatus(long messageStatus) {
        MessageStatus = messageStatus;
    }

    private long MessageID;
    private long MessageStatus;
   // private long SenderId;

    public GroupChatViewModel() {
    }

    public long getSenderId() {
        return SenderId;
    }

    public void setSenderId(long senderId) {
        SenderId = senderId;
    }

    public long getRecieverId() {
        return RecieverId;
    }

    public void setRecieverId(long recieverId) {
        RecieverId = recieverId;
    }

    private long SenderId;
    private long RecieverId;


    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    private String CreatedDate;

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    private String SenderName;


    public GroupChatViewModel(int size, String s, String toString, boolean b) {
    }

    public GroupChatViewModel(long id, String date, String content, boolean fromMe) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.fromMe = fromMe;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public FriendsViewModel getFriend() {
        return friend;
    }

    public void setFriend(FriendsViewModel friend) {
        this.friend = friend;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }
}
