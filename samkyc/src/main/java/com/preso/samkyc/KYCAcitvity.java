package com.preso.samkyc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.preso.samkyc.fragments.OnBoardFirstFragment;
import com.preso.samkyc.fragments.OnBoardKycFrangment;
import com.preso.samkyc.fragments.OnBoardLastFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.viewpager2.widget.ViewPager2.SCROLL_STATE_DRAGGING;

public class KYCAcitvity extends AppCompatActivity {

    ViewPager2 viewPager;
    FragmentStateAdapter adapter;
    private static final int NUM_PAGES = 2;
    List<Fragment> list;

    //Firebase variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_y_c_acitvity);


        viewPager = findViewById(R.id.viewpager);
        //hide the toolbar if any tool bar is present
        getSupportActionBar().hide();

        // get window of acitvity
        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(getResources().getColor(R.color.app_color));


        // Intilise list & adapter and add fragment to list
        list = new ArrayList<>();
        list.add(new OnBoardFirstFragment(next -> {
            if (next) {
                if (viewPager != null) viewPager.setCurrentItem(1, true);
            }
        }));
        list.add(new OnBoardKycFrangment(next -> {
            if (next) {
                if (viewPager != null) {
                    viewPager.setCurrentItem(2, true);

                }
            }
        }));
        list.add(new OnBoardLastFragment(next -> {
            if (next) {
                if (viewPager != null) {
                    finish();
                    KYClient.getOnKycListener().onKycSuccess();
                }
            }
        }));
        adapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(adapter);



        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);

                if (state == SCROLL_STATE_DRAGGING && viewPager.getCurrentItem()  == 1) {
                    viewPager.setUserInputEnabled(true);
                } else {
                    viewPager.setUserInputEnabled(false);
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KYClient.getOnKycListener().onKycFailure();
    }

    // to slide onboarding fragment with the help of viewpager
    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
        }

        @Override
        public Fragment createFragment(int position) {
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}