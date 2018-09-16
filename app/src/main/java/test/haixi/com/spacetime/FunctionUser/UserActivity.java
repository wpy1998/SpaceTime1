package test.haixi.com.spacetime.FunctionUser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import test.haixi.com.spacetime.BasicComponents.BasicActivity;
import test.haixi.com.spacetime.FunctionUser.Fragment.FragmentUser;
import test.haixi.com.spacetime.R;

public class UserActivity extends BasicActivity {
    String type;
    IntentFilter intentFilter;
    MyBroadcastReceiver myBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intentFront = getIntent();
        type = intentFront.getStringExtra("type");
        if (type.equals("fragmentUser")){
            replaceFragment(new FragmentUser());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.user_fragment, fragment);
        transaction.commit();
    }

    @Override
    public void finish() {
        if (type.equals("personMainPage")){
            if (fragmentNumber == 1){
                Intent intent = new Intent(intentActionFinish);
                sendBroadcast(intent);
            }else {
                super.finish();
            }
        }else {
            super.finish();
        }
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
        }
    }
}
