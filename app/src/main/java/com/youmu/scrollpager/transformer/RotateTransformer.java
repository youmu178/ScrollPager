package com.youmu.scrollpager.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by youzehong on 16/8/29.
 */
public class RotateTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.65f;
    private float desgress;
    private int executeNum = 0;

    private OnRotateListener onRotateListener;
    private ViewPager viewPager;

    public RotateTransformer(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void transformPage(View view, float position) {
        int width = view.getWidth();
        int height = view.getHeight();
        // 根ViewPager的Child总数决定执行次数
        if (executeNum < viewPager.getChildCount()) {
            if (position == 0f) {
                desgress = 0f;
            } else {
                // 旋转360度，不同分辨率下初次position是不同的，以此来确定一个固定的最终的度数
                desgress = 360f / position;
            }
        }

        float rotation = desgress * position;

        if (position < -1) { // [- Infinity, -1)
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        } else if (position <= 1) { // [-1, 1]
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1, +Infinity]
            view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        }
        if (rotation >= 90 && rotation <= 270 || rotation <= -90 && rotation >= -270) {
            if (onRotateListener != null) {
                onRotateListener.onRotateNotify();
            }
            // 为防止字体颠倒，强制变换了角度
            rotation = 270 + rotation - 90;
        } else if (rotation > 270 && rotation <= 360 || rotation < -270 && rotation >= -360) {
            if (onRotateListener != null) {
                onRotateListener.onRotateReset();
            }
        }
        view.setPivotX(width * 0.5f);
        view.setPivotY(height * 0.5f);
        view.setRotationY(rotation);
        ++executeNum;
    }

    public void setOnRotateListener(OnRotateListener onRotateListener) {
        this.onRotateListener = onRotateListener;
    }

    public interface OnRotateListener {
        void onRotateNotify();

        void onRotateReset();
    }
}
