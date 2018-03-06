package com.idarenhui.tool_android.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ericrobert on 2017/7/16.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    private Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = getActivity();
        View view=inflater.inflate(getLayoutId(),container,false);
        mUnbinder = ButterKnife.bind(this,view);
        initViews(view);
        initData();
        return view;
    }


    public abstract int getLayoutId();
    public abstract void initViews(View view);
    public abstract void initData();

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    protected void showToast(String msg) {
        View view = getView();
        if (view != null && !TextUtils.isEmpty(msg)) {
            Snackbar.make(getView(), msg, Snackbar.LENGTH_SHORT).show();
        }
    }


    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void jumpTo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
    /**
     * startActivity
     *
     * @param clazz
     */
    protected void jumpTo(Class<?> clazz) {
        Intent intent = new Intent(getActivity(), clazz);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
