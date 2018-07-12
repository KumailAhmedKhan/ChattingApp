package com.app.sample.chatting.NotificationsClass;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import com.app.sample.chatting.ActivityClasses.ActivityChatDetails;
import com.app.sample.chatting.ActivityClasses.ActivityFriendDetails;
import com.app.sample.chatting.ActivityClasses.ActivityGroupDetails;

import com.app.sample.chatting.R;

import static android.app.NotificationManager.IMPORTANCE_HIGH;

public class NotificationGenerate {

    public NotificationGenerate(){

    }
    public void addNotificationforFriends(Context ctx,String Message,String Name,String FrndId,String UserID)throws Exception
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
    public void NotificationForFriendRequest(Context ctx,String Message,String Name,Long ID)throws Exception
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
    public void NotificationForFriendRequesrConfirmed(Context ctx,String Message,String Name,Long ID)throws Exception
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
    public void NotificationForFriendNow(Context ctx,String Message,String Name)throws Exception
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
    public void NotificationForAddedToGroup(Context ctx,String Message,String Name)throws Exception
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
    public void addNotificationforGroups(Context ctx,String Message,String Name,String GroupID)throws Exception
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
