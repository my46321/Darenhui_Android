package com.idarenhui.tool_android.ui.course;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balysv.materialripple.MaterialRippleLayout;
import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.custom.CustomToast;
import com.idarenhui.tool_android.model.CourseOverviewInfo;
import com.idarenhui.tool_android.model.RecommendList;
import com.idarenhui.tool_android.model.gson.CourseOverViewInfoJSON;
import com.idarenhui.tool_android.model.gson.recommendData;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.MainRecycleListAdapter;
import com.my.view.CarouselFigureView;
import com.my.view.switchanimotion.DepthPageTransformer;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
/**
 * Created by chen on 2017/7/18.
 */

public class OverViewCourseFgt extends Fragment {

    @BindView(R.id.carousel_figure_view)
    CarouselFigureView carouselFigureView;
    Unbinder unbinder;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private List<CourseOverviewInfo> infoList;
    private List<RecommendList> recommendDatas;
    private MainRecycleListAdapter.OnItemClickLisitenter lisitenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MainRecycleListAdapter listAdapter;
    private View view;

    private String TAG = "Test";

    private int pagesize = 100;
    private int offset = 0;


    @Override
    public void onDestroy() {
        super.onDestroy();
        writeData();
    }

    private void readData() {
        //1、获取Preferences
        SharedPreferences settings = getContext().getSharedPreferences("setting", 0);
        //2、取出数据
        offset = settings.getInt("offset", offset);
        Log.i(Constants.TAG, "取出的offset的值大小为：" + offset);
        //以上就是Android中SharedPreferences的使用方法，其中创建的Preferences文件存放位置可以在Eclipse中查看：
    }

    private void writeData() {
        //1、打开Preferences，名称为setting，如果存在则打开它，否则创建新的Preferences
        SharedPreferences settings = getContext().getSharedPreferences("setting", 0);
        //2、让setting处于编辑状态
        SharedPreferences.Editor editor = settings.edit();
        //3、存放数据
        editor.putInt("offset", 0);
        //4、完成提交
        editor.commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "OverViewCourseFgt.onCreateView()");
        view = inflater.inflate(R.layout.viewpage_recyclelist, container, false);
        readData();
        loadData();
        init();
        initPullRefresh();
        unbinder = ButterKnife.bind(this, view);

        //设置自定义style，我提供了goole官方的两种动画，可以直接使用
        //new DepthPageTransformer()
        //new ZoomOutPageTransformer()
        carouselFigureView.setViewPagerSwitchStyle(new DepthPageTransformer());
        //设置切换动画的持续时间
        //::::::注意::::::
        //该时间一定要小于轮播图切换时间
        carouselFigureView.setViewPagerSwitchSpeed(500);
        //点击事件
        carouselFigureView.setCarouselFigureItemClickListener(new CarouselFigureView.CarouselFigureItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                //Toast.makeText(MainActivity.this,"position:"+position,Toast.LENGTH_SHORT).show();
                String lesson_id =recommendDatas.get(position).getlesson_id();
                Bundle bundle = new Bundle();
                bundle.putString("lesson_id", lesson_id);
                Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }

    public void init() {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.courseOverView_recycleList);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(linearLayoutManager);
        infoList = new ArrayList<>();
        recommendDatas=new ArrayList<>();
        listAdapter = new MainRecycleListAdapter(infoList, getContext());

        setItemLisenter();

        recyclerView.setAdapter(listAdapter);
    }

    public void setInfoList(List<CourseOverviewInfo> infoList) {
        this.infoList = infoList;
    }

    //可以利用loadData()来加载上次退出页面时保存的list中的信息
    public void loadData() {
        //从服务器加载数据
        loadCycleFromNet();
        loadFromNet();
    }

    //下拉刷新
    public void loadCycleFromNet(){
        String url = "https://api.idarenhui.com/DRH_Test/v1.0/recommend";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new UserCallback<recommendData>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }
                    @Override
                    public void onResponse(recommendData response, int id) {
                          recommendDatas = response.getDate().getrecommend();
                        ArrayList<String> url = new ArrayList<>();
//                        url.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505296960255&di=e70faa98661da6b27062f2e9a14f6382&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3D661f8df43301213fdb3e469f3c8e5ca4%2Fb90e7bec54e736d1c421352591504fc2d56269e9.jpg");
//                        url.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505892772&di=c3e249a16fe15ec5f55d587ea4a80ec7&imgtype=jpg&er=1&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F03087bf40ad162d945da39b211dfa9ec8a13cd6c.jpg");
//                        url.add("http://www.ps-xxw.cn/uploadfile/201503/23/0E192020896.jpg");
//                        url.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505297981534&di=2328c0c066ad41e1a0d818149df521dd&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fphotoblog%2F1411%2F13%2Fc0%2F40872889_40872889_1415845730453_mthumb.jpg");
//                        //可使用setURL()或者setResourceList()，两者必选其一
                        //除了该方法，其余均为可选
                        for(int i=0;i<4;i++){
                            url.add(recommendDatas.get(i).getImg_url());
                        }
                        carouselFigureView.setURL(url);
                    }
                });

                }
    public void initPullRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "OverViewCourseFgt.onRefresh()");
                        listAdapter.clearList();
                        loadFromNet();
                        //刷新完成
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    //设置recycleList中item的点击事件
    public void setItemLisenter() {
        listAdapter.setOnItemClickLisitenter(new MainRecycleListAdapter.OnItemClickLisitenter() {
            @Override
            public void onItemClick(View v, int position) {
//                v.animate().setInterpolator(new DecelerateInterpolator()).
//                        scaleX(.7f).scaleY(.7f);
//                try {
//                    wait(1000);
//                }
//                catch (Throwable t)
//                {
//                    t.printStackTrace();
//                }
//                v.animate().setInterpolator(new DecelerateInterpolator()).
//                        scaleX(1).scaleY(1);
                Log.i(TAG, "首页选中第" + position + "个item");
                //将CourseOverviewInfo中的课程id信息传入activity即可
                CourseOverviewInfo info = infoList.get(position);
                String lesson_id = info.getId();
                Bundle bundle = new Bundle();
                bundle.putString("lesson_id", lesson_id);
                Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    //从服务器加载首页信息
    public void loadFromNet() {
        String url = "https://api.idarenhui.com/DRH_Test/v1.0/index";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("pagesize", String.valueOf(pagesize))
                .addParams("offset", String.valueOf(offset))
                .build()
                .execute(new UserCallback<CourseOverViewInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(CourseOverViewInfoJSON response, int id) {
                        //刚从服务器加载的课程概览的list;
                        List<CourseOverViewInfoJSON.Data.CourseData> courseDatas = response.getDate().getLessonList();
                        String _id, title, description, imgUrl;
                        List<CourseOverviewInfo> headDatas = new ArrayList<>();
                        for (int i = 0; i < courseDatas.size(); i++) {
                            _id = courseDatas.get(i).get_id();
                            title = courseDatas.get(i).getTitle();
                            description = courseDatas.get(i).getDescription();
                            imgUrl = courseDatas.get(i).getImg();
                            headDatas.add(new CourseOverviewInfo(title, description, imgUrl, _id));
                        }
                        int step = headDatas.size();
                        if (step == 0) {
                            CustomToast.toastMsgShort(getContext(), "没有了");
                        } else {
                            CustomToast.toastMsgShort(getContext(), "刷新了" + step + "条数据");
                            //offset += step; offset设置成定值0
                        }
                        //利用适配器将数据加至recycleList
                        listAdapter.AddHeaderItem(headDatas);
                        Log.i(Constants.TAG, "当前offset的值为：" + offset);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}