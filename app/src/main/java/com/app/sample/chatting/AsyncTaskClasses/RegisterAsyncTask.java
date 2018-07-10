package com.app.sample.chatting.AsyncTaskClasses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.app.sample.chatting.ActivityClasses.ActivityLogin;
import com.app.sample.chatting.ActivityClasses.ActivityRegister;
import com.app.sample.chatting.CheckConnectionByBroadCastReceiverClass.CheckConnection;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.URLClass.HTTPURL;

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

public class RegisterAsyncTask extends AsyncTask<String, String, String>
{

    private final String mEmail;
    private final String mPassword;
    private final String mName;
    private final String mPhoneNo;
    private final Context context;
    private Activity activity;
    CheckConnection checkConnection=new CheckConnection();
   // private ProgressBar progressBa
    private ProgressDialog progressDialog;
    TemporaryStorageSharedPreferences temp=new TemporaryStorageSharedPreferences();
    HTTPURL httpurl=new HTTPURL();

    public RegisterAsyncTask(String name, String phoneNo, String email, String password, Context ctx, Activity act) {
        this.mEmail = email;
        this.mPassword = password;
        this.mName = name;
        this.mPhoneNo = phoneNo;
        context=ctx;
        this.activity=act;
    }

    @Override
    protected String doInBackground(String... strings)
    {
        final int checkconnectionflag=checkConnection.connnectioncheck(context);
        if(checkconnectionflag==1)
        {
            String Url = httpurl.getURl();
            // TODO: attempt authentication against a network service.
            StringBuilder builder = new StringBuilder();
            try {
                String temp = strings[0];
                URL url = new URL(Url + "/RegisterUser");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("Name", mName);
                postDataParams.put("PhoneNo", mPhoneNo);
                postDataParams.put("Email", mEmail);
                postDataParams.put("Password", mPassword);
                Log.e("params", postDataParams.toString());

                OutputStream os = connection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                String postString = getPostDataString(postDataParams);
                writer.write(postString);

                writer.flush();
                writer.close();
                os.close();

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();

                } else {
                    return new String("false : " + responseCode);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
            }


        }else
            {
                Toast.makeText(context,"Internet not Connected",Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context,ActivityRegister.class));
            }
        return "";
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(activity);
        progressDialog.show();

    }


    @Override
    protected void onPostExecute(String data) {
        try{
            JSONObject json=null;
            json=new JSONObject(data);
            //JSONObject Object=null;
           // Object=json.getJSONObject("StatusCode");
            String StatusCode=json.getString("StatusCode");
            if(progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            //progressBar.setVisibility(View.GONE);
            if (StatusCode.equals("1"))
            {
                Toast.makeText(context, "SignUp Successfully", Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context,ActivityLogin.class));

            } else if(StatusCode.equals("2"))
            {
                Toast.makeText(context, "User with this phone No Already exist.Please try another phone no", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e)
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

