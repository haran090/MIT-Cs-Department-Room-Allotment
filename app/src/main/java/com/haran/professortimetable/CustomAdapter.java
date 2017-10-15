package com.haran.professortimetable;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by haran on 15-Oct-17.
 */

class CustomAdapter extends FragmentStatePagerAdapter{

    private final Context context;

    public CustomAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return ListFragment.newInstance(context, position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
