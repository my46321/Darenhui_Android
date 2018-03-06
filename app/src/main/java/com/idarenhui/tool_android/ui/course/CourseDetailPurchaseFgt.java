package com.idarenhui.tool_android.ui.course;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.idarenhui.tool_android.R;
import com.idarenhui.tool_android.constant.Constants;
import com.idarenhui.tool_android.custom.ContactDialog;
import com.idarenhui.tool_android.model.gson.LessonTeamInfoJSON;
import com.idarenhui.tool_android.net.Api;
import com.idarenhui.tool_android.net.UserCallback;
import com.idarenhui.tool_android.ui.adapter.CoursePurchaseRecycleListAdapter;
import com.idarenhui.tool_android.ui.base.BaseFragment;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by chen on 2017/7/24.
 */

public class CourseDetailPurchaseFgt extends BaseFragment {
    @BindView(R.id.course_picture) ImageView coursePicture;
    @BindView(R.id.course_title) TextView courseTitle;
    @BindView(R.id.course_description) TextView courseDescription;
    @BindView(R.id.purchase_tips) TextView purchaseTips;
    @BindView(R.id.purchase_checkBt) TextView purchaseCheckBt;
    @BindView(R.id.list_time) RecyclerView listTime;
    @BindView(R.id.list_site) RecyclerView listSite;
    @BindView(R.id.list_duration) RecyclerView listDuration;
    @BindView(R.id.list_price) RecyclerView listPrice;
    @BindView(R.id.purchase_btn) Button purchaseBtn;

    private List<String> timeStrList, siteStrList, durationStrList, priceStrList;
    private Set<String> timeStrSet, siteStrSet, durationStrSet, priceStrSet;
    private CoursePurchaseRecycleListAdapter adapterTime, adapterSite, adapterDuration, adapterPrice;

    private String lesson_id= "5975634290c4905246ac743c";

    private List<LessonTeamInfoJSON.Data.TeamInfo> teamInfoList;

    //适配器中的stateItem列表的操作者
    private List<Integer> stateTime, stateSite, stateDuratiom, statePrice;
    private static final int STATE_SELECT = 0;//选中
    private static final int STATE_NOMAL = 1;//为选中但可选
    private static final int STATE_UNUSE = 2;//不可选

    private static final int TIME = 0;
    private static final int SITE = 1;
    private static final int DURATION = 2;
    private static final int PRICE = 3;

    @Override
    public void initViews(View view) {
        Log.i(Constants.TAG, "CourseDetailPurchaseFgt.initViews()");
        LinearLayoutManager linLytMng_time, linLytMng_site,linLytMng_duration,linLytMng_price;

        linLytMng_time = new LinearLayoutManager(getActivity());
        linLytMng_time.setOrientation(LinearLayoutManager.VERTICAL);
        //linearLayoutManager.scrollToPosition(0);
        linLytMng_site = new LinearLayoutManager(getActivity());
        linLytMng_site.setOrientation(LinearLayoutManager.VERTICAL);

        linLytMng_duration = new LinearLayoutManager(getActivity());
        linLytMng_duration.setOrientation(LinearLayoutManager.VERTICAL);

        linLytMng_price = new LinearLayoutManager(getActivity());
        linLytMng_price.setOrientation(LinearLayoutManager.VERTICAL);

        listTime.setLayoutManager(linLytMng_time);
        listSite.setLayoutManager(linLytMng_site);
        listDuration.setLayoutManager(linLytMng_duration);
        listPrice.setLayoutManager(linLytMng_price);

        timeStrList = new ArrayList<>();
        siteStrList = new ArrayList<>();
        durationStrList = new ArrayList<>();
        priceStrList = new ArrayList<>();

        adapterTime = new CoursePurchaseRecycleListAdapter(timeStrList, getContext());
        adapterSite = new CoursePurchaseRecycleListAdapter(siteStrList, getContext());
        adapterDuration = new CoursePurchaseRecycleListAdapter(durationStrList, getContext());
        adapterPrice = new CoursePurchaseRecycleListAdapter(priceStrList, getContext());

        setItemLisenter();

        listTime.setAdapter(adapterTime);
        listSite.setAdapter(adapterSite);
        listDuration.setAdapter(adapterDuration);
        listPrice.setAdapter(adapterPrice);
    }
    @Override
    public void initData() {
        timeStrSet = new HashSet<>();
        siteStrSet = new HashSet<>();
        durationStrSet = new HashSet<>();
        priceStrSet = new HashSet<>();
        loadData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.course_purchase;
    }

    private void loadData(){
        loadFromNet();
    }
    private void loadFromNet(){
        String url = Api.getLessonTeam;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("lesson_id", lesson_id)
                .addParams("pagesize", String.valueOf(100))
                .addParams("offset", String.valueOf(0))
                .build()
                .execute(new UserCallback<LessonTeamInfoJSON>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(Constants.TAG, getClass().getName() + " \nonError:" + e.getMessage());
                    }

                    @Override
                    public void onResponse(LessonTeamInfoJSON response, int id) {
                        Log.i(Constants.TAG, response.code);
                        teamInfoList = response.data.teams_info;
                        processData();
                    }
                });

    }
    private void processData(){
        if (teamInfoList == null){
            teamInfoList = new ArrayList<>();
        }
        for (int i = 0; i < teamInfoList.size(); i++) {
            //将课程列表中的信息取出，放入集合中（去重复）
            timeStrSet.add(teamInfoList.get(i).time);
            siteStrSet.add(teamInfoList.get(i).site);
            durationStrSet.add(teamInfoList.get(i).duration);
            priceStrSet.add(teamInfoList.get(i).price);
        }
        //将集合中的数据加载至存储信息的List中
        timeStrList.addAll(timeStrSet);
        siteStrList.addAll(siteStrSet);
        durationStrList.addAll(durationStrSet);
        priceStrList.addAll(priceStrSet);

        //初始化四个适配器中的 stateList 状态表
        adapterTime.initStateList();
        adapterSite.initStateList();
        adapterPrice.initStateList();
        adapterDuration.initStateList();

        //获取四个适配器中的 stateList 状态表操控权
        stateTime = adapterTime.getStateList();
        stateSite = adapterSite.getStateList();
        stateDuratiom = adapterPrice.getStateList();
        statePrice = adapterDuration.getStateList();
    }
    private void broadCast(int id, String key){
        Log.i(Constants.TAG, "broadCast->("+id +","+ key+")");
        switch (id){
            case TIME:
                for (int i = 0; i < teamInfoList.size(); i++) {
                    if (teamInfoList.get(i).time != key){
                        //该列下的数据的“可选”状态进行改变,改为不可选
                        String site = teamInfoList.get(i).site;
                        String price = teamInfoList.get(i).price;
                        String duration = teamInfoList.get(i).duration;
                        Log.i(Constants.TAG, teamInfoList.get(i).time +"不等于"+key+";其他信息为："+"site:"+site+"price:"+price+"dueation:"+duration);
                        for(int j=0; j < siteStrList.size();j++){
                            if (site == siteStrList.get(j)){
                                stateSite.set(j, STATE_UNUSE);
                            }
                        }
                        for(int j=0; j < priceStrList.size();j++){
                            if (price == priceStrList.get(j)){
                                statePrice.set(j, STATE_UNUSE);
                            }
                        }
                        for(int j=0; j < durationStrList.size();j++){
                            if (duration == durationStrList.get(j)){
                                stateDuratiom.set(j, STATE_UNUSE);
                            }
                        }
                    }
                }
                break;
            case SITE:
                for (int i = 0; i < teamInfoList.size(); i++) {
                    if (teamInfoList.get(i).site != key){
                        //该列下的数据的“可选”状态进行改变,改为不可选
                        String time = teamInfoList.get(i).time;
                        String price = teamInfoList.get(i).price;
                        String duration = teamInfoList.get(i).duration;
                        for(int j=0; j < timeStrList.size();j++){
                            if (time == timeStrList.get(j)){
                                stateTime.set(j, STATE_NOMAL);
                            }
                        }
                        for(int j=0; j < priceStrList.size();j++){
                            if (price == priceStrList.get(j)){
                                statePrice.set(j, STATE_NOMAL);
                            }
                        }
                        for(int j=0; j < durationStrList.size();j++){
                            if (duration == durationStrList.get(j)){
                                stateDuratiom.set(j, STATE_NOMAL);
                            }
                        }
                    }
                }
                break;
            case DURATION:
                for (int i = 0; i < teamInfoList.size(); i++) {
                    if (teamInfoList.get(i).site != key){
                        //该列下的数据的“可选”状态进行改变,改为不可选
                        String time = teamInfoList.get(i).time;
                        String price = teamInfoList.get(i).price;
                        String site = teamInfoList.get(i).site;
                        for(int j=0; j < timeStrList.size();j++){
                            if (time == timeStrList.get(j)){
                                stateTime.set(j, STATE_NOMAL);
                            }
                        }
                        for(int j=0; j < priceStrList.size();j++){
                            if (price == priceStrList.get(j)){
                                statePrice.set(j, STATE_NOMAL);
                            }
                        }
                        for(int j=0; j < siteStrList.size();j++){
                            if (site == siteStrList.get(j)){
                                stateSite.set(j, STATE_NOMAL);
                            }
                        }
                    }
                }
                break;
            case PRICE:
                for (int i = 0; i < teamInfoList.size(); i++) {
                    if (teamInfoList.get(i).site != key){
                        //该列下的数据的“可选”状态进行改变,改为不可选
                        String time = teamInfoList.get(i).time;
                        String duration = teamInfoList.get(i).duration;
                        String site = teamInfoList.get(i).site;
                        for(int j=0; j < timeStrList.size();j++){
                            if (time == timeStrList.get(j)){
                                stateTime.set(j, STATE_NOMAL);
                            }
                        }
                        for(int j=0; j < durationStrList.size();j++){
                            if (duration == durationStrList.get(j)){
                                stateDuratiom.set(j, STATE_NOMAL);
                            }
                        }
                        for(int j=0; j < siteStrList.size();j++){
                            if (site == siteStrList.get(j)){
                                stateSite.set(j, STATE_NOMAL);
                            }
                        }
                    }
                }
                break;
        }
    }
    private void broadCastClear(int id){
        Log.i(Constants.TAG, "broadCastClear->"+id);
        switch (id){
            case TIME:
                for (int i = 0; i < stateDuratiom.size(); i++) {
                    stateDuratiom.set(i, STATE_NOMAL);
                }
                for (int i = 0; i < stateSite.size(); i++) {
                    stateSite.set(i, STATE_NOMAL);
                }
                for (int i = 0; i < statePrice.size(); i++) {
                    statePrice.set(i, STATE_NOMAL);
                }
                break;
            case PRICE:
                for (int i = 0; i < stateDuratiom.size(); i++) {
                    stateDuratiom.set(i, STATE_NOMAL);
                }
                for (int i = 0; i < stateSite.size(); i++) {
                    stateSite.set(i, STATE_NOMAL);
                }
                for (int i = 0; i < stateTime.size(); i++) {
                    stateTime.set(i, STATE_NOMAL);
                }
                break;
            case DURATION:
                for (int i = 0; i < stateSite.size(); i++) {
                    stateSite.set(i, STATE_NOMAL);
                }
                for (int i = 0; i < statePrice.size(); i++) {
                    statePrice.set(i, STATE_NOMAL);
                }
                for (int i = 0; i < stateTime.size(); i++) {
                    stateTime.set(i, STATE_NOMAL);
                }
                break;
            case SITE:
                for (int i = 0; i < stateDuratiom.size(); i++) {
                    stateDuratiom.set(i, STATE_NOMAL);
                }
                for (int i = 0; i < statePrice.size(); i++) {
                    statePrice.set(i, STATE_NOMAL);
                }
                for (int i = 0; i < stateTime.size(); i++) {
                    stateTime.set(i, STATE_NOMAL);
                }
                break;
        }
    }
    private void setItemLisenter(){
        adapterTime.setOnItemClickLisitenter(new CoursePurchaseRecycleListAdapter.OnItemClickLisitenter() {
            @Override
            public void onItemClick(View v, int position, List<Integer> stateList) {
                Log.i(Constants.TAG, "点击了第"+ position +"个item; " + timeStrList.get(position));
                //stateTime = stateList;
                //先判断点击事件是否有效
                if (stateTime.get(position) == STATE_SELECT){
                    //nothing
                    Log.i(Constants.TAG, "该item:STATE_SELECT");
                }else if (stateTime.get(position) == STATE_NOMAL){
                    Log.i(Constants.TAG, "该item:STATE_NOMAL");
                    //先判断同组是否已经有选中了，如果有，先修改同组的颜色并将其它组所有全部恢复为可选状态
                    boolean hadSelect = false;
                    for (int i = 0; i < stateTime.size(); i++) {
                        if (stateTime.get(i) == STATE_SELECT){
                            hadSelect = true;
                            stateTime.set(i, STATE_NOMAL);
                        }
                    }
                    if (hadSelect){
                        //有选中
                        broadCastClear(TIME);
                    }else {
                        //没有选中的，点击选中，通知改变其它list中冲突item的颜色
                        broadCast(TIME ,timeStrList.get(position));
                    }

                    stateTime.set(position, STATE_SELECT);
                }else {
                    Log.i(Constants.TAG, "该item:STATE_UNUSE");
                    //nothing
                }
            }
        });


        adapterSite.setOnItemClickLisitenter(new CoursePurchaseRecycleListAdapter.OnItemClickLisitenter() {
            @Override
            public void onItemClick(View v, int position, List<Integer> stateList) {
                Log.i(Constants.TAG, "点击了第"+ position +"个item:" + siteStrList.get(position));
                //stateSite = stateList;
                //先判断点击事件是否有效
                if (stateSite.get(position) == STATE_SELECT){
                    Log.i(Constants.TAG, "该item:STATE_SELECT");
                    //nothing
                }else if (stateSite.get(position) == STATE_NOMAL){
                    Log.i(Constants.TAG, "该item:STATE_NOMAL");
                    //先判断同组是否已经有选中了，如果有，先修改同组的颜色并将其它组所有恢复可选状态\
                    boolean hadSelect = false;
                    for (int i = 0; i < stateSite.size(); i++) {
                        if (stateSite.get(i) == STATE_SELECT){
                            hadSelect = true;
                            stateSite.set(i, STATE_NOMAL);
                        }
                    }
                    if (hadSelect){
                        //有选中,
                        broadCastClear(SITE);
                    }else {
                        //没有选中的，点击选中，通知改变其它list中冲突item的颜色
                        broadCast(SITE ,siteStrList.get(position));
                    }

                    stateSite.set(position, STATE_SELECT);
                }else {
                    Log.i(Constants.TAG, "该item:STATE_UNUSE");
                    //nothing
                }
            }
        });

        adapterDuration.setOnItemClickLisitenter(new CoursePurchaseRecycleListAdapter.OnItemClickLisitenter() {
            @Override
            public void onItemClick(View v, int position, List<Integer> stateList) {
                Log.i(Constants.TAG, "点击了第"+ position +"个item");
                //stateDuratiom = stateList;
                //先判断点击事件是否有效
                if (stateDuratiom.get(position) == STATE_SELECT){
                    Log.i(Constants.TAG, "该item:STATE_SELECT");

                    //nothing
                }else if (stateDuratiom.get(position) == STATE_NOMAL){
                    Log.i(Constants.TAG, "该item:STATE_NOMAL");

                    //先判断同组是否已经有选中了，如果有，先修改同组的颜色并将其它组所有恢复可选状态\
                    boolean hadSelect = false;
                    for (int i = 0; i < stateDuratiom.size(); i++) {
                        if (stateDuratiom.get(i) == STATE_SELECT){
                            hadSelect = true;
                            stateDuratiom.set(i, STATE_NOMAL);
                        }
                    }
                    if (hadSelect){
                        //有选中,
                        broadCastClear(DURATION);
                    }else {
                        //没有选中的，点击选中，通知改变其它list中冲突item的颜色
                        broadCast(DURATION ,durationStrList.get(position));
                    }
                    stateDuratiom.set(position, STATE_SELECT);
                }else {
                    Log.i(Constants.TAG, "该item:STATE_UNUSE");

                    //nothing
                }
            }
        });
        adapterPrice.setOnItemClickLisitenter(new CoursePurchaseRecycleListAdapter.OnItemClickLisitenter() {
            @Override
            public void onItemClick(View v, int position, List<Integer> stateList) {
                Log.i(Constants.TAG, "点击了第"+ position +"个item");
                //statePrice = stateList;
                //先判断点击事件是否有效
                if (statePrice.get(position) == STATE_SELECT){
                    Log.i(Constants.TAG, "该item:STATE_SELECT");

                    //nothing
                }else if (statePrice.get(position) == STATE_NOMAL){
                    Log.i(Constants.TAG, "该item:STATE_NOMAL");

                    //先判断同组是否已经有选中了，如果有，先修改同组的颜色并将其它组所有恢复可选状态\
                    boolean hadSelect = false;
                    for (int i = 0; i < statePrice.size(); i++) {
                        if (statePrice.get(i) == STATE_SELECT){
                            hadSelect = true;
                            statePrice.set(i, STATE_NOMAL);
                        }
                    }
                    if (hadSelect){
                        //有选中,
                        broadCastClear(PRICE);
                    }else {
                        //没有选中的，点击选中，通知改变其它list中冲突item的颜色
                        broadCast(PRICE ,priceStrList.get(position));
                    }
                    statePrice.set(position, STATE_SELECT);
                }else {
                    Log.i(Constants.TAG, "该item:STATE_UNUSE");

                    //nothing
                }
            }
        });
    }
    @OnClick(R.id.purchase_btn)
    public void onViewClicked() {
        //支付界面
    }
}