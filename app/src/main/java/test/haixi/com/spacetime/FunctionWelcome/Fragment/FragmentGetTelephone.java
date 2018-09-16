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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import test.haixi.com.spacetime.BasicComponents.BasicActivity;
import test.haixi.com.spacetime.BasicComponents.OkHttpAction;
import test.haixi.com.spacetime.R;

@SuppressLint("ValidFragment")
public class FragmentGetTelephone extends Fragment{
    TextView chooseArea;
    Button nextPage;
    EditText telephone;
    IntentFilter intentFilter;
    HttpBroadcastReceiver httpBroadcastReceiver;

    String intentActionFinish, intentActionNext,
            intentHttpGetVerificationCode = "wpy.example.com.goalproject.Fragment.FragmentGetTelephone.getVerificationCode";
    int areaWhich;

    public FragmentGetTelephone(String intentActionFinish, String intentActionNext){
        this.intentActionFinish = intentActionFinish;
        this.intentActionNext = intentActionNext;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_telephone, container, false);
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
                                    chooseArea.setText("+86");
                                }else if (areaWhich == 1){
                                    chooseArea.setText("+080");
                                }else if (areaWhich == 2){
                                    chooseArea.setText("+090");
                                }else if (areaWhich == 3){
                                    chooseArea.setText("+04");
                                }else if (areaWhich == 4){
                                    chooseArea.setText("+91");
                                }else if (areaWhich == 5){
                                    chooseArea.setText("+98");
                                }
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null).show();
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = telephone.getText().toString();
                if (phoneNumber == ""){
                    Toast.makeText(getContext(), "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }else if (phoneNumber.length() != 11){
                    Toast.makeText(getContext(), "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                BasicActivity.phoneNumber = telephone.getText().toString();
                OkHttpAction okHttpAction = new OkHttpAction(getContext());
                okHttpAction.getVerificationCode(phoneNumber, intentHttpGetVerificationCode);
            }
        });
    }

    private void initView(View view) {
        chooseArea = view.findViewById(R.id.getTelephone_chooseArea);
        nextPage = view.findViewById(R.id.getTelephone_next);
        telephone = view.findViewById(R.id.getTelephone_telephoneNumber);
        intentFilter = new IntentFilter();
        httpBroadcastReceiver = new HttpBroadcastReceiver();
        intentFilter.addAction(intentHttpGetVerificationCode);
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
                    Intent intent1 = new Intent(intentActionNext);
                    getActivity().sendBroadcast(intent1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
