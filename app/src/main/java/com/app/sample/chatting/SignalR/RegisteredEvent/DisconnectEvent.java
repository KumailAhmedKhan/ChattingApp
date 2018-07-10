package com.app.sample.chatting.SignalR.RegisteredEvent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Classes.HubMessage;
import com.app.sample.chatting.SignalR.Interface.HubEventListener;
import com.google.gson.JsonElement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DisconnectEvent implements HubEventListener {
    TemporaryStorageSharedPreferences temporary=new TemporaryStorageSharedPreferences();
    public Context context;


    public DisconnectEvent(Context ctx){
        this.context=ctx.getApplicationContext();
    }
    @Override
    public void onEventMessage(HubMessage message) {
        Log.d("myApp onEventMessage =>", message.getTarget()+"onDisconnect Entering kumail");
        JsonElement[] temp= message.getArguments();
        String string = temp[0].toString();
        JSONObject o = null;
        try {
            o = new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String a =null;
        try {
            a = o.getString("Id");
            Log.d("Id", a+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<String> Onlineusers=temporary.getPreferencesforJsonArray(context,"OnlineFriendIds");
        //Log.d("Hello3",Onlineusers+" " +""+"");
        //String onlineUsersString=Onlineusers.toString().substring(1,Onlineusers.toString().length()-1);
        for(int i=0;i<Onlineusers.size();i++){
            if(Onlineusers.contains(a)==true)
            {
                Onlineusers.remove(" ");
                Onlineusers.remove(" ");
                Onlineusers.remove(" ");
                Onlineusers.remove(a);
            }else{
                Onlineusers.remove(" ");
                Onlineusers.remove(" ");
                Onlineusers.remove(" ");

            }
        }
        String onlineUsersString=Onlineusers.toString();
        onlineUsersString=onlineUsersString.replaceAll(" ","");
        temporary.savePreferences(context,"OnlineFriendIds",onlineUsersString);
        Intent intent = new Intent("OfflineFriend");
        intent.putExtra("OfflineFriendsId",onlineUsersString);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



    }
}
