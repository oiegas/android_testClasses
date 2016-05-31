package com.example.lenovo.testclasses;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by AlexandruOi on 5/8/2016.
 */
public class NonSwipebaleViewPager extends ViewPager {
    public NonSwipebaleViewPager(Context context) {
        super(context);
    }

    public NonSwipebaleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }
}
