package com.ishuyin.gecollsn.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * @author gecollsn
 * @create 5/12/2016
 * @company www.ishuyin.com
 */
public abstract class BaseFragment extends Fragment implements IDefault {
    protected Activity mActivity;
    protected View mRoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mRoot == null) {
            mRoot = inflater.inflate(definedLayoutId(), container, false);
            ButterKnife.bind(this, mRoot);
            doInitEverything();
        }
        return mRoot;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = this.getActivity();
    }

    protected abstract int definedLayoutId();

    protected abstract void doInitEverything();

    protected LayoutInflater layoutInflater(){
        return LayoutInflater.from(getActivity());
    }
}
