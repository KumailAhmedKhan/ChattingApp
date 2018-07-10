package com.app.sample.chatting.AsyncTaskLoader;
import android.content.Intent;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import com.app.sample.chatting.Activity.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URL.httpUrl;
import com.app.sample.chatting.ViewModelClasses.UserGroupViewModel;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserGroupLoader extends AsyncTaskLoader<List<UserGroupViewModel>> {

    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    private Context context;
    TemporaryStorageSharedPreferences temp=new TemporaryStorageSharedPreferences();
    httpUrl httpurl=new httpUrl();
    CheckConnection checkConnection=new CheckConnection();
    public UserGroupLoader(Context ctx)
    {
        super(ctx);
        this.context=ctx.getApplicationContext();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public List<UserGroupViewModel> loadInBackground()
    {
        final int checkconnectionflag=checkConnection.connnectioncheck(context);

        if(checkconnectionflag==1)
        {
            StringBuilder builder = new StringBuilder();
            try
            {
                String Url = httpurl.getURl();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                int userId = Integer.parseInt(temp.getPreferences(context, "Userdata"));

                URL myUrl = new URL(Url + "/GetUserFriendAndGroups?userId=" + userId);
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
                List<UserGroupViewModel> dataList = new ArrayList<>();

                    for (int i = 0; i < jsonArr.length(); i++)
                    {

                        JSONObject jsonObj = jsonArr.getJSONObject(i);
                        UserGroupViewModel data = new UserGroupViewModel();
                        data.UserName = jsonObj.getString("Name");
                        data.UserId = jsonObj.getInt("Id");
                        data.AdminId = jsonObj.getInt("AdminId");

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
