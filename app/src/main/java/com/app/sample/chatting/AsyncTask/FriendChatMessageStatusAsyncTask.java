package com.app.sample.chatting.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;

import com.app.sample.chatting.Activity.ActivityChatDetails;
import com.app.sample.chatting.Activity.ActivityGroupDetails;
import com.app.sample.chatting.Activity.ActivityRegister;
import com.app.sample.chatting.Activity.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.RegisteredEvent.SendMessageToFriendEvent;
import com.app.sample.chatting.URL.httpUrl;
import com.app.sample.chatting.ViewModelClasses.FriendChatViewModel;
import com.app.sample.chatting.ViewModelClasses.FriendsRecentChatViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FriendChatMessageStatusAsyncTask extends AsyncTask<FriendChatViewModel,String,String> {

    //ActivityChatDetails activityChatDetails;
    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    int GroupID;
    private Context context;
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    httpUrl httpurl=new httpUrl();
    public static final String PREFS_NAME = "MyApp_Settings";
    String UserId;
    String receiverId;
    String msgId;
    public static List<FriendChatViewModel> frnd=new ArrayList<>();
    public FriendChatViewModel friendChatViewModel = new FriendChatViewModel();
    static Adapter adapter;
    CheckConnection checkConnection=new CheckConnection();
    public FriendChatMessageStatusAsyncTask()
    {

    }
    public FriendChatMessageStatusAsyncTask(Context context,String MessageID)
    {
        UserId=temp.getPreferences(context,"Userdata");
        receiverId=temp.getPreferences(context,"FriendId");
        this.msgId=MessageID;
    }
    public  void setData(FriendChatViewModel frnd)
    {

        this.friendChatViewModel=frnd;
    }
    public  FriendChatViewModel getData()
    {
        return friendChatViewModel;
    }

    public String getPostDataString(JSONObject params) throws Exception
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while(itr.hasNext())
        {
            String key= itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
    @Override
    protected String doInBackground(FriendChatViewModel... friendChatViewModels)
    {
        final int checkconnectionflag=checkConnection.connnectioncheck(context);
        if(checkconnectionflag==1)
        {
            String Url = httpurl.getURl();
            StringBuilder builder = new StringBuilder();
            try
            {
                URL myUrl = new URL(Url + "/ChangeUserMessageStatus");
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("POST");
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("msgId", msgId);
                //postDataParams.put("recevierId", receiverId);
                Log.e("params", postDataParams.toString());
                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String postString = getPostDataString(postDataParams);
                writer.write(postString);
                writer.flush();
                writer.close();
                os.close();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    in.close();
                /*
                JSONObject jsonData=new JSONObject(String.valueOf(sb));
                JSONArray jsonArr = jsonData.getJSONArray("Model");

                List<FriendsRecentChatViewModel> dataList = new ArrayList<>();

                for (int i = 0; i < jsonArr.length(); i++)
                {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    FriendsRecentChatViewModel data = new FriendsRecentChatViewModel();
                    data.setDate(jsonObj.getString("CreatedDate"));
                    //data.setSendId(jsonObj.getLong("SenderId"));
                    //data.setRecId(jsonObj.getLong("ReceiverUserId"));
                    data.setSenderName(jsonObj.getString("SenderName"));
                    //data.setReceiverName(jsonObj.getString("ReceiverUserName"));
                    //data.setContent(jsonObj.getString("Text"));


                    dataList.add(data);
                }
                */
                    return line;


                }


            } catch (Exception e)
            {
                e.printStackTrace();

            }
        }else{
            Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(context,ActivitySplash.class));
        }
        return null;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s)
    {
        JSONObject jsonObject = null;
        try
        {
            jsonObject = new JSONObject(s);
            jsonObject = jsonObject.getJSONObject("Model");
            friendChatViewModel.setMessageStatus(jsonObject.getLong("Status"));
            friendChatViewModel.setRecId(jsonObject.getLong("ReceiverUserId"));
            friendChatViewModel.setSendId(jsonObject.getLong("SenderId"));
            friendChatViewModel.setContent(jsonObject.getString("Text"));
            friendChatViewModel.setDate(jsonObject.getString("CreatedDate"));
            friendChatViewModel.setMessageId(jsonObject.getLong("Id"));
            frnd.add(friendChatViewModel);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        super.onPostExecute(s);
    }


}
