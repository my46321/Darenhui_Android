package com.idarenhui.tool_android.ui.service;//package com.idarenhui.tool_android.ui.service;
//
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//
//import com.dreamspace.superman.R;
//import com.dreamspace.superman.ui.base.BaseActivity;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * Created by Eric on 2016/11/17 0017.
// */
//
//public class AllServiceAty extends BaseActivity {
//
//    @BindView(R.id.activity_all_service_tablayout)
//    TabLayout mTabLayout;
//    @BindView(R.id.activity_all_service_viewPager)
//    ViewPager mViewPager;
//
//    ServiceAdapter mAdapter;
//
//    @Override
//    public int bindLayout() {
//        return R.layout.activity_all_service;
//    }
//
//    @Override
//    protected void initView() {
//
//    }
//
//    @Override
//    protected void initData() {
//        mAdapter = new ServiceAdapter(getSupportFragmentManager());
//        mAdapter.setTags(new String[]{"体育运动", "器乐舞蹈", "书法绘画", "摄影后期"});
//        mViewPager.setAdapter(mAdapter);
//        mTabLayout.setupWithViewPager(mViewPager);
//    }
//
//    @Override
//    protected String tag() {
//        return "AllServiceAty";
//    }
//
//    @OnClick(R.id.back)
//    public void backClicked(View view) {
//        finish();
//    }
//}
