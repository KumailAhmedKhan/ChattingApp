package com.app.sample.chatting.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.app.sample.chatting.Activity.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URL.httpUrl;
import com.app.sample.chatting.ViewModelClasses.UserNotificationViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ClearNotificationAsyncTask extends AsyncTask<String,String,Integer>
{
    public List<UserNotificationViewModel> group=new ArrayList<>();
    public UserNotificationViewModel groupChatViewModel = new UserNotificationViewModel();
    public static final String REQUEST_METHOD = "POST";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    CheckConnection checkConnection=new CheckConnection();
    private Context context;
    public static  int count = 0;
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    httpUrl httpurl=new httpUrl();
    String UserId;
    public ClearNotificationAsyncTask(){

    }

    public ClearNotificationAsyncTask(Context ctx) {
        this.context = ctx.getApplicationContext();
    }
    @Override
    protected Integer doInBackground(String... strings)
    {
        final int checkconnectionflag=checkConnection.connnectioncheck(context);
        if(checkconnectionflag==1) {
            UserId = temp.getPreferences(context, "Userdata");
            try {
                String Url = httpurl.getURl();
                URL myUrl = new URL(Url + "/ClearUserNotification?UserId=" + UserId);
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



                for (int i = 0; i < jsonArr.length(); i++) {


                    count++;

                }


                return count;

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
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        super.onPostExecute(integer);
        final String noti= String.valueOf(integer);
        TimerTask task=new TimerTask() {
            @Override
            public void run() {

                Intent intent = new Intent("NotificationCountClear");

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        };
        new Timer().schedule(task, 5000);
    }
}
