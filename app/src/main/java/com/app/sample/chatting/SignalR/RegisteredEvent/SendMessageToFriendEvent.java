package com.app.sample.chatting.SignalR.RegisteredEvent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.sample.chatting.ActivityClasses.ActivityChatDetails;
import com.app.sample.chatting.NotificationsClass.NotificationGenerate;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Classes.HubMessage;
import com.app.sample.chatting.SignalR.Interface.HubEventListener;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendMessageToFriendEvent extends ActivityChatDetails implements HubEventListener {

    TemporaryStorageSharedPreferences temp1=new TemporaryStorageSharedPreferences();
    NotificationGenerate notificationGenerate;
    public static long ID;
     int counter=0;
    public SendMessageToFriendEvent(Context ctx){
        this.context=ctx.getApplicationContext();
    }
    // @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onEventMessage(HubMessage message)
    {


        notificationGenerate=new NotificationGenerate();
        Log.d("myApp onEventMessage =>", message.getTarget()+"onEventMessage Entering");
        JsonElement[] SenderInfo= message.getArguments();
        Log.d("Message Sent",SenderInfo[0]+"    "+SenderInfo[1]+"  "+"   Data ");
        String MessageInfo = SenderInfo[1].toString();
        String SenderInformation1=SenderInfo[0].toString();
        Log.d("ReceiverUserId", MessageInfo+"");
        JSONObject jsonData= null;
        JSONObject jsonObject=null;
        try
        {
            jsonData = new JSONObject(MessageInfo);
            jsonObject=new JSONObject(SenderInformation1);
            Log.d("ReceiverUserId123", jsonData+"");
            Log.d("SenderInformation1", jsonObject+"");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        JSONObject SenderInformation = null;

        try
        {
            SenderInformation = jsonData.getJSONObject("Model");
            Log.d("ReceiverUserId456", SenderInformation+"");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        String ReceiverUserID =null;
        String MessageforReceiver=null;
        String SenderName=null;
        String SenderID=null;
        String MessageId=null;

        try
        {
            SenderID=jsonObject.getString("Id");
            SenderName=jsonObject.getString("Name");
            MessageforReceiver=SenderInformation.getString("Text");
            MessageId=SenderInformation.getString("Id");
            ReceiverUserID = SenderInformation.getString("ReceiverUserId");
            SenderID=SenderInformation.getString("SenderId");
            Log.d("ReceiverUserId", ReceiverUserID+"");
            Log.d("MessageforReceiver", MessageforReceiver+"");
            Log.d("SenderUserId", FriendID+"");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        String ActiveModal=temp1.getPreferences(context,"ActiveModal");
        String ActiveModel1="I_"+SenderID;
        String UserID=temp1.getPreferences(context,"Userdata");



        if(!(ActiveModal.equals(ActiveModel1)) && !UserID.equals(SenderID))
        {
            notificationGenerate.addNotificationforFriends(context,MessageforReceiver,SenderName,SenderID,UserID);
            /*
            final String finalMessageforReceiver = MessageforReceiver;
            final String finalReceiverUserID = ReceiverUserID;
             FriendsRecentChatViewModel  friendChatViewModel = new FriendsRecentChatViewModel();
                            friendChatViewModel.setText(finalMessageforReceiver);
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date date = new Date();
                            String Date = dateFormat.format(date);
                            friendChatViewModel.setDate(Date);
                            friendChatViewModel.setSenderId(Long.parseLong(SenderID));
                            ID=Long.parseLong(finalReceiverUserID);
            Intent intent = new Intent("FriendMessage");
        // You can also include some extra data.
        //intent.putExtra("FriendMessage", friendChatViewModel);
            intent.putExtra("MessageDate",Date);
            intent.putExtra("MessageText",finalMessageforReceiver);
            intent.putExtra("MessageSender",SenderID);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
*/
           // messageStatus = (FriendChatMessageStatusAsyncTask) new FriendChatMessageStatusAsyncTask(context, MessageId).execute(friendChatViewModel);

        }
        else
        {
            counter++;
            Log.d("myCounter", counter+"");
            final String finalMessageforReceiver = MessageforReceiver;
            final String finalReceiverUserID = ReceiverUserID;
            Intent intent = new Intent("MessageFromFriend");
            // You can also include some extra data.
            //intent.putExtra("FriendMessage", friendChatViewModel);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String Date = dateFormat.format(date);
            intent.putExtra("MessageReceiverID",finalReceiverUserID);
            intent.putExtra("MessageDate",Date);
            intent.putExtra("MessageText",finalMessageforReceiver);
            intent.putExtra("MessageSenderID",SenderID);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
           // final String finalMessageforReceiver = MessageforReceiver;
            //final String finalReceiverUserID = ReceiverUserID;
            /*runOnUiThread(new Runnable()
            {
                        @Override
                        public void run()
                        {
                            FriendChatViewModel friendChatViewModel = new FriendChatViewModel();
                            friendChatViewModel.setContent(finalMessageforReceiver);
                            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date date = new Date();
                            String Date = dateFormat.format(date);
                            friendChatViewModel.setDate(Date);
                            friendChatViewModel.setRecId(Long.parseLong(finalReceiverUserID));
                            ID=Long.parseLong(finalReceiverUserID);
                            madapter.add(friendChatViewModel);
                            madapter.notifyDataSetChanged();

                        }
            });*/
        }



    }



}



