package com.idarenhui.tool_android.ui.main;

import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.UserInfo;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.event.FirstEvent;
import com.idarenhui.tool_android.model.gson.OrderCountJSON;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.MyFragPagerAdapter;
import com.idarenhui.tool_android.ui.order.OverViewOrderFrg1;
import com.idarenhui.tool_android.ui.order.OverViewOrderFrg2;
import com.idarenhui.tool_android.ui.order.OverViewOrderFrg3;
import com.idarenhui.tool_android.ui.order.OverViewOrderFrg4;
import com.idarenhui.tool_android.ui.order.OverViewOrderFrg5;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 动态
 *
 * @author Ansen
 * @create time 2015-09-08
 */
public class DynamicFragment extends Fragment {

    @BindView(R.id.dynamic_tab_count_1)
    TextView tab_count_1;
    @BindView(R.id.dynamic_tab_1)
    TextView tab_1;
    @BindView(R.id.dynamic_item_1)
    RelativeLayout dynamicItem1;
    @BindView(R.id.dynamic_tab_count_2)
    TextView tab_count_2;
    @BindView(R.id.dynamic_tab_2)
    TextView tab_2;
    @BindView(R.id.dynamic_item_2)
    RelativeLayout dynamicItem2;
    @BindView(R.id.dynamic_tab_count_3)
    TextView tab_count_3;
    @BindView(R.id.dynamic_tab_3)
    TextView tab_3;
    @BindView(R.id.dynamic_item_3)
    RelativeLayout dynamicItem3;
    @BindView(R.id.dynamic_tab_count_4)
    TextView tab_count_4;
    @BindView(R.id.dynamic_tab_4)
    TextView tab_4;
    @BindView(R.id.dynamic_item_4)
    RelativeLayout dynamicItem4;
    @BindView(R.id.dynamic_tab_count_5)
    TextView tab_count_5;
    @BindView(R.id.dynamic_tab_5)
    TextView tab_5;
    @BindView(R.id.dynamic_item_5)
    RelativeLayout dynamicItem5;
    @BindView(R.id.dynamic_tab_iamge)
    ImageView tabImage;
    @BindView(R.id.dynamic_viewPager)
    ViewPager vPager;

    Unbinder unbinder;
    private List<Fragment> list = new ArrayList<Fragment>();
    private MyFragPagerAdapter adapter;

    private int offset = 0;//偏移量216  我这边只是举例说明,不同手机值不一样
    private int currentIndex = 1;

    OverViewOrderFrg1 fgt_1;
    OverViewOrderFrg2 fgt_2;
    OverViewOrderFrg3 fgt_3;
    OverViewOrderFrg4 fgt_4;
    OverViewOrderFrg5 fgt_5;
//    public int getIndex1() {
//        return index1;
//    }
//
//    public int getIndex2() {
//        return index2;
//    }
//
//    public int getIndex3() {
//        return index3;
//    }
//
//    public int getIndex4() {
//        return index4;
//    }
//
//    public int getIndex5() {
//        return index5;
//    }

    //    private int index1=1;
//    private int index2=2;
//    private int index3=3;
//    private int index4=4;
//    private int index5=5;
    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dynamic, null);
        unbinder = ButterKnife.bind(this, rootView);
        init(rootView);
        initCursorPosition();
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {

        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
        loadFromNet();
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    //绑定设置监听事件等
    public void init(View rootView) {
        //初始化五个Fragment  并且填充到ViewPager
        vPager = (ViewPager) rootView.findViewById(R.id.dynamic_viewPager);
        vPager.setPageTransformer(true, new DepthPageTransformer());
        //OverViewOrderFrg fgt_2, fgt_3, fgt_4, fgt_5;

        fgt_1 = new OverViewOrderFrg1();
        fgt_2 = new OverViewOrderFrg2();
        fgt_3 = new OverViewOrderFrg3();
        fgt_4 = new OverViewOrderFrg4();
        fgt_5 = new OverViewOrderFrg5();

//        Bundle bundle = new Bundle();
//        bundle.putInt("index", 1);
//        fgt_1.setArguments(bundle);

        list.add(fgt_1);
        list.add(fgt_2);
        list.add(fgt_3);
        list.add(fgt_4);
        list.add(fgt_5);

        adapter = new MyFragPagerAdapter(getChildFragmentManager(), list);
        vPager.setAdapter(adapter);
        vPager.setOffscreenPageLimit(5);//预加载页面数，左右滑动不会导致销毁
        vPager.setCurrentItem(0);
        vPager.setOnPageChangeListener(pageChangeListener);

        tab_1.setSelected(true);//默认选中
        onViewClicked(rootView);

        tab_count_1.setText("0");
        tab_count_2.setText("0");
        tab_count_3.setText("0");
        tab_count_4.setText("0");
        tab_count_5.setText("0");
        loadFromNet();
    }

    @Override
    public void onHiddenChanged(boolean hidd) {
        //super.setUserVisibleHint(hidd);
        if (hidd) {
            //相当于Fragment的onResume
        } else {
            tab_count_1.setText("0");
            tab_count_2.setText("0");
            tab_count_3.setText("0");
            tab_count_4.setText("0");
            tab_count_5.setText("0");
            loadFromNet();
            fgt_1.loadData();
            fgt_2.loadData();
            fgt_3.loadData();
            fgt_4.loadData();
            fgt_5.loadData();
            //相当于Fragment的onPause
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void loadFromNet() {
        if (UserInfo.hasLogin==true) {
            OkHttpUtils
                    .get()//
                    .url("https://api.idarenhui.com/DRH_Test/v1.0/order/count")//
                    .addHeader("Access-Token", UserInfo.token)
                    .build()//
                    .execute(new UserCallback<OrderCountJSON>() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.i("test", e.getMessage());
                        }

                        @Override
                        public void onResponse(OrderCountJSON response, int id) {
                            List<String> l = response.data.orders_count;
                            int sum = 0;
                            for (int i = 0; i < l.size(); i++) {
                                sum += Integer.parseInt(l.get(i));
                            }
                            tab_count_1.setText("" + sum);
                            tab_count_2.setText(l.get(3));
                            tab_count_3.setText(l.get(5));
                            tab_count_4.setText(l.get(6));
                            tab_count_5.setText(l.get(7));
                        }
                    });
        }
        else
        {
            ContactDialog.showTipDialog(getContext(),"还未登录，请登录后查看个人订单");
        }
    }

    private void initCursorPosition() {
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        Matrix matrix = new Matrix();

        //标题栏我用weight设置权重  分成5份
        //(width / 5) * 2  这里表示标题栏两个控件的宽度
        //(width / 10)  标题栏一个控件的2分之一
        //7  约等于原点宽度的一半
        matrix.postTranslate((width / 5) * 2 + (width / 10) - 7, 0);//图片平移
        tabImage.setImageMatrix(matrix);

        //一个控件的宽度  我的手机宽度是1080/5=216 不同的手机宽度会不一样哦
        offset = (width / 5);
    }

    /**
     * ViewPager滑动监听,用位移动画实现指示器效果
     * <p>
     * TranslateAnimation 强调一个地方,无论你移动了多少次,现在停留在哪里,你的起始位置从未变化过.
     * 例如:我这个demo里面  推荐移动到了同城,指示器也停留到了同城下面,但是指示器在屏幕上的位置还是推荐下面.
     */
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int index) {
            changeTextColor(index);
//			translateAnimation(index);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    };

    //ViewPager左右滑动时改变标题栏字体颜色
    private void changeTextColor(int index) {
        tab_1.setSelected(false);
        tab_2.setSelected(false);
        tab_3.setSelected(false);
        tab_4.setSelected(false);
        tab_5.setSelected(false);
        tab_count_1.setSelected(false);
        tab_count_2.setSelected(false);
        tab_count_3.setSelected(false);
        tab_count_4.setSelected(false);
        tab_count_5.setSelected(false);

        switch (index) {
            case 0:
                tab_1.setSelected(true);
                tab_count_1.setSelected(true);
                break;
            case 1:
                tab_2.setSelected(true);
                tab_count_2.setSelected(true);
                break;
            case 2:
                tab_3.setSelected(true);
                tab_count_3.setSelected(true);
                break;
            case 3:
                tab_4.setSelected(true);
                tab_count_4.setSelected(true);
                break;
            case 4:
                tab_5.setSelected(true);
                tab_count_5.setSelected(true);
                break;
        }
    }

    /**
     * 移动标题栏点点点...
     *
     * @param index
     */
    private void translateAnimation(int index) {
        TranslateAnimation animation = null;
        switch (index) {
            case 0:
                if (currentIndex == 1) {
                    //从推荐移动到关注   X坐标向左移动216
                    //从tab_2到tab_1
                    animation = new TranslateAnimation(0, -offset, 0, 0);
                } else if (currentIndex == 2) {
                    //从同城移动到关注   X坐标向左移动216*2  记住起始x坐标是同城那里
                    // 从tab_3到tab_1
                    animation = new TranslateAnimation(offset, -offset, 0, 0);
                } else if (currentIndex == 3) {
                    //从同城移动到关注   X坐标向左移动216*2  记住起始x坐标是同城那里
                    // 从tab_4到tab_1
                    animation = new TranslateAnimation(offset, -offset, 0, 0);
                } else if (currentIndex == 4) {
                    //从同城移动到关注   X坐标向左移动216*2  记住起始x坐标是同城那里
                    // 从tab_5到tab_1
                    animation = new TranslateAnimation(offset, -offset, 0, 0);
                }
                break;
            case 1:
                if (currentIndex == 0) {//从关注移动到推荐   X坐标向右移动216
                    animation = new TranslateAnimation(-offset, 0, 0, 0);
                } else if (currentIndex == 2) {//从同城移动到推荐   X坐标向左移动216
                    animation = new TranslateAnimation(offset, 0, 0, 0);
                }
                break;
            case 2:
                if (currentIndex == 0) {//从关注移动到同城   X坐标向右移动216*2  记住起始x坐标是关注那里
                    animation = new TranslateAnimation(-offset, offset, 0, 0);
                } else if (currentIndex == 1) {//从推荐移动到同城   X坐标向右移动216
                    animation = new TranslateAnimation(0, offset, 0, 0);
                }
                break;
            case 3:
                break;
            case 4:
                break;
        }
        animation.setFillAfter(true);
        animation.setDuration(300);
        tabImage.startAnimation(animation);

        currentIndex = index;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //五个item的点击事件
    @OnClick({R.id.dynamic_item_1, R.id.dynamic_item_2, R.id.dynamic_item_3, R.id.dynamic_item_4, R.id.dynamic_item_5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dynamic_item_1:
                vPager.setCurrentItem(0);
                break;
            case R.id.dynamic_item_2:
                vPager.setCurrentItem(1);
                break;
            case R.id.dynamic_item_3:
                vPager.setCurrentItem(2);
                break;
            case R.id.dynamic_item_4:
                vPager.setCurrentItem(3);
                break;
            case R.id.dynamic_item_5:
                vPager.setCurrentItem(5);
                break;
        }
    }
}
