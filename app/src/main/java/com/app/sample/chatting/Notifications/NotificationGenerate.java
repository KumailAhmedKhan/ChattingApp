package com.app.sample.chatting.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import com.app.sample.chatting.Activity.ActivityChatDetails;
import com.app.sample.chatting.Activity.ActivityFriendDetails;
import com.app.sample.chatting.Activity.ActivityGroupDetails;
import com.app.sample.chatting.Activity.ActivityMain;

import com.app.sample.chatting.R;
import com.app.sample.chatting.SignalR.Services.SignalRConnectionService;

import static android.app.NotificationManager.IMPORTANCE_HIGH;

public class NotificationGenerate {

    public NotificationGenerate(){

    }
    public void addNotificationforFriends(Context ctx,String Message,String Name,String FrndId,String UserID)
    {
        try{
            long[] pattern = {500,500,500,500,500,500,500,500,500};
            NotificationCompat.Builder builder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                            .setSmallIcon(R.drawable.ic_call)
                            .setContentTitle(Name)
                            .setLights(Color.BLUE, 500, 500)
                            .setVibrate(pattern)
                            .setPriority(IMPORTANCE_HIGH)
                            .setAutoCancel(true)
                            .setContentText(Message);
            Intent notificationIntent = new Intent();
            notificationIntent.putExtra("KEY_FID",FrndId);
            notificationIntent.putExtra("UserId",UserID);
            notificationIntent.putExtra("KEY_FNAME",Name);
            PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            // Add as notification
            NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void NotificationForFriendRequest(Context ctx,String Message,String Name,Long ID)
    {
        try{
            long[] pattern = {500,500,500,500,500,500,500,500,500};
            NotificationCompat.Builder builder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                            .setSmallIcon(R.drawable.ic_call)
                            .setContentTitle(Name)
                            .setLights(Color.BLUE, 500, 500)
                            .setVibrate(pattern)
                            .setPriority(IMPORTANCE_HIGH)
                            .setAutoCancel(true)
                            .setContentText(Message);
            Intent notificationIntent = new Intent(ctx,ActivityFriendDetails.class);
            notificationIntent.putExtra("Name",Name);
            notificationIntent.putExtra("ReceiverId",ID);
            //notificationIntent.putExtra("KEY_FNAME",Name);
            PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            //checkFriendShipStatusAsyncTask = (CheckFriendShipStatusAsyncTask) new CheckFriendShipStatusAsyncTask(ctx, contentIntent,notificationIntent).execute(String.valueOf(ID));
            builder.setContentIntent(contentIntent);
            // Add as notification
            NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void NotificationForFriendRequesrConfirmed(Context ctx,String Message,String Name,Long ID)
    {
        try{
            long[] pattern = {500,500,500,500,500,500,500,500,500};
            NotificationCompat.Builder builder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                            .setSmallIcon(R.drawable.ic_call)
                            .setContentTitle(Name)
                            .setLights(Color.BLUE, 500, 500)
                            .setVibrate(pattern)
                            .setPriority(IMPORTANCE_HIGH)
                            .setAutoCancel(true)
                            .setContentText(Message);
            Intent notificationIntent = new Intent(ctx,ActivityFriendDetails.class);
            notificationIntent.putExtra("Name",Name);
            notificationIntent.putExtra("ReceiverId",ID);
            PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            // Add as notification
            NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void NotificationForFriendNow(Context ctx,String Message,String Name)
    {
        try{
            long[] pattern = {500,500,500,500,500,500,500,500,500};
            NotificationCompat.Builder builder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                            .setSmallIcon(R.drawable.ic_call)
                            .setContentTitle(Name)
                            .setLights(Color.BLUE, 500, 500)
                            .setVibrate(pattern)
                            .setPriority(IMPORTANCE_HIGH)
                            .setAutoCancel(true)
                            .setContentText(Message);
            Intent notificationIntent = new Intent();
            //notificationIntent.putExtra("KEY_FID",FrndId);
            //notificationIntent.putExtra("UserId",UserID);
            notificationIntent.putExtra("KEY_FNAME",Name);
            PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            // Add as notification
            NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void NotificationForAddedToGroup(Context ctx,String Message,String Name)
    {
        try{
            long[] pattern = {500,500,500,500,500,500,500,500,500};
            NotificationCompat.Builder builder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                            .setSmallIcon(R.drawable.ic_call)
                            .setContentTitle(Name)
                            .setLights(Color.BLUE, 500, 500)
                            .setVibrate(pattern)
                            .setPriority(IMPORTANCE_HIGH)
                            .setAutoCancel(true)
                            .setContentText(Message);
            Intent notificationIntent = new Intent(ctx,ActivityChatDetails.class);
            //notificationIntent.putExtra("KEY_FID",FrndId);
            //notificationIntent.putExtra("UserId",UserID);
            notificationIntent.putExtra("KEY_FNAME",Name);
            PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            // Add as notification
            NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void addNotificationforGroups(Context ctx,String Message,String Name,String GroupID)
    {
        try{
            long[] pattern = {500,500,500,500,500,500,500,500,500};
            NotificationCompat.Builder builder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                            .setSmallIcon(R.drawable.ic_call)
                            .setContentTitle(Name)
                            .setLights(Color.BLUE, 500, 500)
                            .setVibrate(pattern)
                            .setPriority(IMPORTANCE_HIGH)
                            .setAutoCancel(true)
                            .setContentText(Message);
            Intent notificationIntent = new Intent(ctx,ActivityGroupDetails.class);
            notificationIntent.putExtra("KEY_GID",GroupID);
            notificationIntent.putExtra("KEY_GNAME","Test");
            PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);
            // Add as notification
            NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }
            catch (Exception e)
            {
                 e.printStackTrace();
            }

    }
}
