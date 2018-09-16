package test.haixi.com.spacetime.FunctionUser.Fragment;

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
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import test.haixi.com.spacetime.BasicComponents.OkHttpAction;
import test.haixi.com.spacetime.R;

@SuppressLint("ValidFragment")
public class FragmentUser extends Fragment{
    Button exit;
    IntentFilter intentFilter;
    LinearLayout editUserMessage, accountAndSafe, feedback, aboutUs;
    Switch getLocation, getNotification;
    HttpBroadcastReceiver httpBroadcastReceiver;

    String intentHttpLogout = "wpy.example.com.functionuser.Fragment.FragmentUser.logout";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        setAction();
        return view;
    }

    private void setAction() {
        editUserMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "editUserMessage", Toast.LENGTH_SHORT).show();
            }
        });

        accountAndSafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "accountAndSafe", Toast.LENGTH_SHORT).show();
            }
        });

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "feedback", Toast.LENGTH_SHORT).show();
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "aboutUs", Toast.LENGTH_SHORT).show();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpAction okHttpAction = new OkHttpAction(getContext());
                okHttpAction.logout(intentHttpLogout);
                getActivity().finish();
            }
        });
    }

    private void initView(View view) {
        exit = view.findViewById(R.id.exit);
        editUserMessage = view.findViewById(R.id.fragment_user_edit_user_message);
        accountAndSafe = view.findViewById(R.id.fragment_user_account_and_safe);
        getLocation = view.findViewById(R.id.fragment_user_is_getLocation);
        getNotification = view.findViewById(R.id.fragment_user_is_getNotification);
        feedback = view.findViewById(R.id.fragment_user_feedback);
        aboutUs = view.findViewById(R.id.fragment_user_about_us);

        intentFilter = new IntentFilter();
        httpBroadcastReceiver = new HttpBroadcastReceiver();
        intentFilter.addAction(intentHttpLogout);
        getActivity().registerReceiver(httpBroadcastReceiver, intentFilter);
    }

    private class HttpBroadcastReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(intentHttpLogout)){
                String data = intent.getStringExtra("data");
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String message = jsonObject.getString("message");

                    if (!message.equals("success")){
                        Toast.makeText(getContext(), "登出失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
