package com.app.sample.chatting.SignalR.RegisteredEvent;

import android.content.Context;
import android.util.Log;

import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Classes.HubMessage;
import com.app.sample.chatting.SignalR.Interface.HubEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        temporaryStorageSharedPreferences.savePreferences(context,"ConnectionID",connectionId);




    }
}
