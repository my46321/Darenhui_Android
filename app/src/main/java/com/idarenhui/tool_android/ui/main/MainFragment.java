package com.idarenhui.tool_android.ui.main;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.ui.adapter.MyFragPagerAdapter;
import com.idarenhui.tool_android.ui.course.ClassifyCourseListFgt;
import com.idarenhui.tool_android.ui.course.OverViewCourseFgt;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment extends Fragment {
    String TAG = "Test";

    @BindView(R.id.activity_main_courselist) ImageView tab_courselist;
    @BindView(R.id.activity_main_courseclassify) ImageView tab_courseclassify;
    @BindView(R.id.activity_main_tab_radio) ImageView tab_radio;

    @BindView(R.id.main_viewPager) ViewPager viewPager;
    Unbinder unbinder;

    private MyFragPagerAdapter myFragPagerAdapter;
    private List<Fragment> fragmentList;

    private int offset;//偏移量,在dimens.xml中获取

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.i(TAG, "MainFragment.onCreateView()");
        View mainFgtView = inflater.inflate(R.layout.activity_main, container, false);

        init(mainFgtView);

        return mainFgtView;
    }

    private void init(View view){
        unbinder = ButterKnife.bind(this, view);
        tab_courselist.setSelected(true);
        //设置viewPager
        fragmentList = new ArrayList<>(2);
        Fragment fragment_1 = new OverViewCourseFgt();
        Fragment fragment_2 = new ClassifyCourseListFgt();
        // courseOverView的下标为0， classifyList的下标为1；
        fragmentList.add(fragment_1);
        fragmentList.add(fragment_2);
        myFragPagerAdapter = new MyFragPagerAdapter(getChildFragmentManager(), fragmentList);
        viewPager.setAdapter(myFragPagerAdapter);

        viewPager.setCurrentItem(0);//设置viewPager的初始界面为第一个界面
        viewPager.setOffscreenPageLimit(2);
        viewPager.setOnPageChangeListener(pageChangeListener);
        onViewClicked(view);

        offset = (int) getResources().getDimension(R.dimen.main_fragment_tab_pic_width);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.activity_main_courselist, R.id.activity_main_courseclassify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_main_courselist:
                viewPager.setCurrentItem(0);
                break;
            case R.id.activity_main_courseclassify:
                viewPager.setCurrentItem(1);
                break;
        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int index) {
            changePicColor(index);
            translateAnimation(index);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    private void changePicColor(int index){
        tab_courselist.setSelected(false);
        tab_courseclassify.setSelected(false);
        switch (index){
            case 0:
                tab_courselist.setSelected(true);
                break;
            case 1:
                tab_courseclassify.setSelected(true);
                break;
        }
    }
    private void translateAnimation(int index){
        TranslateAnimation animation = null;
        switch (index) {
            case 0:
                //
                animation = new TranslateAnimation(offset, 0,0,0);
                break;
            case 1:
                animation = new TranslateAnimation(0,offset,0,0);
                break;
        }
        animation.setFillAfter(true);
        animation.setDuration(300);
        tab_radio.startAnimation(animation);

    }
}