package com.example.acer.mychating;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
class TabsPageAdapter extends FragmentPagerAdapter{
    public TabsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                RequestFragment requestFragment=new RequestFragment();
                return requestFragment;
            case 1:
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
            case 2:
                FriendsFragments friendsFragments=new FriendsFragments();
                return friendsFragments;
            default:
                    return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:

                return "Request";
            case 1:

                return "Chats";
            case 2:

                return "Friends";
            default:
                return null;
        }
    }
}
