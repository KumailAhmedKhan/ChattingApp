package com.app.sample.chatting.AsyncTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.app.sample.chatting.Activity.ActivityLogin;
import com.app.sample.chatting.Activity.ActivityMain;
import com.app.sample.chatting.Activity.ActivitySplash;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiver.CheckConnection;
import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URL.httpUrl;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONException;
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

public class LoginAsyncTask extends AsyncTask<String, String, String> {

private String mEmail;
private String mPassword;
private Context context;
Activity activity;
TemporaryStorageSharedPreferences temp= new TemporaryStorageSharedPreferences();
httpUrl httpurl=new httpUrl();
        CheckConnection checkConnection=new CheckConnection();
private ProgressDialog progressDialog;




        public LoginAsyncTask(String email, String password, Context ctx,Activity active)
        {
        mEmail = email;
        mPassword = password;
        this.context=ctx.getApplicationContext();
                this.activity=active;

        }
        public LoginAsyncTask(Activity active)
        {
              this.activity=active;
        }


        @Override
protected String doInBackground(String... strings)
        {
                final int checkconnectionflag=checkConnection.connnectioncheck(context);
        if(checkconnectionflag==1)
        {
                // TODO: attempt authentication against a network service.
                    String Url=httpurl.getURl();
                StringBuilder builder=new StringBuilder();
                try
                {
                        String temp=strings[0];
                        URL url=new URL(Url+"/LoginUser");
                        // URL url=new URL("http://localhost:45987/api/SLCApi/LoginUser");
                        //JANNAT,4433\DEVPROJ.4433
                        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");

                        JSONObject postDataParams = new JSONObject();
                        postDataParams.put("PhoneNo", mEmail);
                        postDataParams.put("Password", mPassword);
                        Log.e("params",postDataParams.toString());

                        OutputStream os = connection.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                        String postString=getPostDataString(postDataParams);
                        writer.write(postString);

                        writer.flush();
                        writer.close();
                        os.close();

                        int responseCode=connection.getResponseCode();

                        if (responseCode == HttpURLConnection.HTTP_OK)
                        {

                                BufferedReader in=new BufferedReader(
                                new InputStreamReader(
                                connection.getInputStream()));
                                StringBuffer sb = new StringBuffer("");
                                String line="";

                                while((line = in.readLine()) != null)
                                {
                                        sb.append(line);
                                        break;
                                }

                                in.close();
                                return sb.toString();

                        }
                        else {
                                return new String("false : "+responseCode);
                        }

                        }
                        catch (Exception e)
                        {
                                e.printStackTrace();
                                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                }


                }
        else {
               Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context,ActivityLogin.class));



        }
                return  "";
        }


        @Override
        protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(activity);
                progressDialog.show();

        }


        @Override
protected void onPostExecute(String data)
        {
        JSONObject jsonObject = null;
        String StatusCode;
        int Id;
        progressDialog.dismiss();
        try
        {
        ///////////////////////////////////////////Collecting UserId from Json Object //////////////////////////////
        jsonObject = new JSONObject(data);
        Log.d("DATARETURNINGFROMSERVER",jsonObject+"");
        boolean flaq = jsonObject.getBoolean("Success");

        StatusCode = jsonObject.getString("StatusCode");

        Log.d("DATARETURNING",StatusCode+"");
        //////////////////////////////////////////Write value in Shared Preferences//////////////////////////////////


        if (StatusCode.equals("4") )
        {
                /*
                * JSONObject myData = ...
Gson gson = new Gson();
JsonElement element = gson.fromJson(myData.toString(), JsonElement.class);*/
                JSONObject jsonModel = jsonObject.getJSONObject("Model");
                Id = jsonModel.getInt("Id");
                Gson gson = new Gson();
                JsonElement json=gson.fromJson(jsonModel.toString(),JsonElement.class);
                temp.savePreferences(context,"Userdata",String.valueOf(Id));
                temp.savePreferences(context,"Logindata", String.valueOf(100));
                temp.setPreferencesforJsonElement(context,"UserModel",json);
                Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG).show();
        //context.startActivity(new Intent(context,ActivitySplash.class));
                Intent newIntent= new Intent(context, ActivitySplash.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);

        }
        else if (StatusCode.equals("5") )
        {
        Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_LONG).show();
        }
        } catch (JSONException e)
        {
        e.printStackTrace();
        }
        }

@Override
protected void onCancelled() {

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

