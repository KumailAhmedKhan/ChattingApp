package com.app.sample.chatting.AsyncTaskClasses;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.app.sample.chatting.ActivityClasses.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URLClass.HTTPURL;
import com.app.sample.chatting.ViewModelClasses.GroupChatViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GroupChatMessageStatusAsyncTask extends AsyncTask<GroupChatViewModel,String,String> {

    public static List<GroupChatViewModel> group=new ArrayList<>();
    public GroupChatViewModel groupChatViewModel = new GroupChatViewModel();
    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    private Context context;
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    HTTPURL httpurl=new HTTPURL();
    public static final String PREFS_NAME = "MyApp_Settings";
    String UserId;
    String receiverId;
    String msgId;
    boolean isSeen;
    CheckConnection checkConnection=new CheckConnection();
    public GroupChatMessageStatusAsyncTask(){}
    public GroupChatMessageStatusAsyncTask(Context context,String MessageID,String UserID,boolean isSeen){
        //receiverId=temp.getPreferences(context,"FriendId");
        UserID=temp.getPreferences(context,"Userdata");
        this.UserId=UserID;
        this.msgId=MessageID;
        this.isSeen=isSeen;
    }
    @Override
    protected String doInBackground(GroupChatViewModel... groupChatViewModels) {
        final int checkconnectionflag=checkConnection.connnectioncheck(context);
        if(checkconnectionflag==1) {
            UserId = temp.getPreferences(context, "Userdata");
            try {
                String Url = httpurl.getURl();
                URL myUrl = new URL(Url + "/GetUserNotifactions?UserId="+ UserId);
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
                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                String Data = stringBuilder.toString();
                JSONObject jsonData = new JSONObject(Data);
                JSONArray jsonArr = jsonData.getJSONArray("Model");





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
    protected void onPreExecute() {
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



        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        super.onPostExecute(s);
    }



}
