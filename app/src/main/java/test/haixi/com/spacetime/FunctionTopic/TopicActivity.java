package test.haixi.com.spacetime.FunctionTopic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import test.haixi.com.spacetime.BasicComponents.BasicActivity;
import test.haixi.com.spacetime.FunctionTopic.Fragment.FragmentTopicChat;
import test.haixi.com.spacetime.FunctionTopic.Fragment.FragmentTopicIntroduction;
import test.haixi.com.spacetime.FunctionTopic.Fragment.FragmentTopicUserMainPage;
import test.haixi.com.spacetime.R;

public class TopicActivity extends BasicActivity {
    String intentActionFinish = "test.haixi.com.functiontopic.TopicActivity.finish",
            intentActionNext = "test.haixi.com.functiontopic.TopicActivity.next";
    int fragmentNumber = 0;

    IntentFilter intentFilter;
    MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        intentFilter = new IntentFilter();
        myBroadcastReceiver = new MyBroadcastReceiver();
        intentFilter.addAction(intentActionFinish);
        intentFilter.addAction(intentActionNext);
        registerReceiver(myBroadcastReceiver, intentFilter);

        fragmentNumber = 0;
        replaceFragment(new FragmentTopicIntroduction(intentActionFinish, intentActionNext));
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_change_animation, 0, 0, 0);
        transaction.replace(R.id.topic_frameLayout,fragment);
        transaction.commit();
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
                }if (fragmentNumber == 1){
                    replaceFragment(new FragmentTopicIntroduction(intentActionFinish, intentActionNext));
                    fragmentNumber--;
                }else if (fragmentNumber == 2){
                    replaceFragment(new FragmentTopicUserMainPage(intentActionFinish, intentActionNext));
                    fragmentNumber--;
                } else if (fragmentNumber == 3){
                    replaceFragment(new FragmentTopicChat(intentActionFinish, intentActionNext));
                    fragmentNumber--;
                }
            }else if (action.equals(intentActionNext)){
                if (fragmentNumber == 0){
                    replaceFragment(new FragmentTopicUserMainPage(intentActionFinish, intentActionNext));
                    fragmentNumber++;
                }else if (fragmentNumber == 1){
                    replaceFragment(new FragmentTopicChat(intentActionFinish, intentActionNext));
                    fragmentNumber++;
                }else if(fragmentNumber == 2){
                }
            }
        }
    }
}
