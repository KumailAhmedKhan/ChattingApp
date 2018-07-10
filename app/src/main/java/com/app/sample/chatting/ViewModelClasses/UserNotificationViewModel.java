package com.app.sample.chatting.ViewModelClasses;

public class UserNotificationViewModel
{

    private long NotificationId;
    private long SenderId;
    private String Text;
    private String SenderName;
    private String NotificationCreatedDate;
    private int NotificationType;
    private String Grouptonotify;


    public  int getNotificationCount() {
        return NotificationCount;
    }

    public  void setNotificationCount(int notificationCount) {
        NotificationCount = notificationCount;
    }

    public  int NotificationCount;

    public UserNotificationViewModel(long notificationId, long senderId, String text, String senderName, String notificationCreatedDate, int notificationType, String grouptonotify) {
        NotificationId = notificationId;
        SenderId = senderId;
        Text = text;
        SenderName = senderName;
        NotificationCreatedDate = notificationCreatedDate;
        NotificationType = notificationType;
        Grouptonotify = grouptonotify;
    }

    public UserNotificationViewModel() {
    }

    public long getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(long notificationId) {
        NotificationId = notificationId;
    }

    public long getSenderId() {
        return SenderId;
    }

    public void setSenderId(long senderId) {
        SenderId = senderId;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getNotificationCreatedDate() {
        return NotificationCreatedDate;
    }

    public void setNotificationCreatedDate(String notificationCreatedDate) {
        NotificationCreatedDate = notificationCreatedDate;
    }

    public int getNotificationType() {
        return NotificationType;
    }

    public void setNotificationType(int notificationType) {
        NotificationType = notificationType;
    }

    public String getGrouptonotify() {
        return Grouptonotify;
    }

    public void setGrouptonotify(String grouptonotify) {
        Grouptonotify = grouptonotify;
    }
}
