package com.app.sample.chatting.AsyncTaskLoaderClasses;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.app.sample.chatting.ActivityClasses.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URLClass.HTTPURL;
import com.app.sample.chatting.ViewModelClasses.GroupChatViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GroupChatLoader extends AsyncTaskLoader<List<GroupChatViewModel>>
{

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    int GroupID;
    private Context context;
    public static final String PREFS_NAME = "MyApp_Settings";
    HTTPURL httpurl=new HTTPURL();
    CheckConnection checkConnection=new CheckConnection();
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    int groupID;

    public GroupChatLoader(Context context, int str)
    {
        super(context);
        this. GroupID=str;
        this.context=context.getApplicationContext();
    }
    public GroupChatLoader(Context ctx,Bundle bundle){
        super(ctx);
        this.context=ctx.getApplicationContext();
        groupID= Integer.parseInt(bundle.getString("KEY_GROUPID"));

    }

    @Override
    public List<GroupChatViewModel> loadInBackground()
    {
        final int checkconnectionflag=checkConnection.connnectioncheck(context);

        if(checkconnectionflag==1)
        {
            String Url = httpurl.getURl();
            try
            {

                URL myUrl = new URL(Url + "/GetUserGroupChat?groupId=" + groupID);
                //Create a connection
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                //Set methods and timeouts
                connection.setRequestMethod("POST");
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
                List<GroupChatViewModel> dataList = new ArrayList<>();
                    for (int i = 0; i < jsonArr.length(); i++)
                    {

                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        GroupChatViewModel data = new GroupChatViewModel();
                        //data.msgId = jsonObj.getInt("Id");
                        //data.SenderId = jsonObj.getInt("SenderId");
                        //data.ReceiverGroupId = jsonObj.getInt(" ReceiverGroupId");
                        // data.=jsonObj.getString("SenderName");
                        // data.msgType=jsonObj.getString(" Type");
                        data.setSenderId(jsonObj.getInt("SenderId"));
                        //data.setRecieverId(jsonObj.getLong(" ReceiverGroupId"));
                        data.setCreatedDate(jsonObj.getString("CreatedDate"));
                        data.setContent(jsonObj.getString("Text"));
                        data.setSenderName(jsonObj.getString("SenderName"));
                        dataList.add(data);
                    }
                    return dataList;

            }
            catch (Exception e)
            {
                e.printStackTrace();

            }
        }else{
            Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
            context.startActivity(new Intent(context,ActivitySplash.class));
        }
        return null;
    }
}
