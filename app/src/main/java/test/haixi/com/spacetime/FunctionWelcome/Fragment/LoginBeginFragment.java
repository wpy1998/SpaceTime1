package test.haixi.com.spacetime.FunctionWelcome.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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

import test.haixi.com.spacetime.BasicComponents.BasicActivity;
import test.haixi.com.spacetime.BasicComponents.OkHttpAction;
import test.haixi.com.spacetime.MainActivity;
import test.haixi.com.spacetime.R;
import test.haixi.com.spacetime.FunctionWelcome.RegisterActivity;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class LoginBeginFragment extends Fragment{
    Button login;
    TextView registerNewAccount, forgetPassword, getVerificationCode, area;
    ImageView chooseArea;
    EditText password, telephone;
    IntentFilter intentFilter;
    HttpBroadcastReceiver httpBroadcastReceiver;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    int areaWhich;
    String intentActionFinish, intentActionNext,
            intentHttpLogin = "wpy.example.com.goalproject.Fragment.LoginBeginFragment.login";

    public LoginBeginFragment(String intentActionFinish, String intentActionNext){
        this.intentActionFinish = intentActionFinish;
        this.intentActionNext = intentActionNext;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_begin, container, false);
        initView(view);
        setAction();
        LoadAccount();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(httpBroadcastReceiver);
    }

    private void initView(View view) {
        login = view.findViewById(R.id.login_login);
        registerNewAccount = view.findViewById(R.id.login_registerNewAccount);
        forgetPassword = view.findViewById(R.id.login_forgetPassword);
        getVerificationCode = view.findViewById(R.id.login_getVerificationCode);
        area = view.findViewById(R.id.login_telephoneArea);
        chooseArea = view.findViewById(R.id.login_chooseArea);
        password = view.findViewById(R.id.login_password);
        telephone = view.findViewById(R.id.login_telephoneNumber);

        intentFilter = new IntentFilter();
        httpBroadcastReceiver = new HttpBroadcastReceiver();
        intentFilter.addAction(intentHttpLogin);
        getActivity().registerReceiver(httpBroadcastReceiver, intentFilter);

        preferences = getActivity().getSharedPreferences("ShiKong", MODE_PRIVATE);
        editor = preferences.edit();
    }

    private void setAction(){
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
                                    area.setText("+86");
                                }else if (areaWhich == 1){
                                    area.setText("+080");
                                }else if (areaWhich == 2){
                                    area.setText("+090");
                                }else if (areaWhich == 3){
                                    area.setText("+04");
                                }else if (areaWhich == 4){
                                    area.setText("+91");
                                }else if (areaWhich == 5){
                                    area.setText("+98");
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });

        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setTitle("请选择您的地区号码").setIcon(
                        R.drawable.myperson).setSingleChoiceItems(
                        new String[] { "中国", "日本1", "日本2", "悉尼", "英国", "印度"}, areaWhich,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                areaWhich = which;
                                if (areaWhich == 0){
                                    area.setText("+86");
                                }else if (areaWhich == 1){
                                    area.setText("+080");
                                }else if (areaWhich == 2){
                                    area.setText("+090");
                                }else if (areaWhich == 3){
                                    area.setText("+04");
                                }else if (areaWhich == 4){
                                    area.setText("+91");
                                }else if (areaWhich == 5){
                                    area.setText("+98");
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                intent.putExtra("message", "forgetPassword");
                startActivity(intent);
            }
        });

        registerNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                intent.putExtra("message", "registerNewAccount");
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber1 = telephone.getText().toString();
                String password1 = password.getText().toString();
                if (phoneNumber1.equals("")){
                    Toast.makeText(getContext(), "请输入您的电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (phoneNumber1.length() != 11){
                    Toast.makeText(getContext(), "请输入正确格式的电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password1.equals("")){
                    Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                OkHttpAction okHttpAction = new OkHttpAction(getContext());
                okHttpAction.loginWithPassword(phoneNumber1, password1, intentHttpLogin);
            }
        });

        getVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(intentActionNext);
                getActivity().sendBroadcast(intent);
            }
        });
    }

    private class HttpBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(intentHttpLogin)){
                String data = intent.getStringExtra("data");
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String message = jsonObject.getString("message");
                    if (!message.equals("success")){
                        Toast.makeText(getContext(), "登录失败", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    String data1 = jsonObject.getString("data");
                    JSONObject object = new JSONObject(data1);
                    BasicActivity.token = object.getString("token");
                    BasicActivity.phoneNumber = telephone.getText().toString();
                    BasicActivity.password = password.getText().toString();
                    System.out.println(BasicActivity.token);
                    SaveAccount();

                    Intent intent1 = new Intent(getContext(), MainActivity.class);
                    startActivity(intent1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void LoadAccount(){
        String phoneNumber1 = preferences.getString("phoneNumber", "");
        String password1 = preferences.getString("password", "");
        telephone.setText(phoneNumber1);
        password.setText(password1);
    }

    private void SaveAccount(){
        editor.putString("phoneNumber", telephone.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.apply();
    }
}
