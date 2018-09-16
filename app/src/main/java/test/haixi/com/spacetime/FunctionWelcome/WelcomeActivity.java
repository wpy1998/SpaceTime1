package test.haixi.com.spacetime.FunctionWelcome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import test.haixi.com.spacetime.BasicComponents.Adapter.FragmentAdapter;
import test.haixi.com.spacetime.BasicComponents.BasicActivity;
import test.haixi.com.spacetime.BasicComponents.Components.CustomViewPager;
import test.haixi.com.spacetime.FunctionWelcome.Fragment.FragmentFrame;
import test.haixi.com.spacetime.MainActivity;
import test.haixi.com.spacetime.R;

public class WelcomeActivity extends BasicActivity {
    CustomViewPager viewPager;
    List<Fragment> fragments;
    FragmentAdapter fragmentAdapter;
    MyBroadcastReceiver myBroadcastReceiver;
    IntentFilter intentFilter;

    String intentActionNext = "wpy.example.com.goalproject.WelcomeActivity.next";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        fragmentNumber = 0;

        initView();
        setAction();
    }

    private void initView() {
        intentFilter = new IntentFilter();
        myBroadcastReceiver = new MyBroadcastReceiver();
        intentFilter.addAction(intentActionFinish);
        intentFilter.addAction(intentActionNext);
        registerReceiver(myBroadcastReceiver, intentFilter);

        viewPager = findViewById(R.id.welcome_viewPager);
        fragments = new ArrayList<Fragment>();
        fragments.add(new FragmentFrame(0, intentActionNext));
        fragments.add(new FragmentFrame(1, intentActionNext));
        fragments.add(new FragmentFrame(2, intentActionNext));
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
    }

    private void setAction() {
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                fragmentNumber = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void finish() {
        if (fragmentNumber == 0){
            super.finish();
        }else {
            Intent intent = new Intent(intentActionFinish);
            sendBroadcast(intent);
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(intentActionFinish)){
                if (fragmentNumber == 0){
                    finish();
                }else if (fragmentNumber == 1){
                    viewPager.setCurrentItem(0, true);
                }else if (fragmentNumber == 2){
                    viewPager.setCurrentItem(1, true);
                }
            }else if (action.equals(intentActionNext)){
                if (fragmentNumber == 0){
                    viewPager.setCurrentItem(1, true);
                }else if (fragmentNumber == 1){
                    viewPager.setCurrentItem(2, true);
                }else if (fragmentNumber == 2){
                    Intent intent1 = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent1);
                    fragmentNumber = 0;
                    finish();
                }
            }
        }
    }
}
