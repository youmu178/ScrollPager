package com.youmu.scrollpager.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youmu.scrollpager.transformer.RotateTransformer;

/**
 * Created by youzehong on 15/10/29.
 */
public abstract class BaseFragment extends Fragment implements RotateTransformer.OnRotateListener{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int viewId = onGetViewId();
        if (viewId > 0) {
            View layout = inflater.inflate(viewId, null, false);
            onViewCreate(layout);
            return layout;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected int onGetViewId() {
        return 0;
    }

    protected void onViewCreate(View view) {

    }
}
