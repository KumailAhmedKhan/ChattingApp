package com.app.sample.chatting.model;

import com.app.sample.chatting.ViewModelClasses.FriendsViewModel;

import java.io.Serializable;

public class Chat implements Serializable {
	private long id;
	private String date;
	private boolean read = false;
	private FriendsViewModel friend;
	private String snippet;
	public int  newMsgID;
	public int SenderId;
	public String SenderName;
	public String CreatedDate;
	public int ReceiverUserId;
	public String ReceiverUserName;
	public boolean Status;
	public String Text;
	public String Type;

	public Chat(long id, String date, boolean read, FriendsViewModel friend, String snippet) {
		this.id = id;
		this.date = date;
		this.read = read;
		this.friend = (FriendsViewModel) friend;
		this.snippet = snippet;
	}

	public Chat(long id, String date, boolean read, FriendsViewModel friend, String snippet, int newMsgID, int senderId, String senderName, String createdDate, int receiverUserId, String receiverUserName, boolean status, String text, String type) {
		this.id = id;
		this.date = date;
		this.read = read;
		this.friend = friend;
		this.snippet = snippet;
		this.newMsgID = newMsgID;
		SenderId = senderId;
		SenderName = senderName;
		CreatedDate = createdDate;
		ReceiverUserId = receiverUserId;
		ReceiverUserName = receiverUserName;
		Status = status;
		Text = text;
		Type = type;
	}

	public long getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public boolean isRead() {
		return read;
	}

	public FriendsViewModel getFriend() {
		return friend;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public void setFriend(FriendsViewModel friend) {
		this.friend = friend;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public int getNewMsgID() {
		return newMsgID;
	}

	public void setNewMsgID(int newMsgID) {
		this.newMsgID = newMsgID;
	}

	public int getSenderId() {
		return SenderId;
	}

	public void setSenderId(int senderId) {
		SenderId = senderId;
	}

	public String getSenderName() {
		return SenderName;
	}

	public void setSenderName(String senderName) {
		SenderName = senderName;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public int getReceiverUserId() {
		return ReceiverUserId;
	}

	public void setReceiverUserId(int receiverUserId) {
		ReceiverUserId = receiverUserId;
	}

	public String getReceiverUserName() {
		return ReceiverUserName;
	}

	public void setReceiverUserName(String receiverUserName) {
		ReceiverUserName = receiverUserName;
	}

	public boolean isStatus() {
		return Status;
	}

	public void setStatus(boolean status) {
		Status = status;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}
}