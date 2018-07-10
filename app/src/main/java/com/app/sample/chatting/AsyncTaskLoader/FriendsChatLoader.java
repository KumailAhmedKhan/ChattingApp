package com.app.sample.chatting.AsyncTaskLoader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.app.sample.chatting.Activity.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URL.httpUrl;
import com.app.sample.chatting.ViewModelClasses.FriendChatViewModel;

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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FriendsChatLoader extends AsyncTaskLoader<List<FriendChatViewModel>> {

    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    int GroupID;
    private Context context;
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    httpUrl httpurl=new httpUrl();
    public static final String PREFS_NAME = "MyApp_Settings";
    CheckConnection checkConnection=new CheckConnection();
    int userID;
    int receiverId;
    String userId;
    String ID;

    public FriendsChatLoader(Context context, Bundle bundle) {

        super(context);
        receiverId= Integer.parseInt(temp.getPreferences(context,"Userdata"));
        //userID= Integer.parseInt(temp.getPreferences(context,"SenderID"));
        //ID= (bundle.getString("KEY_ID"));
        userID= Integer.parseInt((bundle.getString("KEY_FID")));

    }

    @Override
    public List<FriendChatViewModel> loadInBackground()
    {
        final int checkconnectionflag=checkConnection.connnectioncheck(getContext());

        if(checkconnectionflag==1)
        {

            StringBuilder builder = new StringBuilder();
            try
            {
                String Url = httpurl.getURl();

                URL myUrl = new URL(Url + "/GetUserChat");
                //Create a connection

                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("POST");/*
            if(ID.contains(null)==true){
                ID= String.valueOf(userID);
            }else {
                userID= Integer.parseInt(ID);
            }
*/
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("senderId", userID);
                postDataParams.put("recevierId", receiverId);
                Log.e("params", postDataParams.toString());

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                String postString = getPostDataString(postDataParams);
                writer.write(postString);

                writer.flush();
                writer.close();
                os.close();

                int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK)
                    {

                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                        connection.getInputStream()));
                        StringBuffer sb = new StringBuffer("");
                        String line = "";

                            while ((line = in.readLine()) != null)
                            {

                                sb.append(line);
                                break;
                            }

                        in.close();

                        JSONObject jsonData = new JSONObject(String.valueOf(sb));
                        JSONArray jsonArr = jsonData.getJSONArray("Model");

                        List<FriendChatViewModel> dataList = new ArrayList<>();

                            for (int i = 0; i < jsonArr.length(); i++)
                            {
                                JSONObject jsonObj = jsonArr.getJSONObject(i);
                                FriendChatViewModel data = new FriendChatViewModel();
                                data.setDate(jsonObj.getString("CreatedDate"));
                                data.setSendId(jsonObj.getLong("SenderId"));
                                data.setRecId(jsonObj.getLong("ReceiverUserId"));
                                data.setSenderName(jsonObj.getString("SenderName"));
                                data.setReceiverName(jsonObj.getString("ReceiverUserName"));
                                data.setContent(jsonObj.getString("Text"));
                                data.setMessageStatus(jsonObj.getLong("Status"));
                                data.setMessageId(jsonObj.getLong("Id"));


                                dataList.add(data);
                            }
                            return dataList;


                    }


            } catch (Exception e)
            {
                e.printStackTrace();

            }
        }else {
            Toast.makeText(getContext(),"Internet not Connected",Toast.LENGTH_LONG).show();
            getContext().startActivity(new Intent(getContext(),ActivitySplash.class));
        }
        return null;

    }





    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

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

}
