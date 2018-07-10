package com.app.sample.chatting.AsyncTaskClasses;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.app.sample.chatting.ActivityClasses.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URLClass.HTTPURL;
import com.app.sample.chatting.ViewModelClasses.UserNotificationViewModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserNotificationAsyncTaskLoader extends AsyncTaskLoader<List<UserNotificationViewModel>> {
    public  List<UserNotificationViewModel> group=new ArrayList<>();
    public UserNotificationViewModel groupChatViewModel = new UserNotificationViewModel();
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    CheckConnection checkConnection=new CheckConnection();
    private Context context;
    public static  int count = 0;
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    HTTPURL httpurl=new HTTPURL();
    String UserId;


    public UserNotificationAsyncTaskLoader(Context ctx) {

        super(ctx);
    }


    @Override
    public List<UserNotificationViewModel> loadInBackground()
    {
        final int checkconnectionflag=checkConnection.connnectioncheck(getContext());
        if(checkconnectionflag==1) {
            UserId = temp.getPreferences(getContext(), "Userdata");
            try {
                String Url = httpurl.getURl();
                URL myUrl = new URL(Url + "/GetUserNotifactions?UserId=" + UserId);
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

                List<UserNotificationViewModel> dataList = new ArrayList<>();

                for (int i = 0; i < jsonArr.length(); i++) {

                    JSONObject jsonObj = jsonArr.getJSONObject(i);
                    UserNotificationViewModel data = new UserNotificationViewModel();
                    data.setNotificationCount(i);
                    data.setSenderId(jsonObj.getLong("SenderId"));
                    data.setNotificationType(jsonObj.getInt("Type"));
                    data.setText(jsonObj.getString("Text"));
                    data.setNotificationCreatedDate(jsonObj.getString("CreatedDate"));
                    data.setSenderName(jsonObj.getString("SenderName"));
                    data.setGrouptonotify(jsonObj.getString("GroupToNotify"));
                    dataList.add(data);
                }
                //Intent intent = new Intent("NotificationCount");
                //intent.putExtra("NotificationCount",count);
                //LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                return dataList;

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
            getContext().startActivity(new Intent(getContext(),ActivitySplash.class));
        }
        return null;
    }
}
