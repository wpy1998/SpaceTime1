package test.haixi.com.spacetime.FunctionWelcome.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.haixi.com.spacetime.R;

@SuppressLint("ValidFragment")
public class FragmentFrame extends Fragment{
    int page;
    String intentActionNext;

    LinearLayout mainView;
    TextView content;

    public FragmentFrame(int page, String intentActionNext){
        this.page = page;
        this.intentActionNext = intentActionNext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frame, container, false);
        initView(view);
        setAction();
        return view;
    }

    private void initView(View view) {
        content = view.findViewById(R.id.frame_content);
        mainView = view.findViewById(R.id.frame_mainView);
    }

    private void setAction() {
        if (page == 0){
            content.setText("不同时段不同主题\n多重空间任你选择\n");
        }else if (page == 1){
            content.setText("3分钟即时匿名聊\n天,相互相互成为\n好友");
        }else if (page == 2){
            content.setTextSize(30);
            content.setText("创造时间和空间上的相遇\n开启场景社交新时代\n");
            Button button = new Button(getContext());
            button.setText("立即开启");
            button.setBackgroundColor(Color.parseColor("#3E66FB"));
            button.setTextColor(Color.parseColor("#ffffff"));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(intentActionNext);
                    getActivity().sendBroadcast(intent);
                }
            });

            mainView.addView(button);
        }
    }
}
