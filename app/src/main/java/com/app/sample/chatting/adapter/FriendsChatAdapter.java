package com.app.sample.chatting.adapter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreference.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.ViewModelClasses.FriendChatViewModel;
import com.app.sample.chatting.ViewModelClasses.GroupChatViewModel;

import java.util.List;

public class FriendsChatAdapter extends BaseAdapter {

    private List<FriendChatViewModel> mMessages;
    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    TemporaryStorageSharedPreferences temp=new TemporaryStorageSharedPreferences();
    private int userId;
     com.app.sample.chatting.AsyncTask.FriendChatMessageStatusAsyncTask messagestatus=new com.app.sample.chatting.AsyncTask.FriendChatMessageStatusAsyncTask();
     FriendChatViewModel friendChatViewModel=new FriendChatViewModel();
    BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub


            friendChatViewModel.setDate(intent.getStringExtra("MessageDate"));
            friendChatViewModel.setContent(intent.getStringExtra("MessageText"));
            String senderID=intent.getStringExtra("MessageSender");
            friendChatViewModel.setSendId(Long.parseLong(senderID));
           // mMessages.add(friendChatViewModel);
            notifyDataSetChanged();
            //friendsRecentChatViewModel=intent.get("FriendMessage");
            //friendsRecentChatViewModel=bundle;
            // Get extra data included in the Intent
            // Frien message = intent.getBooleanExtra("CUSTOM_INTENT",false);
            // fl= message;
            // Log.d("receiver", "Got message: " + fl);
        }
    };


    public interface OnItemClickListener {
        void onItemClick(View view, FriendChatViewModel obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public FriendsChatAdapter(Context context, List<FriendChatViewModel> messages) {
        super();
        this.ctx = context;
        this.mMessages = messages;
    }

    @Override
    public int getCount() {
        return mMessages.size();
    }

    @Override
    public Object getItem(int position) {
        return mMessages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mMessages.get(position).getId();
    }

    public void setfriendchat(List<FriendChatViewModel> data){
        try{
            mMessages.clear();;
            mMessages.addAll(data);
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // LocalBroadcastManager.getInstance(ctx).registerReceiver(mMessageReceiver, new IntentFilter("MessageFromFriend"));

        userId= Integer.parseInt(temp.getPreferences(ctx,"Userdata"));
        FriendChatViewModel msg = (FriendChatViewModel) getItem(position);
        ViewHolder holder;
        if(convertView == null){
            holder 				= new ViewHolder();
            convertView			= LayoutInflater.from(ctx).inflate(R.layout.recentfriendchatdetails, parent, false);
            holder.time 		= (TextView) convertView.findViewById(R.id.text_time);
//            holder.name         =(TextView) convertView.findViewById(R.id.Name);
            holder.message 		= (TextView) convertView.findViewById(R.id.text_content);
            holder.lyt_thread 	= (CardView) convertView.findViewById(R.id.lyt_thread);
            holder.lyt_parent 	= (LinearLayout) convertView.findViewById(R.id.lyt_parent);
            holder.image_status	= (ImageView) convertView.findViewById(R.id.imagegreenpoint);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Log.d("Message ID",msg.getMessageId()+"Dynamic Message");
        Log.d("Message ID",messagestatus.friendChatViewModel.getMessageId()+"Static Message");
        Log.d("Message List",messagestatus.frnd+"Static Message");


/*
       // for(int i=0;i<messagestatus.frnd.size();i++){
            if(msg.getRecId()!=userId  ){
               // if((msg.getMessageId()==(messagestatus.frnd.get(0).getMessageId()))
                    //    || (msg.getContent().contains(messagestatus.frnd.get(0).getContent()))
                      //  && msg.getDate().contains(messagestatus.frnd.get(0).getDate()) )
                //{
                    holder.message.setTextColor(Color.parseColor("#000000"));
                    holder.image_status.setVisibility(View.INVISIBLE);
                    holder.message.setText(msg.getContent());
                    holder.time.setText(msg.getDate());
                    holder.lyt_parent.setPadding(100, 10, 15, 10);
                    holder.lyt_parent.setGravity(Gravity.RIGHT);
                    holder.time.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.lyt_thread.setCardBackgroundColor(ctx.getResources().getColor(R.color.blue_fb));

               // }
               // else
                /*
                    holder.message.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.image_status.setVisibility(View.INVISIBLE);
                    holder.message.setText(msg.getContent());
                    holder.time.setText(msg.getDate());
                    holder.lyt_parent.setPadding(100, 10, 15, 10);
                    holder.lyt_parent.setGravity(Gravity.RIGHT);
                    holder.time.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.lyt_thread.setCardBackgroundColor(ctx.getResources().getColor(R.color.blue_fb));}*/


           // }
          //  else
          //  {

               // if( ( msg.getMessageId()==(messagestatus.frnd.get(0).getMessageId()))
                 //       || (msg.getContent().contains(messagestatus.frnd.get(0).getContent()))
                 //       && (msg.getDate().contains(messagestatus.frnd.get(0).getDate()))) {
                   /* holder.message.setText(msg.getContent());
                    holder.time.setText(msg.getDate());
                    holder.image_status.setVisibility(View.VISIBLE);
                    holder.lyt_parent.setPadding(15, 10, 100, 10);
                    holder.lyt_parent.setGravity(Gravity.LEFT);
                    holder.message.setTextColor(Color.parseColor("#FFFFFF"));
                    // holder.message.setTypeface(null, Typeface.BOLD);
                    holder.time.setTextColor(Color.parseColor("#000000"));
                    holder.lyt_thread.setCardBackgroundColor(Color.parseColor("#E6E6E6"));
                    // msg.setMessageStatus(1);
                //}
               // else{
                    holder.message.setText(msg.getContent());
                    holder.time.setText(msg.getDate());
                    holder.image_status.setVisibility(View.INVISIBLE);
                    holder.lyt_parent.setPadding(15, 10, 100, 10);
                    holder.lyt_parent.setGravity(Gravity.LEFT);
                    holder.message.setTextColor(Color.parseColor("#000000"));
                    holder.time.setTextColor(Color.parseColor("#000000"));
                    holder.lyt_thread.setCardBackgroundColor(Color.parseColor("#E6E6E6"));
                }


            //}//}*/

        if(msg.getRecId()!=userId  )
        {
           // if((msg.getMessageId()==(messagestatus.friendChatViewModel.getMessageId()))
             //       || (msg.getContent().contains(messagestatus.friendChatViewModel.getContent()))
              //      && msg.getDate().contains(messagestatus.friendChatViewModel.getDate()) )

                holder.message.setTextColor(Color.parseColor("#FFFFFF"));
                holder.image_status.setVisibility(View.INVISIBLE);
                holder.message.setText(msg.getContent());
                holder.time.setText(msg.getDate());
                holder.lyt_parent.setPadding(100, 10, 15, 10);
                holder.lyt_parent.setGravity(Gravity.RIGHT);
                holder.time.setTextColor(Color.parseColor("#FFFFFF"));
                holder.lyt_thread.setCardBackgroundColor(ctx.getResources().getColor(R.color.blue_fb));


           // else
           /* {
                holder.message.setTextColor(Color.parseColor("#FFFFFF"));
                holder.image_status.setVisibility(View.INVISIBLE);
                holder.message.setText(msg.getContent());
                holder.time.setText(msg.getDate());
                holder.lyt_parent.setPadding(100, 10, 15, 10);
                holder.lyt_parent.setGravity(Gravity.RIGHT);
                holder.time.setTextColor(Color.parseColor("#FFFFFF"));
                holder.lyt_thread.setCardBackgroundColor(ctx.getResources().getColor(R.color.blue_fb));}*/


        }
        else
            {

            //if( ( msg.getMessageId()==(messagestatus.friendChatViewModel.getMessageId())) || (msg.getContent().contains(messagestatus.friendChatViewModel.getContent())) && (msg.getDate().contains(messagestatus.friendChatViewModel.getDate()))) {
                holder.message.setText(msg.getContent());
                holder.time.setText(msg.getDate());
                holder.image_status.setVisibility(View.INVISIBLE);
                holder.lyt_parent.setPadding(15, 10, 100, 10);
                holder.lyt_parent.setGravity(Gravity.LEFT);
                holder.message.setTextColor(Color.parseColor("#000000"));
               // holder.message.setTypeface(null, Typeface.BOLD);
                holder.time.setTextColor(Color.parseColor("#000000"));
                holder.lyt_thread.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
               // msg.setMessageStatus(1);
          //  }
            //else
              /*)  {
                holder.message.setText(msg.getContent());
                holder.time.setText(msg.getDate());
                holder.image_status.setVisibility(View.INVISIBLE);
                holder.lyt_parent.setPadding(15, 10, 100, 10);
                holder.lyt_parent.setGravity(Gravity.LEFT);
                holder.message.setTextColor(Color.parseColor("#000000"));
                holder.time.setTextColor(Color.parseColor("#000000"));
                holder.lyt_thread.setCardBackgroundColor(Color.parseColor("#E6E6E6"));
            }*/


        }
        return convertView;
    }

    /**
     * remove data item from messageAdapter
     *
     **/
    public void remove(int position){
        try{
            mMessages.remove(position);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * add data item to messageAdapter
     *
     **/
    public void adaptercleaR(){
        mMessages.clear();;
    }
    public void add(FriendChatViewModel msg){
        try{
            //mMessages.clear();
            mMessages.add(msg);
            notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static class ViewHolder{
        TextView time;
        TextView message;
        LinearLayout lyt_parent;
        CardView lyt_thread;
        ImageView image_status;
        TextView name;
    }
}
