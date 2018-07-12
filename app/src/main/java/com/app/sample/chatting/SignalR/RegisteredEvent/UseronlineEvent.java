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


/**
 * Created by Ahmad on 4/27/2018.
 */

public class UseronlineEvent implements HubEventListener {

    HubMessage hubMessage=new HubMessage();
    TemporaryStorageSharedPreferences temporary=new TemporaryStorageSharedPreferences();
    private Context context;
    JsonElement[] temp=null;
    int friendID[];

    public UseronlineEvent(Context ctx){
        this.context=ctx.getApplicationContext();
    }
    public void onEventMessage(HubMessage message) {

        Log.d("myApp onEventMessage =>", message.getTarget()+"onEventMessage Entering kumail");
        JsonElement[] temp= message.getArguments();
        Log.d("Argument",temp[0]+"FriendID");
        String temp1=temp[0].toString();
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
        /////////////////////////////////////Yasr is cheez ko improve karna ha/////////////////////////////////

       // String OnlineID= temporary.getPreferences(context.getApplicationContext(),"OnlineFriendIds");
        /*
        OnlineID=OnlineID.replaceAll("\\[","").replaceAll("\\]","");
        String[] OnlineFriends = OnlineID.split(",");
        List<String> list =  new ArrayList<String>();
        Collections.addAll(list, OnlineFriends);
        list.remove("");
        list.add(a);
        //list.removeAll(Arrays.asList(null));
       // Set<String> s = new LinkedHashSet<String>(list);



       // Log.d("Hello11",s+" " +""+"");
*/
       // OnlineFriends = list.toArray(new String[list.size()]);
       // String abc= Arrays.toString(OnlineFriends);

        //Log.d("Hello3",abc+" " +""+"");
       // temporary.savePreferences(context.getApplicationContext(),"OnlineFriendIds",abc);
        List<String> Onlineusers=temporary.getPreferencesforJsonArray(context.getApplicationContext(),"OnlineFriendIds");
        //Log.d("Hello3",Onlineusers+" " +""+"");
        //String onlineUsersString=Onlineusers.toString().substring(1,Onlineusers.toString().length()-1);
        for(int i=0;i<Onlineusers.size();i++){
            if(Onlineusers.contains(a)==true)
            {
                Onlineusers.remove(" ");
                Onlineusers.remove(" ");
                Onlineusers.remove(" ");

            }else{
                Onlineusers.remove(" ");
                Onlineusers.remove(" ");
                Onlineusers.remove(" ");
                Onlineusers.add(a);
            }
        }
        String onlineUsersString=Onlineusers.toString();
        onlineUsersString=onlineUsersString.replaceAll(" ","");
        Intent intent = new Intent("OfflineFriend");
        intent.putExtra("OfflineFriendsId",onlineUsersString);
        LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intent);
        try {
            temporary.savePreferences(context.getApplicationContext(),"OnlineFriendIds",onlineUsersString);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
