package com.jyutwaa.zhaoziliang.glimpse.Fragment.Bilibili;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jyutwaa.zhaoziliang.glimpse.Adapter.Bilibili.BilibiliMainViewPagerAdapter;
import com.jyutwaa.zhaoziliang.glimpse.Config.Config;
import com.jyutwaa.zhaoziliang.glimpse.Fragment.BaseFragment;
import com.jyutwaa.zhaoziliang.glimpse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoziliang on 17/2/25.
 */

public class BilibiliMainFragment extends BaseFragment{

    public Activity mActivity;
    public Context mContext;

    public ViewPager mViewPager;
    public TabLayout mTabLayout;

    private FragmentPagerAdapter mPagerAdapter;
    private List<Fragment> mFragments;

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewPager = (ViewPager) mView.findViewById(R.id.vp_bilibili_main);
        mTabLayout = (TabLayout) mView.findViewById(R.id.tl_bilibili_main);
        if(savedInstanceState == null){
            initViewAndData();
        } else {
            initViewAndData();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            initViewAndData();
        }
    }

    private void initViewAndData() {
        mFragments = new ArrayList<>();
        //TODO:Optimize fragments loading. We should not load all fragments to tablayout at first.
        mFragments.add(new IntegratedFragment());
        mFragments.add(new CoverFragment());
        mFragments.add(new EsportFragment());
        mFragments.add(new VarietyFragment());
        mFragments.add(new TvFragment());
        mFragments.add(new SerialFragment());
        mFragments.add(new MovieFragment());
        mFragments.add(new FunnySciFragment());
        mFragments.add(new OtakuDanceFragment());
        mFragments.add(new KichikuFragment());
        mPagerAdapter = new BilibiliMainViewPagerAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(mPagerAdapter);
        for(int i = 0; i < 10; i++){
            mTabLayout.addTab(mTabLayout.newTab().setText(Config.BILIBILI_TOPLIST_TYPES[i]));
        }
        mTabLayout.setupWithViewPager(mViewPager);
        for(int i = 0; i < 10; i++){
            mTabLayout.getTabAt(i).setText(Config.BILIBILI_TOPLIST_TYPES[i]);
        }
    }

    @Override
    protected int getLayoutIdentifier() {
        return R.layout.fragment_bilibili;
    }

    @Override
    protected void initWidgets() {
    }
}
