package com.youmu.scrollpager;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.youmu.scrollpager.adapter.TabPagerAdapter;
import com.youmu.scrollpager.fragments.BaseFragment;
import com.youmu.scrollpager.fragments.OneFragment;
import com.youmu.scrollpager.fragments.ThreeFragment;
import com.youmu.scrollpager.fragments.TwoFragment;
import com.youmu.scrollpager.transformer.RotateTransformer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<BaseFragment> mFragmentList = new ArrayList<>();
    private ViewPager mScrollPagerLayout;
    private TabPagerAdapter mScrollPagerAdapter;
    private RotateTransformer rotateTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentList.add(new OneFragment());
        mFragmentList.add(new TwoFragment());
        mFragmentList.add(new ThreeFragment());

        mScrollPagerLayout = (ViewPager) findViewById(R.id.scrollPagerLayout);

        mScrollPagerLayout.setPageMargin(-100);
        mScrollPagerLayout.setOffscreenPageLimit(mFragmentList.size());
        mScrollPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), mFragmentList, new String[]{"月账户", "季账户", "年账户"});
        mScrollPagerLayout.setAdapter(mScrollPagerAdapter);
        mScrollPagerLayout.setCurrentItem(1);
        rotateTransformer = new RotateTransformer(mScrollPagerLayout);
        mScrollPagerLayout.setPageTransformer(true, rotateTransformer);

        rotateTransformer.setOnRotateListener(new RotateTransformer.OnRotateListener() {
            @Override
            public void onRotateNotify() {
                for (BaseFragment fragment :mFragmentList) {
                    fragment.onRotateNotify();
                }
            }

            @Override
            public void onRotateReset() {
                for (BaseFragment fragment :mFragmentList) {
                    fragment.onRotateReset();
                }
            }
        });
    }

}
