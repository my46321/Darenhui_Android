package com.idarenhui.tool_android.ui.service;//package com.idarenhui.tool_android.ui.service;
//
//import android.content.Intent;
//import android.support.design.widget.TabLayout;
//import android.text.Spannable;
//import android.text.SpannableStringBuilder;
//import android.text.style.RelativeSizeSpan;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.dreamspace.superman.R;
//import com.dreamspace.superman.bean.Master;
//import com.dreamspace.superman.ui.base.BaseActivity;
//import com.dreamspace.superman.views.GridLayout;
//import com.dreamspace.superman.views.MasterView;
//import com.dreamspace.util.DisplayUtils;
//import com.dreamspace.util.Logger;
//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.UMShareListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
//public class ServiceDetailAty extends BaseActivity {
//
//    @BindView(R.id.service_detail_price)
//    TextView mTvPrice;
//    @BindView(R.id.service_detail_main_image)
//    ImageView mIvMain;
//    @BindView(R.id.service_detail_tab_image)
//    TabLayout mTabLayout;
//    @BindView(R.id.service_detail_tv_intro)
//    TextView mTvIntro;
//    @BindView(R.id.service_detail_tv_target)
//    TextView mTvTarget;
//    @BindView(R.id.service_detail_grid_images)
//    GridLayout mGridLayout;
//    @BindView(R.id.service_detail_intro_time)
//    TextView mTvTime;
//    @BindView(R.id.service_detail_intro_caution)
//    TextView mTvCaution;
//    @BindView(R.id.service_detail_intro_master1)
//    MasterView mMasterView1;
//    @BindView(R.id.service_detail_intro_master2)
//    MasterView mMasterView2;
//
//    int[] picIds = new int[]{
//            R.drawable.activity_cover2,
//            R.drawable.activity_show1,
//            R.drawable.service,
//            R.drawable.activity_show1,
//            R.drawable.service,
//            R.drawable.activity_cover,
//            R.drawable.activity_cover2,
//            R.drawable.activity_show1,
//            R.drawable.activity_cover2,
//    };
//
//    @Override
//    public int bindLayout() {
//        return R.layout.activity_service_detail;
//    }
//
//
//    @Override
//    protected void initView() {
//
//        String s1 = "800";
//        String s2 = "元/";
//        String s3 = "25";
//        String s4 = "课时";
//        Spannable ss = new SpannableStringBuilder(s1 + s2 + s3 + s4);
//        ss.setSpan(new RelativeSizeSpan(0.8f), s1.length() + s2.length(), ss.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mTvPrice.setText(ss);
//
//        Glide.with(this).load(picIds[0]).centerCrop().into(mIvMain);
//        final int count = picIds.length;
//        int width = DisplayUtils.dip2px(this, 67);
//        int height = DisplayUtils.dip2px(this, 45);
//        for (int i = 0; i < count; i++) {
//            TabLayout.Tab tab = mTabLayout.newTab();
//            ImageView iv = new ImageView(this);
//            tab.setCustomView(iv);
//            mTabLayout.addTab(tab);
//            Glide.with(this).load(picIds[i]).override(width, height).centerCrop().into(iv);
//        }
//        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Glide.with(ServiceDetailAty.this).load(picIds[tab.getPosition()]).centerCrop().into(mIvMain);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        mTvIntro.setText("夏天来了，你总是对自己身材不满意？别人在“炫腹”，而你却不是太瘦就是太胖？夏天来了，你总是对自己身材不满意？别人在“炫腹”，而你却不是太瘦就是太胖？夏天来了，你总是对自己身材不满意？别人在“炫腹”，而你却不是太瘦就是太胖？");
//
//        mTvTarget.setText("1.了解人体肌肉群分布。\n2.各部分肌肉练习指导。\n3.日常饮食指导。\n4.宿舍日常训练方法。\n5.身材保持，习惯养成。");
//        mGridLayout.setNumInRow(3);
//        mGridLayout.setImageListsByIds(picIds);
//        mTvTime.setText("9月9日开始，共计4周，16节课，32个学时。\n(时间安排详见训练计划)");
//        mTvCaution.setText("报名成功后要进行初步的学院筛选，所以，报名付费后不是报名成功哟，报名失败退款会自动退回。");
//        Master master = new Master();
//        master.setAvatarId(R.drawable.avatar_test);
//        master.setRealName("潘之琳");
//        master.setMasterLabel("游泳达人");
//        master.setShortProfile("三年的咖啡点运营经历，带给我的不单单是冲泡咖啡的手艺，更多的是咖啡那种慢生活的理念。");
//        mMasterView1.setMaster(master);
//        mMasterView2.setMaster(master);
//    }
//
//    @Override
//    protected void initData() {
////        mId = getIntent().getStringExtra(INTENT_ID);
////        Logger.e(mId);
//    }
//
//    @Override
//    protected String tag() {
//        return "ServiceDetailAty";
//    }
//
//
////    @OnClick(R.id.service_detail_reserve)
////    void reserveActivity(View v){
////        readyGo(ReserveAty.class);
////    }
//
//    @OnClick(R.id.share)
//    public void shareClicked(View view) {
//        UMImage image = new UMImage(ServiceDetailAty.this, "getCover()");
//        new ShareAction(this).setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
//                .withText("Description")
//                .withMedia(image)
//                .withTitle("title")
//                .withTargetUrl("http://www.idarenhui.com/DaRenBackend/share/#/share_class?id=18")
//                .setCallback(umShareListener)
//                .open();
//
////        new ShareAction(ActivityDetailAty.this).withText("hello")
////                .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
////                .setCallback(umShareListener).open();
//
//    }
//
//    //如果使用的是qq或者新浪精简版jar，需要在您使用分享或授权的Activity（fragment不行）中添加如下回调代码：
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        /** attention to this below ,must add this**/
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//    }
//
//    private UMShareListener umShareListener = new UMShareListener() {
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
//            Logger.d("plat", "platform" + platform);
//            Toast.makeText(ServiceDetailAty.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(ServiceDetailAty.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                Logger.d("throw", "throw:" + t.getMessage());
//            }
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(ServiceDetailAty.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
//        }
//    };
//}
