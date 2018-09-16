package test.haixi.com.spacetime.FunctionWelcome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import test.haixi.com.spacetime.BasicComponents.BasicActivity;
import test.haixi.com.spacetime.FunctionWelcome.Fragment.FragmentGetTelephone;
import test.haixi.com.spacetime.FunctionWelcome.Fragment.FragmentVerificationCode;
import test.haixi.com.spacetime.FunctionWelcome.Fragment.LoginBeginFragment;
import test.haixi.com.spacetime.MainActivity;
import test.haixi.com.spacetime.R;

public class LoginActivity extends BasicActivity {
    IntentFilter intentFilter;
    MyBroadcastReceiver myBroadcastReceiver;
    private String intentActionNext = "wpy.example.com.goalproject.LoginActivity.next";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fragmentNumber = 0;

        myBroadcastReceiver = new MyBroadcastReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(intentActionFinish);
        intentFilter.addAction(intentActionNext);
        registerReceiver(myBroadcastReceiver, intentFilter);

        replaceFragment(new LoginBeginFragment(intentActionFinish, intentActionNext));
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

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.login_frameLayout, fragment);
        transaction.commit();
    }

    private class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(intentActionFinish)){
                if (fragmentNumber == 0){
                    finish();
                }else if ((fragmentNumber == 1)){
                    LoginBeginFragment loginBeginFragment = new LoginBeginFragment(intentActionFinish, intentActionNext);
                    replaceFragment(loginBeginFragment);
                    fragmentNumber--;
                }else if (fragmentNumber == 2){
                    FragmentGetTelephone fragmentGetTelephone = new FragmentGetTelephone(intentActionFinish, intentActionNext);
                    replaceFragment(fragmentGetTelephone);
                    fragmentNumber--;
                }
            }else if (action.equals(intentActionNext)){
                if (fragmentNumber == 0){
                    FragmentGetTelephone fragmentGetTelephone = new FragmentGetTelephone(intentActionFinish, intentActionNext);
                    replaceFragment(fragmentGetTelephone);
                    fragmentNumber++;
                }else if (fragmentNumber == 1){
                    FragmentVerificationCode fragmentVerificationCode = new FragmentVerificationCode(intentActionFinish,
                            intentActionNext);
                    replaceFragment(fragmentVerificationCode);
                    fragmentNumber++;
                }else {
                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent1);
                    replaceFragment(new LoginBeginFragment(intentActionFinish, intentActionNext));
                    fragmentNumber = 0;
                }
            }
        }
    }
}
