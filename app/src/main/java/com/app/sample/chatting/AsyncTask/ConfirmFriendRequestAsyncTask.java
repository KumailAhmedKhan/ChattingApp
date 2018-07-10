package com.app.sample.chatting.AsyncTask;
import com.app.sample.chatting.URL.httpUrl;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.sample.chatting.Activity.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.SignalR.Classes.WebSocketHubConnection;
import com.app.sample.chatting.SignalR.Interface.HubConnection;
import com.app.sample.chatting.URL.httpUrl;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConfirmFriendRequestAsyncTask extends AsyncTask<String,String,Boolean>
{
    private Context context;
    public static final String REQUEST_METHOD = "GET";
    public static final int READ_TIMEOUT = 15000;
    public static final int CONNECTION_TIMEOUT = 15000;
    TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
    httpUrl httpurl=new httpUrl();
    int UserId;
    View view;
    private static int ReceiverID;
    CheckConnection checkConnection=new CheckConnection();
    private HubConnection connection;
    httpUrl httpUrl=new httpUrl();

    public ConfirmFriendRequestAsyncTask() {
    }

    public ConfirmFriendRequestAsyncTask(Context ctx,View vi) {
        context = ctx.getApplicationContext();
        this.view=vi;
        connection=new WebSocketHubConnection( httpUrl.getHubURl(),"",context);
    }


    @Override
    protected Boolean doInBackground(String... strings)
    {
        final int checkconnectionflag=checkConnection.connnectioncheck(context);
        if(checkconnectionflag==1)
        {
            String UnknownUserId=strings[1];
            UserId = Integer.parseInt(temp.getPreferences(context, "Userdata"));
            try
            {
                String Url = httpurl.getURl();
                int user2id= Integer.parseInt(UnknownUserId);
                ReceiverID=user2id;
                URL myUrl = new URL(Url + "/ConfirmFrienRequest?senderId="+UserId+"&RecieverId="+user2id);
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
                Boolean Success=jsonData.getBoolean("Success");
                return Success;

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else{
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
    protected void onPostExecute(Boolean s)
    {
        if(s)
        {
        final String conid=temp.getPreferences(context,"ConnectionID");
            connection.invoke("confirmFriendRequestAsync",conid,ReceiverID);
            final Button FriendShipStatus=(Button) view.findViewById(R.id.FriendRequestButton);
            final Button CancelFriendRequest=(Button) view.findViewById(R.id.CancelFriendRequestButton);
            final Button ConfirmRequest=(Button) view.findViewById(R.id.ConfirmFriendRequestButton);
            final Button Friends=(Button) view.findViewById(R.id.FriendButton);
            FriendShipStatus.setVisibility(View.GONE);
            ConfirmRequest.setVisibility(View.GONE);
            Friends.setVisibility(View.VISIBLE);
            Friends.setText("FRIENDS");
            CancelFriendRequest.setVisibility(View.GONE);

        }

        super.onPostExecute(s);
    }
}
