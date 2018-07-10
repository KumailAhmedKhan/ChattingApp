package com.app.sample.chatting.SignalR.RegisteredEvent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.sample.chatting.Activity.ActivityGroupDetails;
import com.app.sample.chatting.AsyncTask.GroupChatMessageStatusAsyncTask;
import com.app.sample.chatting.Notifications.NotificationGenerate;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Classes.HubMessage;
import com.app.sample.chatting.SignalR.Interface.HubEventListener;
import com.app.sample.chatting.ViewModelClasses.GroupChatViewModel;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendMessageToGroupEvent extends ActivityGroupDetails implements HubEventListener
{
    TemporaryStorageSharedPreferences temp1=new TemporaryStorageSharedPreferences();
    NotificationGenerate notificationGenerate;
    public SendMessageToGroupEvent(Context ctx){
        this.context=ctx.getApplicationContext();
    }
    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onEventMessage(HubMessage message) {

        notificationGenerate=new NotificationGenerate();
       // Log.d("myApp onEventMessage =>", message.getTarget()+"onEventMessage Entering");
        JsonElement[] SenderInfo= message.getArguments();
        //Log.d("MessageReceiveongroup"," "+SenderInfo[0]+" "+SenderInfo[1]+" "+" ");
        String MessageInfo = SenderInfo[1].toString();
        String SenderInformation1=SenderInfo[0].toString();
       // Log.d("ReceiverUserId", MessageInfo+"");
        JSONObject jsonData= null;
        JSONObject jsonObject=null;
        try {
            jsonData = new JSONObject(MessageInfo);
            jsonObject=new JSONObject(SenderInformation1);
            //Log.d("ReceiverUserId123", jsonData+"");
            //Log.d("SenderInformation1", jsonObject+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject SenderInformation = null;


        try {
            SenderInformation = jsonData.getJSONObject("Model");
            //Log.d("ReceiverUserId456", SenderInformation+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String GroupID =null;
        String MessageInGroup=null;
        String SenderName=null;
        String SenderID=null;
        String MessageID=null;

        try {
            SenderID=jsonObject.getString("Id");
            SenderName=jsonObject.getString("Name");
            MessageInGroup=SenderInformation.getString("Text");
            GroupID = SenderInformation.getString("ReceiverGroupId");
            MessageID= String.valueOf(SenderInformation.get("Id"));
           // Log.d("ReceiverUserId", GroupID+"");
           // Log.d("MessageforReceiver", MessageInGroup+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String gmodal="G_"+GroupID;
        String Activegroupmodal=temp1.getPreferences(context,"ActiveModalGroup");
        String userId=temp1.getPreferences(context,"Userdata");
        if(! Activegroupmodal.equals(gmodal))
        {
                notificationGenerate.addNotificationforGroups(context,MessageInGroup,SenderName+"{"+groupName+"}",GroupID);
/*
                final String finalSenderName = SenderName;
            final String finalMessageInGroup = MessageInGroup;
            GroupChatViewModel groupChatViewModel=new GroupChatViewModel();
                    groupChatViewModel.setSenderName(finalSenderName);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String Date=dateFormat.format(date);
                    groupChatViewModel.setCreatedDate(Date);
                    groupChatViewModel.setContent(finalMessageInGroup);
                            //ID=Long.parseLong(finalReceiverUserID);
            Intent intent = new Intent("GroupMessage");

            intent.putExtra("MessageDate",Date);
            intent.putExtra("MessageText",finalMessageInGroup);
            intent.putExtra("Message",GroupID);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
*/
            //groupChatMessageStatusAsyncTask=(GroupChatMessageStatusAsyncTask) new GroupChatMessageStatusAsyncTask(context,MessageID,userId,false).execute(groupChatViewModel);

        }else if(Activegroupmodal.equals(gmodal)  )
        {
            final String finalSenderName = SenderName;
            final String finalMessageInGroup = MessageInGroup;
            GroupChatViewModel groupChatViewModel=new GroupChatViewModel();
            groupChatViewModel.setSenderName(finalSenderName);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String Date=dateFormat.format(date);
            groupChatViewModel.setCreatedDate(Date);
            groupChatViewModel.setContent(finalMessageInGroup);
            //ID=Long.parseLong(finalReceiverUserID);
            Intent intent = new Intent("FriendFromGroupMessage");

            intent.putExtra("MessageDate",Date);
            intent.putExtra("MessageText",finalMessageInGroup);
            intent.putExtra("GroupID",GroupID);
            intent.putExtra("SenderID",SenderID);
            intent.putExtra("SenderName",finalSenderName);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            //final String finalSenderName = SenderName;
           // final String finalMessageInGroup = MessageInGroup;
            /*
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GroupChatViewModel groupChatViewModel=new GroupChatViewModel();
                    groupChatViewModel.setSenderName(finalSenderName);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    String Date=dateFormat.format(date);
                    groupChatViewModel.setCreatedDate(Date);
                    groupChatViewModel.setContent(finalMessageInGroup);
                    madapter.add(groupChatViewModel);
                    madapter.notifyDataSetChanged();
                }
            });*/


        }

    }

}
