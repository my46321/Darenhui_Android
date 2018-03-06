package com.idarenhui.tool_android.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

/**
 * Created by chen on 2017/7/24.
 */
public class CourseDetailPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    public CourseDetailPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments){
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
// CourseDetailActivity中由原来的View改为现在的fragment
//public class CourseDetailPagerAdapter extends PagerAdapter{
//    private List<View> pageview;
//    public CourseDetailPagerAdapter(List<View> viewList){
//        this.pageview = viewList;
//    }
//    @Override
//    //获取当前窗体界面数
//    public int getCount() {
//        // TODO Auto-generated method stub
//        return pageview.size();
//    }
//
//    @Override
//    //判断是否由对象生成界面
//    public boolean isViewFromObject(View arg0, Object arg1) {
//        // TODO Auto-generated method stub
//        return arg0 == arg1;
//    }
//
//    //使从ViewGroup中移出当前View
//    public void destroyItem(View arg0, int arg1, Object arg2) {
//        ((ViewPager) arg0).removeView(pageview.get(arg1));
//    }
//
//    //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
//    public Object instantiateItem(View arg0, int arg1) {
//        ((ViewPager) arg0).addView(pageview.get(arg1));
//        return pageview.get(arg1);
//    }
//}
