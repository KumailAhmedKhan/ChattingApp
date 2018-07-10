package com.app.sample.chatting.AdapterClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.app.sample.chatting.R;
import com.app.sample.chatting.SharedPreferenceClass.TemporaryStorageSharedPreferences;
import com.app.sample.chatting.ViewModelClasses.FriendsRecentChatViewModel;

import java.util.ArrayList;
import java.util.List;

public class FriendRecentChatAdapter extends RecyclerView.Adapter<FriendRecentChatAdapter.ViewHolder> {

    private SparseBooleanArray selectedItems;
    private List<FriendsRecentChatViewModel> msg=new ArrayList<>();
    private List<FriendsRecentChatViewModel> orignal=new ArrayList<>();
    private List<FriendsRecentChatViewModel> filter=new ArrayList<>();
    TemporaryStorageSharedPreferences temporary=new TemporaryStorageSharedPreferences();
    private Context ctx;
    // for item click listener
    private OnItemClickListener mOnItemClickListener;
    private boolean clicked = false;
    FriendsRecentChatViewModel friendsRecentChatViewModel=new FriendsRecentChatViewModel();

    BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            // TODO Auto-generated method stub


            friendsRecentChatViewModel.setDate(intent.getStringExtra("MessageDate"));
            friendsRecentChatViewModel.setText(intent.getStringExtra("MessageText"));
            String senderID=intent.getStringExtra("MessageSender");
            friendsRecentChatViewModel.setSenderId(Long.parseLong(senderID));
                notifyDataSetChanged();
            //friendsRecentChatViewModel=intent.get("FriendMessage");
            //friendsRecentChatViewModel=bundle;
            // Get extra data included in the Intent
          // Frien message = intent.getBooleanExtra("CUSTOM_INTENT",false);
           // fl= message;
           // Log.d("receiver", "Got message: " + fl);
        }
    };

    ////////////////////////////////////////////////////////implementing on Recylerview click listener////////////////////////////////
    public interface OnItemClickListener {
        void onItemClick(View view, FriendsRecentChatViewModel obj, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    // for item long click listener
    private OnItemLongClickListener mOnItemLongClickListener;

    public interface OnItemLongClickListener {
        void onItemClick(View view, FriendsRecentChatViewModel obj, int position);
    }


    public void setOnItemLongClickListener(final OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    //////////////////////////////////////////////////////////////////////// implementing ViewHolder Class/////////////////////////////////////////////////////////////
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView content;
        public TextView time;
        public ImageView image;
        public ImageView imagebutton;
        public LinearLayout lyt_parent;
        //public CardView lyt_thread;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            content = (TextView) v.findViewById(R.id.content);
            time = (TextView) v.findViewById(R.id.time);
            image = (ImageView) v.findViewById(R.id.image);
            imagebutton=(ImageView)v.findViewById(R.id.bullet) ;
            lyt_parent = (LinearLayout) v.findViewById(R.id.lyt_parent);

            //lyt_thread=(CardView) v.findViewById(R.id.lyt_thread);

        }

    }
    public FriendRecentChatAdapter(Context ctx, List<FriendsRecentChatViewModel> items){
        this.ctx = ctx.getApplicationContext();
        this.msg=items;
        selectedItems = new SparseBooleanArray();
    }




    @Override
    public FriendRecentChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LocalBroadcastManager.getInstance(ctx).registerReceiver(mMessageReceiver, new IntentFilter("FriendMessage"));
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recentchatsrecycleview, parent, false);
        // set the view's size, margins, paddings and layout parameters
        FriendRecentChatAdapter.ViewHolder vh = new FriendRecentChatAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FriendRecentChatAdapter.ViewHolder holder, final int position) {
        try{
            final FriendsRecentChatViewModel c = msg.get(position);
            holder.title.setText(c.getSenderName());
            holder.content.setText(c.getText());

            holder.imagebutton.setVisibility(View.INVISIBLE);
            if(c.getSenderId()==friendsRecentChatViewModel.getSenderId() ){
                holder.imagebutton.setVisibility(View.VISIBLE);

            }
            //
            //Log.d("KKKK",c.getSenderId()+"XYZ");
            holder.title.setTextColor(Color.parseColor("#000000"));
            // Picasso.with(ctx).load(c.getPhoto()).resize(100, 100).transform(new CircleTransform()).into(holder.image);

            // Here you apply the animation when the view is bound
            setAnimation(holder.itemView, position);
            holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!clicked && mOnItemClickListener != null) {
                        clicked = true;
                        mOnItemClickListener.onItemClick(view, c, position);
                    }
                }
            });

            holder.lyt_parent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onItemClick(view, c, position);
                    }
                    return false;
                }
            });

            holder.lyt_parent.setActivated(selectedItems.get(position, false));

            clicked = false;
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * Here is the key method to apply the animation
     */
    private int lastPosition = -1;
    private void setAnimation(View viewToAnimate, int position) {
        try{ // If the bound view wasn't previously displayed on screen, it's animated
            if (position > lastPosition)
            {

                Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.slide_in_bottom);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return msg.size();
    }
    @Override
    public long getItemId(int position) {
        return msg.get(position).getSenderId();
    }

   public void  setfchat(List<FriendsRecentChatViewModel> data){
        try{
            msg.clear();
            msg.addAll(data);
            notifyDataSetChanged();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

   }

    /**
     * For multiple selection
     */
    public void toggleSelection(int pos) {
        try{
            if (selectedItems.get(pos, false))
            {
                selectedItems.delete(pos);
            } else
                {
                selectedItems.put(pos, true);
                }
            notifyItemChanged(pos);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void clearSelections() {
        try{
            selectedItems.clear();
            notifyDataSetChanged();
        }catch (Exception e )
        {
            e.printStackTrace();
        }

    }
    private ItemFilter mFilter = new ItemFilter();
    public List<FriendsRecentChatViewModel> getSelectedItems() {
        List<FriendsRecentChatViewModel> items = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(msg.get(selectedItems.keyAt(i)));
        }
        return items;
    }
    public Filter getFilter() {
        return mFilter;
    }
    public void removeSelectedItem(){
        try{
            List<FriendsRecentChatViewModel> items = getSelectedItems();
            msg.removeAll(items);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public int getSelectedItemCount() {
        return selectedItems.size();
    }


    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String query = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<FriendsRecentChatViewModel> list = orignal;
            final List<FriendsRecentChatViewModel> result_list = new ArrayList<>(list.size());

            for (int i = 0; i < list.size(); i++) {
                String str_title = list.get(i).getFriend().getName();
                if (str_title.toLowerCase().contains(query)) {
                    result_list.add(list.get(i));
                }
            }

            results.values = result_list;
            results.count = result_list.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filter = (List<FriendsRecentChatViewModel>) results.values;
            notifyDataSetChanged();
        }

    }

}
