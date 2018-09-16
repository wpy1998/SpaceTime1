package test.haixi.com.spacetime.FunctionWelcome.Fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import test.haixi.com.spacetime.BasicComponents.BasicActivity;
import test.haixi.com.spacetime.BasicComponents.OkHttpAction;
import test.haixi.com.spacetime.R;

import static test.haixi.com.spacetime.BasicComponents.BasicActivity.token;

@SuppressLint("ValidFragment")
public class FragmentResetPassword extends Fragment{
    EditText password;
    Button nextPage;
    IntentFilter intentFilter;
    HttpBroadcastReceiver httpBroadcastReceiver;

    String intentActionFinish, intentActionNext,
            intentHttpResetPassword = "wpy.example.com.goalproject.Fragment.FragmentResetPassword.resetPassword";

    public FragmentResetPassword(String intentActionFinish, String intentActionNext){
        this.intentActionFinish = intentActionFinish;
        this.intentActionNext = intentActionNext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        initView(view);
        setAction();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(httpBroadcastReceiver);
    }

    private void initView(View view) {
        password = view.findViewById(R.id.reset_password_password);
        nextPage = view.findViewById(R.id.reset_password_nextPage);

        intentFilter = new IntentFilter();
        httpBroadcastReceiver = new HttpBroadcastReceiver();
        intentFilter.addAction(intentHttpResetPassword);
        getActivity().registerReceiver(httpBroadcastReceiver, intentFilter);
    }

    private void setAction() {
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword1 = password.getText().toString();
                if (newPassword1.equals("")){
                    Toast.makeText(getContext(), "请输入新密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                System.out.println(token);
                OkHttpAction okHttpAction = new OkHttpAction(getContext());
                okHttpAction.resetPassword(BasicActivity.phoneNumber, newPassword1, intentHttpResetPassword);
            }
        });
    }

    private class HttpBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action =  intent.getAction();
            if (action.equals(intentHttpResetPassword)){
                String data = intent.getStringExtra("data");
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String message = jsonObject.getString("message");
                    if (!message.equals("success")){
                        Toast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(intentActionNext);
                    getActivity().sendBroadcast(intent1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
