package com.app.sample.chatting.SignalR.RegisteredEvent;

import android.content.Context;
import android.util.Log;

import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Classes.HubMessage;
import com.app.sample.chatting.SignalR.Interface.HubEventListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class ConnectEvent implements HubEventListener {
    private Context context;
    private TemporaryStorageSharedPreferences temporaryStorageSharedPreferences;

    public ConnectEvent(Context ctx){
        this.context=ctx.getApplicationContext();
        temporaryStorageSharedPreferences=new TemporaryStorageSharedPreferences();
    }
    @Override
    public void onEventMessage(HubMessage message) {
        Log.d("myApp onEventMessage =>", message.getTarget()+"onEventMessage Connect");
        JsonElement[] result= message.getArguments();
       // Log.d("Connect",temp[0]+""+""+temp[1]+""+temp[2]+"");
        String connectionId=result[0].getAsString();
        JsonElement userModel=result[1].getAsJsonObject();
        JsonArray onlineUsersJson = result[2].getAsJsonArray();
        temporaryStorageSharedPreferences.setPreferencesforJsonArray(context,"OnlineFriendIds",onlineUsersJson);

        //temporaryStorageSharedPreferences.setPreferencesforJsonElement(context,"UserModel",userModel);

        try {
            temporaryStorageSharedPreferences.savePreferences(context,"ConnectionID",connectionId);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
