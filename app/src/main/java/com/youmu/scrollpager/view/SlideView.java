package com.youmu.scrollpager.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by youzehong on 15/10/23.
 */
public class SlideView extends ViewGroup {

    private int mScreenHeiht;
    private int mLastY;
    private int mStart;
    private int mEnd;
    private Scroller mScroller;

    public SlideView(Context context) {
        this(context, null);
    }

    public SlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mScreenHeiht = wm.getDefaultDisplay().getHeight();
        mScroller = new Scroller(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
        marginLayoutParams.height = mScreenHeiht * childCount;
        setLayoutParams(marginLayoutParams);
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                childView.layout(l, i * mScreenHeiht, r, (i + 1) * mScreenHeiht);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                int moveY = mLastY - y;
                if (getScrollY() < 0) {
                    moveY = 0;
                }
                if (getScrollY() > getHeight() - mScreenHeiht) {
                    moveY = 0;
                }
                scrollBy(0, moveY);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mEnd = getScrollY();
                int scrollY = mEnd - mStart;
                if (scrollY > 0) { //下滑
                    if (scrollY < mScreenHeiht / 5) {
                        mScroller.startScroll(0, getScrollY(), 0, -scrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeiht - scrollY);
                    }
                } else { // 上滑
                    if (-scrollY < mScreenHeiht / 5) {
                        mScroller.startScroll(0, getScrollY(), 0, -scrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeiht -scrollY);
                    }

                }
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }
}
