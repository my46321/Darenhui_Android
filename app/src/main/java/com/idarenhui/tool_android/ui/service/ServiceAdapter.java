package com.idarenhui.tool_android.ui.service;//package com.idarenhui.tool_android.ui.service;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
///**
// * Created by Liujilong on 2016/11/8/0008.
// * liujilong.me@gmail.com
// */
//
//class ServiceAdapter extends FragmentPagerAdapter {
//
//    private String[] tags;
//
//    public ServiceAdapter(FragmentManager fm) {
//        super(fm);
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return ServiceListFgt.newInstance();
//    }
//
//    @Override
//    public int getCount() {
//        return tags == null ? 0 : tags.length;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return tags[position];
//    }
//
//    void setTags(String[] tags) {
//        this.tags = tags;
//    }
//}
