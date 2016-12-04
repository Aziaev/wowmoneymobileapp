package com.rabigol.wowmoney.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{

    List<Fragment> list = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public void addItem(Fragment fragment){
        list.add(fragment);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
