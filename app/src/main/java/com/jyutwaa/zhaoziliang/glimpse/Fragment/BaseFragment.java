package com.jyutwaa.zhaoziliang.glimpse.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhaoziliang on 17/2/7.
 */

public abstract class BaseFragment extends Fragment {

    public View mView;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setRetainInstance(true);
        mView = View.inflate(getContext(), getLayoutIdentifier(), null);
        initWidgets();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFragmentTransaction.hide(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentTransaction.show(this);
    }

    protected abstract int getLayoutIdentifier();
    protected abstract void initWidgets();
}
