package com.app.sample.chatting.SignalR.RegisteredEvent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.sample.chatting.Notifications.NotificationGenerate;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Classes.HubMessage;
import com.app.sample.chatting.SignalR.Interface.HubEventListener;
import com.app.sample.chatting.ViewModelClasses.UserNotificationViewModel;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmFriendRequestEvent implements HubEventListener
{
    private Context context;
    NotificationGenerate notificationGenerate=new NotificationGenerate();
    String Type;
    UserNotificationViewModel notification=new UserNotificationViewModel();
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    public ConfirmFriendRequestEvent(Context ctx){
        this.context=ctx.getApplicationContext();
    }
    @Override
    public void onEventMessage(HubMessage message) {
        Log.d("myApp onEventMessage =>", message.getTarget()+"onEventMessage Entering");
        JsonElement[] NotificationSenderInfo= message.getArguments();
        Log.d("myApp onEventMessage =>",NotificationSenderInfo[1]+"Notification message");
        String NotificationInfo = NotificationSenderInfo[1].toString();
        JSONObject jsonData= null;
            try
            {
                jsonData = new JSONObject(NotificationInfo);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        JSONObject SenderInformation = null;

        try
        {
            SenderInformation = jsonData.getJSONObject("Model");
            Log.d("Notification Data", SenderInformation+"");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        try
        {
            notification.setText(SenderInformation.getString("Text"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        try
        {
            notification.setGrouptonotify(SenderInformation.getString("GroupToNotify"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        try
        {
            notification.setSenderName(SenderInformation.getString("SenderName"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        try
        {
            notification.setNotificationCreatedDate(SenderInformation.getString("CreatedDate"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        try {
            notification.setNotificationType(SenderInformation.getInt("Type"));
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        try
        {
            notification.setSenderId(SenderInformation.getInt("SenderId"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        try
        {
            notification.setNotificationId(SenderInformation.getInt("Id"));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
            if(notification.getNotificationType()==1)
            {
                notificationGenerate.NotificationForFriendRequest(context,notification.getText(),notification.getSenderName(),notification.getSenderId());
                notificationGenerate.NotificationForFriendRequesrConfirmed(context,notification.getText(),notification.getSenderName(),notification.getSenderId());
                Intent intent = new Intent("FriendNow");
                String Id= String.valueOf(notification.getSenderId());
                String Type= String.valueOf(notification.getNotificationType());
                String Name=String.valueOf(notification.getSenderName());
                intent.putExtra("NotificationType",Type);
                intent.putExtra("SenderId",Id);
                intent.putExtra("SenderName",Name);
                Intent intent1 = new Intent("SendFriendRequestNotification");
                String Id1= temp.getPreferences(context, "Userdata");
                intent1.putExtra("UserId",Id1);
                String Type1= String.valueOf(notification.getNotificationType());
                intent1.putExtra("NotificationType",Type1);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
            else if(notification.getNotificationType()==2)
            {
                notificationGenerate.NotificationForFriendRequesrConfirmed(context,notification.getText(),notification.getSenderName(),notification.getSenderId());
                Intent intent = new Intent("FriendNow");
                String Id= String.valueOf(notification.getSenderId());
                String Type= String.valueOf(notification.getNotificationType());
                String Name=String.valueOf(notification.getSenderName());
                intent.putExtra("NotificationType",Type);
                intent.putExtra("SenderId",Id);
                intent.putExtra("SenderName",Name);
                Intent intent1 = new Intent("SendFriendRequestNotification");
                String Id1= temp.getPreferences(context, "Userdata");
                intent1.putExtra("UserId",Id1);
                String Type1= String.valueOf(notification.getNotificationType());
                intent1.putExtra("NotificationType",Type1);
                Intent intent2=new Intent("FriendsAddInAdapter");
                intent2.putExtra("SENDERNAME",notification.getSenderName());
                intent2.putExtra("SENDERID",String.valueOf(notification.getSenderId()));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
            else if(notification.getNotificationType()==3)
            {
                notificationGenerate.NotificationForFriendNow(context,notification.getText(),notification.getSenderName());
                Intent intent = new Intent("FriendNow");
                String Id= String.valueOf(notification.getSenderId());
                String Type= String.valueOf(notification.getNotificationType());
                String Name=String.valueOf(notification.getSenderName());
                intent.putExtra("NotificationType",Type);
                intent.putExtra("SenderId",Id);
                intent.putExtra("SenderName",Name);
                Intent intent1 = new Intent("SendFriendRequestNotification");
                String Id1= temp.getPreferences(context, "Userdata");
                intent1.putExtra("UserId",Id1);
                String Type1= String.valueOf(notification.getNotificationType());
                intent1.putExtra("NotificationType",Type1);
                Intent intent2=new Intent("FriendsAddInAdapter");
                intent2.putExtra("SENDERNAME",notification.getSenderName());
                intent2.putExtra("SENDERID",String.valueOf(notification.getSenderId()));
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent2);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


            }
            else if(notification.getNotificationType()==4)
            {
                Intent intent1 = new Intent("SendFriendRequestNotification");
                String Id1= temp.getPreferences(context, "Userdata");
                intent1.putExtra("UserId",Id1);
                String Type1= String.valueOf(notification.getNotificationType());
                intent1.putExtra("NotificationType",Type1);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);

                notificationGenerate.NotificationForAddedToGroup(context,notification.getText(),notification.getSenderName());
            }
            else
                {
                    Log.d("myApp onEventMessage =>","Notification Pata Nahi"+"Notification message");
            }


    }
}
