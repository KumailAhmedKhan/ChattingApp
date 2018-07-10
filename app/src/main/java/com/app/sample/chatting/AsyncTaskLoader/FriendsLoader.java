package com.app.sample.chatting.AsyncTaskLoader;

import android.content.Intent;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.app.sample.chatting.Activity.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URL.httpUrl;
import com.app.sample.chatting.ViewModelClasses.FriendsViewModel;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FriendsLoader extends AsyncTaskLoader<List<FriendsViewModel>>
{

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    private Context context;
    int UserID;
    CheckConnection checkConnection=new CheckConnection();
    httpUrl httpurl=new httpUrl();
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();

    public FriendsLoader(Context ctx)
        {
            super(ctx);
            this.context=ctx.getApplicationContext();

        }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<FriendsViewModel> loadInBackground()
    {   final int checkconnectionflag=checkConnection.connnectioncheck(context);

        if(checkconnectionflag==1)
        {
        String Url=httpurl.getURl();
        StringBuilder builder=new StringBuilder();
            try
            {
                //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                //int userId= Integer.parseInt(preferences.getString("UserId", "0"));
                //////////////////////////////////////////Write value in Shared Preferences//////////////////////////////////
                String userId;
               userId=temp.getPreferences(context,"Userdata");
                URL myUrl = new URL(Url+"/GetAllFriends?UserId="+userId);
                //Create a connection
                HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
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
                String inputLine="";
                    while((inputLine = reader.readLine()) != null)
                    {
                        stringBuilder.append(inputLine);
                    }
                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();
                String Data=stringBuilder.toString();
                JSONObject jsonData=new JSONObject(Data);
                JSONArray jsonArr = jsonData.getJSONArray("Model");
                List<FriendsViewModel> dataList = new ArrayList<>();

                    for (int i = 0; i < jsonArr.length(); i++)
                    {
                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        FriendsViewModel data = new FriendsViewModel();
                        data.Id=jsonObj.getInt("Id");
                        data.Email = jsonObj.getString("Email");
                        data.PhoneNo = jsonObj.getString("PhoneNo");
                        data.Name = jsonObj.getString("Name");

                        dataList.add(data);
                    }
                    return  dataList;

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
