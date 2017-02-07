package com.jyutwaa.zhaoziliang.glimpse;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyutwaa.zhaoziliang.glimpse.Activity.BaseActivity;
import com.jyutwaa.zhaoziliang.glimpse.Fragment.ZhihuFragment;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl.IMainPresenterImpl;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IMain;
import com.jyutwaa.zhaoziliang.glimpse.Utils.SharedPreferenceUtils;

public class MainActivity extends BaseActivity implements IMain{

    private FrameLayout fl_fragContainer;
    private Toolbar toolBar;
    private NavigationView navigationView;
    private RelativeLayout rl_navi_header;
    private DrawerLayout drawer;

    private IMainPresenterImpl mainPresenter;
    private SimpleArrayMap<Integer, String> mTitleMap;
    private int navigationId;
    private MenuItem currentMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        mainPresenter = new IMainPresenterImpl(this, this);
        mTitleMap = new SimpleArrayMap<>();
        mainPresenter.getBackground();
        setSupportActionBar(toolBar);
        toolBar.setOnMenuItemClickListener(onMenuItemClickListener);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            animateToolbar();
        }
        initFragemntsTitleMap();
        drawer.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        if(savedInstanceState == null){
            navigationId = SharedPreferenceUtils.getNavigationMenuItemId(this);
            if(navigationId != -1){
                currentMenuItem = navigationView.getMenu().findItem(navigationId);
            }
            if(currentMenuItem == null){
                currentMenuItem = navigationView.getMenu().findItem(R.id.zhihu);
            }
            if(currentMenuItem != null){
                currentMenuItem.setChecked(true);
                Fragment fragment = findFragmentById(currentMenuItem.getItemId());
                String title = mTitleMap.get(currentMenuItem.getItemId());
                if(fragment != null){
                    switchFragment(title, fragment);
                }
            }
        }

    }

    private void switchFragment(String title, Fragment fragment) {

    }

    private Fragment findFragmentById(int itemId) {
        Fragment fragment = null;
        switch(itemId){
            case R.id.zhihu:
                fragment = new ZhihuFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    private void initFragemntsTitleMap() {
        mTitleMap.put(R.id.zhihu, "知乎日报");
    }

    private void animateToolbar() {
        // this is gross but toolbar doesn't expose it's children to animate them :(
        View t = toolBar.getChildAt(0);
        if (t != null && t instanceof TextView) {
            TextView title = (TextView) t;

            // fade in and space out the title.  Animating the letterSpacing performs horribly so
            // fake it by setting the desired letterSpacing then animating the scaleX ¯\_(ツ)_/¯
            title.setAlpha(0f);
            title.setScaleX(0.8f);

            title.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setStartDelay(500)
                    .setDuration(900)
                    .setInterpolator(new LinearInterpolator()).start();
        }
        View amv = toolBar.getChildAt(1);
        if (amv != null & amv instanceof ActionMenuView) {
            ActionMenuView actions = (ActionMenuView) amv;
            popAnim(actions.getChildAt(0), 500, 200); // filter
            popAnim(actions.getChildAt(1), 700, 200); // overflow
        }
    }

    private void popAnim(View v, int startDelay, int duration) {
        if (v != null) {
            v.setAlpha(0f);
            v.setScaleX(0f);
            v.setScaleY(0f);

            v.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(startDelay)
                    .setDuration(duration)
                    .setInterpolator(AnimationUtils.loadInterpolator(this,
                            android.R.interpolator.overshoot)).start();
        }
    }

    private void initWidgets() {
        fl_fragContainer = (FrameLayout) findViewById(R.id.fragment_container);
        toolBar = (Toolbar) findViewById(R.id.home_toolbar);
        navigationView = (NavigationView) findViewById(R.id.navi_view);
        rl_navi_header = (RelativeLayout) findViewById(R.id.navi_header);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch(item.getItemId()){
                case R.id.zhihu:
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    @Override
    public void getPic() {

    }
}
