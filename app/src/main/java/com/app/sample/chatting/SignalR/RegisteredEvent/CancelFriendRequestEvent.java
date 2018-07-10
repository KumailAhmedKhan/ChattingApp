package com.app.sample.chatting.SignalR.RegisteredEvent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.sample.chatting.SignalR.Classes.HubMessage;
import com.app.sample.chatting.SignalR.Interface.HubEventListener;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

public class CancelFriendRequestEvent implements HubEventListener {
    String TYpe;
    String Success;
    private Context context;
    public CancelFriendRequestEvent(Context ctx){
        this.context=ctx.getApplicationContext();
    }
    @Override
    public void onEventMessage(HubMessage message) {
        Log.d("myApp onEventMessage =>", message.getTarget()+"onEventMessage Entering");
        JsonElement[] temp= message.getArguments();
        String NotificationInfo = temp[0].toString();
        JSONObject jsonData= null;
        try
        {
            jsonData = new JSONObject(NotificationInfo);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        try
        {
            Success=jsonData.getString("Success");
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
            TYpe=SenderInformation.getString("Type");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        Intent intent1 = new Intent("CancelFriendRequestNotification");
        intent1.putExtra("NotificationSuccess",Success);
        intent1.putExtra("NotificationType",TYpe);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent1);
    }
}
