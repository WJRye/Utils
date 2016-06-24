package com.wj.utils.view;

import android.support.v4.view.ViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @Author：王江 on 2016/6/23 17:04
 * @Description:
 */
public class DisallowScrollViewPager extends ViewPager {

    private boolean mIsAllowScroll = true;

    public DisallowScrollViewPager(Context context) {
        super(context);
    }

    public DisallowScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!mIsAllowScroll) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setIsAllowScroll(boolean allowScroll) {
        this.mIsAllowScroll = allowScroll;
    }

}
