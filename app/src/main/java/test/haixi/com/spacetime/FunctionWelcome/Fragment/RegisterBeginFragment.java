package test.haixi.com.spacetime.FunctionWelcome.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import test.haixi.com.spacetime.BasicComponents.OkHttpAction;
import test.haixi.com.spacetime.FunctionWelcome.LoginActivity;
import test.haixi.com.spacetime.R;

@SuppressLint("ValidFragment")
public class RegisterBeginFragment extends Fragment{
    TextView toLogin, getVerificationCode, areaCode;
    EditText telephone, verificationCode, password;
    Button nextPage;
    ImageView chooseArea;

    IntentFilter intentFilter;
    HttpBroadcastReceiver httpBroadcastReceiver;

    int areaWhich;
    String intentActionFinish, intentActionNext;
    String intentHttpCheckExistence = "wpy.example.com.goalproject.Fragment.RegisterBeginFragment.checkExistence",
            intentHttpGetVerificationCode = "wpy.example.com.goalproject.Fragment.RegisterBeginFragment.getVerificationCode",
            intentHttpCheckVerificationCode = "wpy.example.com.goalproject.Fragment.RegisterBeginFragment.checkVerificationCode";

    public RegisterBeginFragment(String intentActionFinish, String intentActionNext){
        this.intentActionFinish = intentActionFinish;
        this.intentActionNext = intentActionNext;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_begin, container, false);
        initView(view);
        setAction();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(httpBroadcastReceiver);
    }

    private void setAction() {
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        getVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone1 = telephone.getText().toString();
                if (phone1.equals("")){
                    Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                } else if (phone1.length() != 11){
                    Toast.makeText(getContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }else {
                    OkHttpAction okHttpAction = new OkHttpAction(getContext());
                    okHttpAction.checkExistence(phone1, intentHttpCheckExistence);
                }
            }
        });

        chooseArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setTitle("请选择您的地区号码").setIcon(
                        R.drawable.myperson).setSingleChoiceItems(
                        new String[] { "中国", "日本1", "日本2", "悉尼", "英国", "印度"}, areaWhich,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                areaWhich = which;
                                if (areaWhich == 0){
                                    areaCode.setText("+86");
                                }else if (areaWhich == 1){
                                    areaCode.setText("+080");
                                }else if (areaWhich == 2){
                                    areaCode.setText("+090");
                                }else if (areaWhich == 3){
                                    areaCode.setText("+04");
                                }else if (areaWhich == 4){
                                    areaCode.setText("+91");
                                }else if (areaWhich == 5){
                                    areaCode.setText("+98");
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });

        areaCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setTitle("请选择您的地区号码").setIcon(
                        R.drawable.myperson).setSingleChoiceItems(
                        new String[] { "中国", "日本1", "日本2", "悉尼", "英国", "印度"}, areaWhich,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                areaWhich = which;
                                if (areaWhich == 0){
                                    areaCode.setText("+86");
                                }else if (areaWhich == 1){
                                    areaCode.setText("+080");
                                }else if (areaWhich == 2){
                                    areaCode.setText("+090");
                                }else if (areaWhich == 3){
                                    areaCode.setText("+04");
                                }else if (areaWhich == 4){
                                    areaCode.setText("+91");
                                }else if (areaWhich == 5){
                                    areaCode.setText("+98");
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = password.getText().toString();
                String verificationCode1 = verificationCode.getText().toString();
                String phone1 = telephone.getText().toString();
                if (phone1.equals("")){
                    Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (phone1.length() != 11){
                    Toast.makeText(getContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (verificationCode1.equals("")){
                    Toast.makeText(getContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password1.equals("")){
                    Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                OkHttpAction okHttpAction = new OkHttpAction(getContext());
                okHttpAction.checkVerificationCode(phone1, verificationCode1, intentHttpCheckVerificationCode);
            }
        });
    }

    private void initView(View view) {
        toLogin = view.findViewById(R.id.register_begin_login);
        getVerificationCode = view.findViewById(R.id.register_begin_getVerificationCode);
        areaCode = view.findViewById(R.id.register_begin_areaCode);
        chooseArea = view.findViewById(R.id.register_begin_chooseArea);
        telephone = view.findViewById(R.id.register_begin_telephoneNumber);
        verificationCode = view.findViewById(R.id.register_begin_verification_code);
        password = view.findViewById(R.id.register_begin_setPassword);
        nextPage = view.findViewById(R.id.register_begin_nextPage);

        intentFilter = new IntentFilter();
        httpBroadcastReceiver = new HttpBroadcastReceiver();
        intentFilter.addAction(intentHttpGetVerificationCode);
        intentFilter.addAction(intentHttpCheckVerificationCode);
        intentFilter.addAction(intentHttpCheckExistence);
        getActivity().registerReceiver(httpBroadcastReceiver, intentFilter);
    }

    private class HttpBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(intentHttpGetVerificationCode)){
                String data = intent.getStringExtra("data");
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String message = jsonObject.getString("message");
                    if (!message.equals("success")){
                        return;
                    }

                    Toast.makeText(getContext(), "验证码已发送，请及时查收", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if (action.equals(intentHttpCheckVerificationCode)){
                String data = intent.getStringExtra("data");
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String message = jsonObject.getString("message");
                    if (!message.equals("success")){
                        Toast.makeText(getContext(), "验证码检查错误，请重新获取", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String data1 = jsonObject.getString("data");
                    JSONObject object = new JSONObject(data1);
                    boolean correction = object.getBoolean("correction");
                    if (!correction){
                        Toast.makeText(getContext(), "验证码错误，请重新输入", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String password1 = password.getText().toString();
                    String verificationCode1 = verificationCode.getText().toString();
                    String phone1 = telephone.getText().toString();

                    Intent intent1 = new Intent(intentActionNext);
                    intent1.putExtra("smsCode", verificationCode1);
                    intent1.putExtra("phoneNumber", phone1);
                    intent1.putExtra("password", password1);
                    context.sendBroadcast(intent1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if (action.equals(intentHttpCheckExistence)){
                String data = intent.getStringExtra("data");
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String message = jsonObject.getString("message");
                    if (!message.equals("success")){
                        return;
                    }

                    String data1 = jsonObject.getString("data");
                    JSONObject object = new JSONObject(data1);
                    boolean existence = object.getBoolean("existence");
                    if (existence){
                        Toast.makeText(getContext(), "该手机号码已被注册", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String phoneNumber = telephone.getText().toString();
                    OkHttpAction okHttpAction = new OkHttpAction(getContext());
                    okHttpAction.getVerificationCode(phoneNumber, intentHttpGetVerificationCode);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}