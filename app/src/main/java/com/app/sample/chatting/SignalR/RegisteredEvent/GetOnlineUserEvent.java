package com.app.sample.chatting.SignalR.RegisteredEvent;

import android.content.Context;
import android.util.Log;

import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Classes.HubMessage;
import com.app.sample.chatting.SignalR.Interface.HubEventListener;
import com.google.gson.JsonElement;

public class GetOnlineUserEvent implements HubEventListener {

    TemporaryStorageSharedPreferences temporary=new TemporaryStorageSharedPreferences();
    private Context context;

    public GetOnlineUserEvent(Context ctx){
        this.context=ctx.getApplicationContext();
    }
    @Override
    public void onEventMessage(HubMessage message) {
        Log.d("myApp onEventMessage =>", message.getTarget()+"onLine User");
        JsonElement[] temp= message.getArguments();
        String string = temp[0].toString();
        String temp1=temp[0].toString();
        // String[] Array = temp1.split(",");
        try {
            temporary.savePreferences(context.getApplicationContext(),"OnlineFriendIds",temp1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
