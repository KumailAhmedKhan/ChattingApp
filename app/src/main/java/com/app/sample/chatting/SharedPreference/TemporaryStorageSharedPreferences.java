package com.app.sample.chatting.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TemporaryStorageSharedPreferences{
    protected final static int DEFAULT = 0;
        int temp = 0;


        public static void savePreferences(Context mContext, String key, String value)
        {
                try{
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(key, value).apply();
                }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }

        }
        public static void setPreferencesforJsonElement(Context mContext, String key, JsonElement object)
        {
            try{
                String value=object.toString();
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(key, value).apply();
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        public static JsonElement getPreferencesforJsonElement(Context context, String keyValue) {
           // try{}catch (Exception e){e.printStackTrace();}
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String userModel=sharedPreferences.getString(keyValue, "");
            JsonElement myObject = new Gson().fromJson(userModel,JsonElement.class);
        return myObject;
        }
        public static void setPreferencesforJsonArray(Context mContext, String key, JsonArray object)
        {
            try{
                String jsonarraystring=object.toString();
                // //.substring(1,object.toString().length()-1);
                savePreferences(mContext,key,jsonarraystring);
            }catch (Exception e)
            {
                e.printStackTrace();
            }


        }
        public static List<String> getPreferencesforJsonArray(Context context, String keyValue)
        {

           // try{}catch (Exception e){e.printStackTrace();}
            String onlineUserString=getPreferences(context,keyValue);
            onlineUserString=onlineUserString.substring(1,onlineUserString.toString().length()-1);
            onlineUserString=onlineUserString.replaceAll(" ","");

            List<String> onlineUsers = new ArrayList<String>(Arrays.asList( onlineUserString.split(",")));
            //onlineUsers=onlineUsers.re

            return onlineUsers;
        }


        /**
         * @param context
         * @param keyValue
         * @return
         */
        public static String getPreferences(Context context, String keyValue) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            try{
                String temp=sharedPreferences.getString(keyValue, "");
            }catch (Exception e){
                e.printStackTrace();
            }

            return sharedPreferences.getString(keyValue, "");
        }

        /**
         * @param mContext
         */
        public static void removeAllSharedPreferences(Context mContext) {
            try{
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear().apply();
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
}
