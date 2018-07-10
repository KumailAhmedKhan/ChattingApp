package com.app.sample.chatting.AsyncTask;

import android.app.Activity;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sample.chatting.Activity.ActivityFriendDetails;
import com.app.sample.chatting.Activity.ActivitySplash;
import com.app.sample.chatting.Activity.NewUserActivity;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URL.httpUrl;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class checkFriendShipStatusAsyncTaskLoader extends AsyncTaskLoader<String> {
    private Context context;
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    httpUrl httpurl=new httpUrl();
    public static final String PREFS_NAME = "MyApp_Settings";
    String UserId;
    String receiverId;
    String msgId;
    int UnknownUserID;
    private Intent intent;
    boolean isSeen;
    PendingIntent pendingIntent;
    private ProgressDialog dialog;
    String UnknownUserId;
    CheckConnection checkConnection=new CheckConnection();

    public checkFriendShipStatusAsyncTaskLoader(@NonNull Context context , Bundle bundle) {
        super(context);
        UnknownUserId=bundle.getString("ReceiverId");
    }


    @Nullable
    @Override
    public String loadInBackground() {
        final int checkconnectionflag=checkConnection.connnectioncheck(context);
        if(checkconnectionflag==1) {
            //String UnknownUserId=strings[0];
            UserId = temp.getPreferences(context, "Userdata");
            try {
                String Url = httpurl.getURl();
                int user2id= Integer.parseInt(UnknownUserId);
                URL myUrl = new URL(Url + "/CheckFriendshipStatus?UserId="+UserId+"&User2Id="+user2id);
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
                //JSONArray jsonArr = jsonData.getJSONArray("Model");
                //JSONObject jsonObj = jsonArr.getJSONObject(0);
                String StatusCode=jsonData.getString("StatusCode");
                return StatusCode;



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
}
