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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import test.haixi.com.spacetime.BasicComponents.BasicActivity;
import test.haixi.com.spacetime.BasicComponents.OkHttpAction;
import test.haixi.com.spacetime.R;

@SuppressLint("ValidFragment")
public class FragmentVerificationCode extends Fragment{
    EditText content;
    IntentFilter intentFilter;
    HttpBroadcastReceiver httpBroadcastReceiver;

    String intentActionNext, intentActionFinish,
            intentHttpLoginWithSmsCode = "wpy.example.com.goalproject.Fragment.FragmentVerificationCode.loginWithSmsCode",
            intentHttpForgetPassword = "wpy.example.com.goalproject.Fragment.FragmentVerificationCode.forgetPassword",
            intentHttpCheckSmsCode = "wpy.example.com.goalproject.Fragment.FragmentVerificationCode.checkSmsCode";

    public FragmentVerificationCode(String intentActionFinish, String intentActionNext){
        this.intentActionFinish = intentActionFinish;
        this.intentActionNext = intentActionNext;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verification_code, container, false);
        initView(view);
        setAction();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(httpBroadcastReceiver);
    }

    private void setAction() {
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content1 = content.getText().toString();
                if (content1.length() >= 4){
                    content.setFocusable(false);
                    content.setFocusableInTouchMode(false);
                    content.setClickable(false);
                    OkHttpAction okHttpAction = new OkHttpAction(getContext());
                    okHttpAction.loginWithVerificationCode(BasicActivity.phoneNumber, content.getText().toString(),
                            intentHttpLoginWithSmsCode);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView(View view) {
        content = view.findViewById(R.id.verificationCode_content);
        intentFilter = new IntentFilter();
        httpBroadcastReceiver = new HttpBroadcastReceiver();
        intentFilter.addAction(intentHttpLoginWithSmsCode);
        intentFilter.addAction(intentHttpForgetPassword);
        intentFilter.addAction(intentHttpCheckSmsCode);
        getActivity().registerReceiver(httpBroadcastReceiver, intentFilter);
    }

    private class HttpBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(intentHttpLoginWithSmsCode)){
                String data = intent.getStringExtra("data");
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String message = jsonObject.getString("message");
                    if (!message.equals("success")){
                        content.setFocusable(true);
                        content.setFocusableInTouchMode(true);
                        content.setClickable(true);
                        Toast.makeText(getContext(), "验证码检查错误，请重新获取", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String data1 = jsonObject.getString("data");
                    JSONObject object = new JSONObject(data1);
                    BasicActivity.accessKeyId = object.getString("accessKeyId");
                    BasicActivity.accessKeySecret = object.getString("accessKeySecret");
                    BasicActivity.token = object.getString("token");
                    Intent intent1 = new Intent(intentActionNext);
                    getActivity().sendBroadcast(intent1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
