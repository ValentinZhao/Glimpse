package com.jyutwaa.zhaoziliang.glimpse.Presenter.presenterImpl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jyutwaa.zhaoziliang.glimpse.Api.ApiManager;
import com.jyutwaa.zhaoziliang.glimpse.Model.MainBgImage.ImageResponse;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.IMainPresenter;
import com.jyutwaa.zhaoziliang.glimpse.Presenter.viewImpl.IMain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhaoziliang on 17/2/4.
 */

public class IMainPresenterImpl extends BasePresenterImpl implements IMainPresenter{

    private IMain mIMain;
    private Context mContext;

    public IMainPresenterImpl(IMain mIMain, Context mContext) {
        this.mIMain = mIMain;
        this.mContext = mContext;
    }

    @Override
    public void getBackground() {
        ApiManager.getInstance().getMainImageService().getImage()
                .subscribeOn(Schedulers.io())
                .map(new Func1<ImageResponse, Boolean>() {
                    @Override
                    public Boolean call(ImageResponse imageResponse) {
                        if(imageResponse.getImageData() != null && imageResponse.getImageData().getImages() != null){
                            try {
                                Bitmap bitmap = BitmapFactory.decodeStream(new URL(imageResponse.getImageData().getBase_url()
                                    + imageResponse.getImageData().getImages().get(0).getImage_url()
                                    + "?imageMogr/v2/auto-orient/thumbnail/480x320/quality/100")
                                    .openConnection()
                                    .getInputStream());
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                                        new FileOutputStream(new File(mContext.getFilesDir().getPath() + "/bg.jpg")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
    }
}
