package com.jyutwaa.zhaoziliang.glimpse;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jyutwaa.zhaoziliang.glimpse.Activity.BaseActivity;
import com.jyutwaa.zhaoziliang.glimpse.Fragment.Zhihu.ZhihuFragment;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl.IMainPresenterImpl;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IMain;
import com.jyutwaa.zhaoziliang.glimpse.Utils.SharedPreferenceUtils;
import com.jyutwaa.zhaoziliang.glimpse.Utils.ViewUtils;

import java.io.File;

public class MainActivity extends BaseActivity implements IMain{

    private FrameLayout fl_fragContainer;
    private Toolbar toolBar;
    private NavigationView navigationView;
    private RelativeLayout rl_navi_header;
    private DrawerLayout drawer;
    private SwitchCompat sc_theme;

    private IMainPresenterImpl mainPresenter;
    private SimpleArrayMap<Integer, String> mTitleMap;
    private int navigationId;
    private MenuItem currentMenuItem;
    private Fragment currentFragment;

    private int [][] state = new int[][]{
            new int[] {-android.R.attr.checked},
            new int[] {android.R.attr.checked}
    };
    private int [] textColor = new int[]{Color.BLACK, Color.BLACK};
    private int [] iconColor = new int[]{Color.GRAY, Color.BLACK};
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        setThemeColor(getResources().getColor(R.color.immersive_bars));
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
                currentMenuItem.setChecked(false);
                Fragment fragment = findFragmentById(currentMenuItem.getItemId());
                String title = mTitleMap.get(currentMenuItem.getItemId());
                if(fragment != null){
                    switchFragment(title, fragment);
                }
            }
        } else {
            if(currentMenuItem != null){
                Fragment fragment = findFragmentById(currentMenuItem.getItemId());
                String title = mTitleMap.get(currentMenuItem.getItemId());
                if(fragment != null){
                    switchFragment(title, fragment);
                }
            } else {
                switchFragment(" ", new ZhihuFragment());
                currentMenuItem = navigationView.getMenu().findItem(R.id.zhihu);
            }
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if(currentMenuItem != null && currentMenuItem != item){
                    currentMenuItem.setChecked(false);
                    SharedPreferenceUtils.setNaivigationMenuItemId(MainActivity.this, currentMenuItem.getItemId());
                    currentMenuItem = item;
                    currentMenuItem.setChecked(true);
                    switchFragment(mTitleMap.get(currentMenuItem.getItemId()), findFragmentById(R.id.zhihu));
                }
                drawer.closeDrawer(GravityCompat.END);
                return true;
            }
        });
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {

            drawer.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                @Override
                public WindowInsets onApplyWindowInsets(View v, WindowInsets insets) {
                    // inset the toolbar down by the status bar height
                    ViewGroup.MarginLayoutParams lpToolbar = (ViewGroup.MarginLayoutParams) toolBar
                            .getLayoutParams();
                    lpToolbar.topMargin += insets.getSystemWindowInsetTop();
                    lpToolbar.rightMargin += insets.getSystemWindowInsetRight();
                    toolBar.setLayoutParams(lpToolbar);

                    // inset the grid top by statusbar+toolbar & the bottom by the navbar (don't clip)
                    fl_fragContainer.setPadding(fl_fragContainer.getPaddingLeft(),
                            insets.getSystemWindowInsetTop() + ViewUtils
                                    .getActionBarSize(MainActivity.this),
                            fl_fragContainer.getPaddingRight() + insets.getSystemWindowInsetRight(), // landscape
                            fl_fragContainer.getPaddingBottom() + insets.getSystemWindowInsetBottom());

                    // we place a background behind the status bar to combine with it's semi-transparent
                    // color to get the desired appearance.  Set it's height to the status bar height
                    View statusBarBackground = findViewById(R.id.status_bar_bg_color);
                    FrameLayout.LayoutParams lpStatus = (FrameLayout.LayoutParams)
                            statusBarBackground.getLayoutParams();
                    lpStatus.height = insets.getSystemWindowInsetTop();
                    statusBarBackground.setLayoutParams(lpStatus);

                    // inset the filters list for the status bar / navbar
                    // need to set the padding end for landscape case

                    // clear this listener so insets aren't re-applied
                    drawer.setOnApplyWindowInsetsListener(null);
                    return insets.consumeSystemWindowInsets();
                }
            });
        }

        navigationView.setItemTextColor(new ColorStateList(state, textColor));
        navigationView.setItemIconTintList(null);
        changeThemes();
    }

    private void changeThemes() {
        MenuItem item = navigationView.getMenu().findItem(R.id.theme_setting);
        sc_theme = (SwitchCompat) MenuItemCompat.getActionView(item).findViewById(R.id.sc_theme);
        sc_theme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sc_theme.setChecked(isChecked);
                if(isChecked){
                    setThemeColor(Color.MAGENTA);
                } else {
                    setThemeColor(getResources().getColor(R.color.status_bar_color));
                }
            }
        });
    }

    private void setThemeColor(int colorId) {
        getWindow().setStatusBarColor(colorId);
        toolBar.setBackgroundColor(colorId);
    }

    private void switchFragment(String title, Fragment fragment) {
        if(currentFragment == null || !currentFragment.getClass().getName().equals(fragment.getClass().getName())){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            currentFragment = fragment;
        }
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
                case R.id.menu_open:
                    drawer.openDrawer(GravityCompat.END);
                    break;
                case R.id.menu_about:
                    Toast.makeText(MainActivity.this, "进入作者介绍页面!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    @Override
    public void getPic() {
        View headerView = navigationView.getHeaderView(0);
        RelativeLayout rl_image = (RelativeLayout) headerView.findViewById(R.id.navi_header);
        if(new File(getFilesDir().getPath() + "/bg.jpg").exists()){
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), getFilesDir().getPath() + "/bg.jpg");
            rl_image.setBackground(bitmapDrawable);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        } else {
            if(System.currentTimeMillis() - exitTime > 2000){
                Toast.makeText(this, "再点一次以退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public interface LoadingMore {

        void loadingStart();

        void loadingfinish();
    }
}
