package com.app.sample.chatting.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();

    public FragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title)
    {
        try
        {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public Fragment getItem(int position)
    {

        switch (position)
        {
            case 0:
                FriendsFragment tab1 = new FriendsFragment();
                return tab1;
            case 1:
                ChatsFragment tab2 = new ChatsFragment();
                return tab2;
            case 2:
                GroupsFragment tab3 = new GroupsFragment();
                return tab3;
            default:
                return null;
        }

        //return mFragments.get(position);
    }


    @Override
    public int getCount()
    {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mFragmentTitles.get(position);
    }
}