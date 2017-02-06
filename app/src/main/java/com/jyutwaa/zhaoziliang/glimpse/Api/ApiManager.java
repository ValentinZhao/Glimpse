package com.jyutwaa.zhaoziliang.glimpse.Api;

import com.jyutwaa.zhaoziliang.glimpse.MyApplication;
import com.jyutwaa.zhaoziliang.glimpse.Utils.NetworkUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhaoziliang on 17/2/5.
 */

public class ApiManager {

    private static Interceptor OVERRIDE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if(NetworkUtils.isNetworkAvailable(MyApplication.getApplication())){
                int maxOnlineDuration = 60; //online cache could be reached within 1 minute
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxOnlineDuration)
                        .build();
            } else {
                int maxOfflineDuration = 60 * 60 * 24 * 7 * 4;//offline cache could be reached within 4 weeks
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache=Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxOfflineDuration)
                        .build();
            }
        }
    };

    public static ApiManager mApiManager;
    private Object monitor = new Object();
    private static File httpCacheDirectory = new File(MyApplication.getApplication().getCacheDir(), "zhihuCache");
    private static int cacheSize = 10 * 1024 * 1024;//10 MB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
    private static OkHttpClient client = new OkHttpClient().newBuilder()
            .addNetworkInterceptor(OVERRIDE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(OVERRIDE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();

    public static ApiManager getInstance(){
        if(mApiManager == null){
            synchronized(ApiManager.class){
                if(mApiManager == null){
                    mApiManager = new ApiManager();
                }
            }
        }

        return mApiManager;
    }

    public MainImageApi mMainImageApi;
    public MainImageApi getMainImageService(){
        if(mMainImageApi == null){
            synchronized (monitor){
                if(mMainImageApi == null){
                    mMainImageApi = new Retrofit.Builder()
                            .baseUrl("http://wpstatic.zuimeia.com/")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(MainImageApi.class);
                }
            }
        }
        return mMainImageApi;
    }
}
