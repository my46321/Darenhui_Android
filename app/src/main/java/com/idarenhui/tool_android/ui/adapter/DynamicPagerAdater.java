package com.idarenhui.tool_android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by chen on 2017/7/19.
 */

public class DynamicPagerAdater extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public DynamicPagerAdater(FragmentManager fragmentManager, List<Fragment> fragments){
        super(fragmentManager);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
