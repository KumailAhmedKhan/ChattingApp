package com.app.sample.chatting.AsyncTaskLoaderClasses;
import android.annotation.SuppressLint;
import android.content.Intent;


import android.content.Context;

import com.app.sample.chatting.ActivityClasses.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URLClass.HTTPURL;
import com.app.sample.chatting.ViewModelClasses.FriendsRecentChatViewModel;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FriendRecentChatLoader extends android.support.v4.content.AsyncTaskLoader<List<FriendsRecentChatViewModel>> {
    public static final String REQUEST_METHOD = "GET";

    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    int GroupID;
    private Context context;
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    HTTPURL httpurl=new HTTPURL();
    public static final String PREFS_NAME = "MyApp_Settings";
    String UserId;
    String receiverId;
    CheckConnection checkConnection=new CheckConnection();

    public FriendRecentChatLoader(Context context) {


        super(context);
        this.context=context.getApplicationContext();
        UserId=temp.getPreferences(context,"Userdata");
        receiverId=temp.getPreferences(context,"FriendId");
    }

    @Override
    public List<FriendsRecentChatViewModel> loadInBackground()
    {
        final int checkconnectionflag=checkConnection.connnectioncheck(context);
        if(checkconnectionflag==1)
        {
            StringBuilder builder = new StringBuilder();
            try
            {
                String Url = httpurl.getURl();

                URL myUrl = new URL(Url + "/GetUserRecentChat?UserId=" + UserId);
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);
                //Connect to our url
                connection.connect();
                //Create a new InputStreamReader
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();
                //Check if the line we are reading is not null
                String inputLine = "";
                    while ((inputLine = reader.readLine()) != null)
                    {
                        stringBuilder.append(inputLine);
                    }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                String Data = stringBuilder.toString();
                JSONObject jsonData = new JSONObject(Data);
                JSONArray jsonArr = jsonData.getJSONArray("Model");

                List<FriendsRecentChatViewModel> dataList = new ArrayList<>();

                    for (int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        FriendsRecentChatViewModel data = new FriendsRecentChatViewModel();

                        data.setSenderId(jsonObj.getInt("SenderId"));
                        data.setRecieverId(jsonObj.getLong("ReceiverGroupId"));
                        //data.(jsonObj.getString("CreatedDate"));
                        data.setDate(jsonObj.getString("CreatedDate"));
                        data.setText(jsonObj.getString("Text"));
                        data.setSenderName(jsonObj.getString("SenderName"));

                        dataList.add(data);
                    }
                        return dataList;

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else {
            Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(context,ActivitySplash.class));
        }
        return null;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void waitForLoader() {
        super.waitForLoader();

    }


}

