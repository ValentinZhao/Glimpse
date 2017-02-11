package com.jyutwaa.zhaoziliang.glimpse.Widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by zhaoziliang on 17/2/11.
 */

public class SwipeBackLayout extends FrameLayout {

    private int downX;
    private int downY;
    private int tempX;
    private Scroller mScroller;
    private int mTouchSlop;
    private int viewWidth;
    private View mContentView;
    private boolean isSliding;
    private boolean isFinish;

    private Activity mActivity;


    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void attachToActivity(Activity activity){
        mActivity = activity;
        TypedArray a = activity.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.windowBackground});
        int background = a.getResourceId(0, 0);
        a.recycle();
        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
        decor.removeView(decorChild);
        addView(decorChild);
        setContentView(decorChild);
        decor.addView(this);
    }

    private void setContentView(ViewGroup decorChild) {
        mContentView = (View) decorChild.getParent();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getRawX();
                tempX = (int) ev.getRawX();
                downY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
                if(moveX - downX > mTouchSlop
                        && Math.abs((int) ev.getRawY() - downY) < mTouchSlop){
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getRawX();
                int deltaX = moveX - tempX;
                tempX = moveX;
                if(moveX - downX > mTouchSlop
                        && Math.abs((int) event.getRawY() - downY) < mTouchSlop){
                    isSliding = true;
                }
                if(moveX - downX >= 0 && isSliding){
                    scrollBy(deltaX, 0);
                }
                break;
            case MotionEvent.ACTION_UP:
                isSliding = false;
                if(mContentView.getScrollX() > -viewWidth / 2){
                    isFinish = true;
                    scrollRightToDissmiss();
                } else {
                    isFinish = false;
                    scrollReturn();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    //we could obtain this layout's width and height both in onMeasure() and onLayout() methods
    //but the width and height we get in onLayout() are usually fixed, which will not change any more
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if(changed){
            viewWidth = this.getWidth();
        }
    }

    @Override
    public void computeScroll() {
        //mScroller.computeScrollOffset() is true when startScroll() is called
        if(mScroller.computeScrollOffset()){
            mContentView.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
            if(mScroller.isFinished() && isFinish){
                mActivity.finish();
            }
        }
    }

    //mContentView.getScrollX() is distance we have scrolled on screen
    private void scrollReturn() {
        int delta = mContentView.getScrollX();
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta, 0, Math.abs(delta));
        postInvalidate(); //refresh the view
    }

    private void scrollRightToDissmiss() {
        int delta = viewWidth + mContentView.getScrollX();
        mScroller.startScroll(mContentView.getScrollX(), 0, -delta + 1, 0, Math.abs(delta));
        postInvalidate(); //refresh the view
    }
}
