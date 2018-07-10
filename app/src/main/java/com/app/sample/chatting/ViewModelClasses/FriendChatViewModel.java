package com.app.sample.chatting.ViewModelClasses;

import java.io.Serializable;

public class FriendChatViewModel implements Serializable {


    private long id;
    private String date;
    private FriendsViewModel friend;
    private String content;
    private boolean fromMe;
    private String SenderName;

    public long getMessageId() {
        return MessageId;
    }

    public void setMessageId(long messageId) {
        MessageId = messageId;
    }

    private long MessageId;

    public long getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(long messageStatus) {
        this.messageStatus = messageStatus;
    }

    private long messageStatus;

    public String getRecieverName() {
        return RecieverName;
    }

    public void setRecieverName(String recieverName) {
        RecieverName = recieverName;
    }

    public long getRecId() {
        return recId;
    }

    public void setRecId(long recId) {
        this.recId = recId;
    }

    public long getSendId() {
        return sendId;
    }

    public void setSendId(long sendId) {
        this.sendId = sendId;
    }

    private String RecieverName;
    private long recId;
    private long sendId;

    public FriendChatViewModel() {
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setReceiverName(String receiverName) {
        ReceiverName = receiverName;
    }

    private String ReceiverName;

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }



    public FriendChatViewModel(int size, String date,  String content, boolean fromMe) {
        this.id = id;
        this.date = date;
        //this.friend = friend;
        this.content = content;
        this.fromMe = fromMe;
    }

    public FriendChatViewModel(long id, String date, FriendsViewModel friend, String content, boolean fromMe) {
        this.id = id;
        this.date = date;
        this.friend = friend;
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
