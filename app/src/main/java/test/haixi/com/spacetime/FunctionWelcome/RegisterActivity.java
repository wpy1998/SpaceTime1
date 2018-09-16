package test.haixi.com.spacetime.FunctionWelcome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import test.haixi.com.spacetime.BasicComponents.BasicActivity;
import test.haixi.com.spacetime.FunctionWelcome.Fragment.FragmentGetTelephone;
import test.haixi.com.spacetime.FunctionWelcome.Fragment.FragmentResetPassword;
import test.haixi.com.spacetime.FunctionWelcome.Fragment.FragmentVerificationCode;
import test.haixi.com.spacetime.FunctionWelcome.Fragment.RegisterBeginFragment;
import test.haixi.com.spacetime.FunctionWelcome.Fragment.RegisterCompleteMessage;
import test.haixi.com.spacetime.MainActivity;
import test.haixi.com.spacetime.R;

public class RegisterActivity extends BasicActivity {
    IntentFilter intentFilter;
    MyBroadcastReceiver myBroadcastReceiver;
    private String intentActionNext = "wpy.example.com.RegisterActivity.LoginActivity.next";
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fragmentNumber = 0;

        intentFilter = new IntentFilter();
        myBroadcastReceiver = new MyBroadcastReceiver();
        intentFilter.addAction(intentActionFinish);
        intentFilter.addAction(intentActionNext);
        registerReceiver(myBroadcastReceiver, intentFilter);

        Intent intentFront = getIntent();
        type = intentFront.getStringExtra("message");
        if (type.equals("registerNewAccount")){
            replaceFragment(new RegisterBeginFragment(intentActionFinish, intentActionNext));
        }else if (type.equals("forgetPassword")){
            replaceFragment(new FragmentGetTelephone(intentActionFinish, intentActionNext));
        }else {
            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }

    @Override
    public void finish() {
        if (type.equals("registerNewAccount")){
            if (fragmentNumber == 0){
                super.finish();
            }else {
                Intent intent = new Intent(intentActionFinish);
                sendBroadcast(intent);
            }
        }else if (type.equals("forgetPassword")){
            if (fragmentNumber == 0){
                super.finish();
            }else {
                Intent intent = new Intent(intentActionFinish);
                sendBroadcast(intent);
            }
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(intentActionFinish)){
                if (type.equals("registerNewAccount")){
                    if (fragmentNumber == 1){
                        replaceFragment(new RegisterBeginFragment(intentActionFinish, intentActionNext));
                        fragmentNumber--;
                    }else if (fragmentNumber == 0){
                        finish();
                    }
                }else if (type.equals("forgetPassword")){
                    if (fragmentNumber == 0){
                        finish();
                    }else if (fragmentNumber == 1){
                        replaceFragment(new FragmentGetTelephone(intentActionFinish, intentActionNext));
                        fragmentNumber--;
                    }else if (fragmentNumber == 2){
                        replaceFragment(new FragmentVerificationCode(intentActionFinish, intentActionNext));
                        fragmentNumber--;
                    }
                }

            }else if (action.equals(intentActionNext)){
                if (type.equals("registerNewAccount")){
                    if (fragmentNumber == 0){
                        RegisterCompleteMessage registerCompleteMessage = new RegisterCompleteMessage(intentActionFinish,
                                intentActionNext);
                        replaceFragment(registerCompleteMessage);
                        String smsCode = intent.getStringExtra("smsCode");
                        String phoneNumber = intent.getStringExtra("phoneNumber");
                        String password = intent.getStringExtra("password");
                        registerCompleteMessage.initData(smsCode, phoneNumber, password);
                        fragmentNumber++;
                    }else if (fragmentNumber == 1){
                        fragmentNumber = 0;
                        finish();
                        Intent intent1 = new Intent(RegisterActivity.this, WelcomeActivity.class);
                        startActivity(intent1);
                    }
                }else if (type.equals("forgetPassword")){
                    if (fragmentNumber == 0){
                        FragmentVerificationCode fragmentVerificationCode = new FragmentVerificationCode(intentActionFinish,
                                intentActionNext);
                        replaceFragment(fragmentVerificationCode);
                        fragmentNumber++;
                    }else if (fragmentNumber == 1){
                        FragmentResetPassword fragmentResetPassword = new FragmentResetPassword(intentActionFinish, intentActionNext);
                        replaceFragment(fragmentResetPassword);
                        fragmentNumber++;
                    }else if (fragmentNumber == 2){
                        fragmentNumber = 0;
                        finish();
                        Intent intent1 = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent1);
                    }
                }
            }
        }
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.register_frameLayout, fragment);
        transaction.commit();
    }
}
