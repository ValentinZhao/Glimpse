package com.jyutwaa.zhaoziliang.glimpse.Activity.Zhihu;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.jyutwaa.zhaoziliang.glimpse.Activity.BaseDetailPageActivity;
import com.jyutwaa.zhaoziliang.glimpse.Config.Config;
import com.jyutwaa.zhaoziliang.glimpse.Model.Zhihu.ZhihuDetailPage;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl.IZhihuDetailPagePresenterImpl;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IZhihuDetailPage;
import com.jyutwaa.zhaoziliang.glimpse.R;
import com.jyutwaa.zhaoziliang.glimpse.Utils.DeviceUtils;
import com.jyutwaa.zhaoziliang.glimpse.Utils.ThirdPartyUtils.ColorUtils;
import com.jyutwaa.zhaoziliang.glimpse.Utils.ThirdPartyUtils.GlideUtils;
import com.jyutwaa.zhaoziliang.glimpse.Utils.ThirdPartyUtils.ViewUtils;
import com.jyutwaa.zhaoziliang.glimpse.Utils.WebUtils;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.ParallaxScrimageView;
import com.jyutwaa.zhaoziliang.glimpse.Widgets.TranslateYTextView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaoziliang on 17/2/11.
 */

public class ZhihuDetailPageActivity extends BaseDetailPageActivity implements IZhihuDetailPage{

    private static final float SCRIM_ADJUSTMENT = 0.075f;

    private List<String> list = new ArrayList<>();
    private String id;
    private String title;
    private int mWidth;
    private int mHeight;
    private int[] mDeviceInfos;
    private IZhihuDetailPagePresenterImpl mIZhihuDetailPagePresenterImpl;

    private ParallaxScrimageView mShot;
    private TranslateYTextView mTitle;
    private Toolbar mToolbar;
    private NestedScrollView mNestedView;
    private WebView mWebView;
    private NestedScrollView.OnScrollChangeListener mScrollChangeListener;
    private String mBody;
    private String[] mCss;
    private boolean isEmpty;
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihu_detail_layout);
        getWindow().setStatusBarColor(getResources().getColor(R.color.immersive_bars));
        mDeviceInfos = DeviceUtils.getDeviceInfos(this);
        mWidth = mDeviceInfos[0];
        mHeight = mDeviceInfos[1];
        initWidgets();
        initData();
        initViews();
        getData();
    }

    private void getData() {
        mIZhihuDetailPagePresenterImpl.getDetailPage(id);
    }

    private void initData() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        mIZhihuDetailPagePresenterImpl = new IZhihuDetailPagePresenterImpl(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            postponeEnterTransition();
            mShot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    mShot.getViewTreeObserver().removeOnPreDrawListener(this);
                    startPostponedEnterTransition();
                    return true;
                }
            });
        }
        mScrollChangeListener = new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(oldScrollY < 168){
                    mShot.setOffset(-oldScrollY);
                    mTitle.setOffset(-oldScrollY);
                }
            }
        };
        mNestedView.setOnScrollChangeListener(mScrollChangeListener);
    }

    private void initViews() {
        mToolbar.setTitleMargin(20, 20, 0, 10);
        mToolbar.setTitle(title);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNestedView.smoothScrollTo(0, 0);
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ZhihuDetailPageActivity.this.onBackPressed();
            }
        });

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    private void initWidgets() {
        mShot = (ParallaxScrimageView) findViewById(R.id.zhihu_detail_shot);
        mTitle = (TranslateYTextView) findViewById(R.id.zhihu_detail_title);
        mToolbar = (Toolbar) findViewById(R.id.zhihu_detail_toolbar);
        mNestedView = (NestedScrollView) findViewById(R.id.zhihu_detail_nest);
        mWebView = (WebView) findViewById(R.id.zhihu_detail_web);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            mWebView.getClass().getMethod("onPause").invoke(mWebView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mWebView.getClass().getMethod("onResume").invoke(mWebView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if(mWebView != null){
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        mIZhihuDetailPagePresenterImpl.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void showError(String error) {
        Snackbar.make(mWebView, "网络错误,请重试", Snackbar.LENGTH_SHORT).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        }).show();
    }

    @Override
    public void showDetailPage(ZhihuDetailPage zhihuDetailPage) {
        Glide.with(this)
                .load(zhihuDetailPage.getImage()).centerCrop()
                .listener(loadListener).override(mWidth, mHeight)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mShot);
        isEmpty = TextUtils.isEmpty(zhihuDetailPage.getBody());//isEmpty is true on first load. Never be true in following requests.
        mBody = zhihuDetailPage.getBody();
        mCss = zhihuDetailPage.getCss();
        mUrl = zhihuDetailPage.getShare_url();
        //optimalization: first load from the Internet, then we save the html+css. Webview loads the
        //html+css from disk cache after that.
        if(isEmpty){
            mWebView.loadUrl(mUrl);
        } else {
            String data = WebUtils.buildHtmlWithCss(mBody, mCss, Config.isNight);
            mWebView.loadDataWithBaseURL(WebUtils.BASE_URL, data, WebUtils.MIME_TYPE, WebUtils.ENCODING, WebUtils.FAIL_URL);
        }
    }

    private RequestListener loadListener = new RequestListener<String, GlideDrawable>() {
        @Override
        public boolean onResourceReady(GlideDrawable resource, String model,
                                       Target<GlideDrawable> target, boolean isFromMemoryCache,
                                       boolean isFirstResource) {
            final Bitmap bitmap = GlideUtils.getBitmap(resource);
            final int twentyFourDip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    24, ZhihuDetailPageActivity.this.getResources().getDisplayMetrics());
            Palette.from(bitmap)
                    .maximumColorCount(3)
                    .clearFilters() /* by default palette ignore certain hues
                        (e.g. pure black/white) but we don't want this. */
                    .setRegion(0, 0, bitmap.getWidth() - 1, twentyFourDip) /* - 1 to work around
                        https://code.google.com/p/android/issues/detail?id=191013 */
                    .generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {
                            boolean isDark;
                            @ColorUtils.Lightness int lightness = ColorUtils.isDark(palette);
                            if (lightness == ColorUtils.LIGHTNESS_UNKNOWN) {
                                isDark = ColorUtils.isDark(bitmap, bitmap.getWidth() / 2, 0);
                            } else {
                                isDark = lightness == ColorUtils.IS_DARK;
                            }

                            // color the status bar. Set a complementary dark color on L,
                            // light or dark color on M (with matching status bar icons)
                            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){


                                int statusBarColor = getWindow().getStatusBarColor();
                                final Palette.Swatch topColor =
                                        ColorUtils.getMostPopulousSwatch(palette);
                                if (topColor != null &&
                                        (isDark || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                                    statusBarColor = ColorUtils.scrimify(topColor.getRgb(),
                                            isDark, SCRIM_ADJUSTMENT);
                                    // set a light status bar on M+
                                    if (!isDark && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        ViewUtils.setLightStatusBar(mShot);
                                    }
                                }

                                if (statusBarColor != getWindow().getStatusBarColor()) {
                                    mShot.setScrimColor(statusBarColor);
                                    ValueAnimator statusBarColorAnim = ValueAnimator.ofArgb(
                                            getWindow().getStatusBarColor(), statusBarColor);
                                    statusBarColorAnim.addUpdateListener(new ValueAnimator
                                            .AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator animation) {
                                            getWindow().setStatusBarColor(
                                                    (int) animation.getAnimatedValue());
                                        }
                                    });
                                    statusBarColorAnim.setDuration(1000L);
                                    statusBarColorAnim.setInterpolator(
                                            new AccelerateInterpolator());
                                    statusBarColorAnim.start();
                                }
                            }

                        }
                    });


            Palette.from(bitmap)
                    .clearFilters()
                    .generate(new Palette.PaletteAsyncListener() {
                        @Override
                        public void onGenerated(Palette palette) {

                            // slightly more opaque ripple on the pinned image to compensate
                            // for the scrim
                            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){

                                mShot.setForeground(ViewUtils.createRipple(palette, 0.3f, 0.6f,
                                        ContextCompat.getColor(ZhihuDetailPageActivity.this, R.color.mid_grey),
                                        true));
                            }
                        }
                    });

            // TODO should keep the background if the image contains transparency?!
            mShot.setBackground(null);
            return false;
        }

        @Override
        public boolean onException(Exception e, String model, Target<GlideDrawable> target,
                                   boolean isFirstResource) {
            return false;
        }
    };

}
