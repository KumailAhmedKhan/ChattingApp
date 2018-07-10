package com.app.sample.chatting.ViewModelClasses.Model;

import com.app.sample.chatting.ViewModelClasses.FriendsViewModel;

import java.io.Serializable;

public class GroupDetails implements Serializable {
	private long id;
	private String date;
	private FriendsViewModel friend;
	private String content;
	private boolean fromMe;

	public GroupDetails(int i, String s, FriendsViewModel friendsViewModel, String s1, boolean b) {
	}

	public String getSenderName() {
		return SenderName;
	}

	public void setSenderName(String senderName) {
		SenderName = senderName;
	}

	private String SenderName;

	public GroupDetails(long id, String date, FriendsViewModel friend, String content, boolean fromMe,String s) {

		this.id = id;
		this.date = date;
		this.friend = friend;
		this.content = content;
		this.fromMe = fromMe;
		this.SenderName=s;
	}

	public long getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public FriendsViewModel getFriend() {
		return friend;
	}

	public String getContent() {
		return content;
	}

	public boolean isFromMe() {
		return fromMe;
	}
}