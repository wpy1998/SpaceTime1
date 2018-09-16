package test.haixi.com.spacetime.FunctionTopic.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import test.haixi.com.spacetime.R;

@SuppressLint("ValidFragment")
public class FragmentTopicIntroduction extends Fragment{
    ImageView follow, back;
    TextView content;
    Button nextPage;

    String intentActionFinish, intentActionNext;
    boolean isFollow = false;

    public FragmentTopicIntroduction(String intentActionFinish, String intentActionNext){
        this.intentActionFinish = intentActionFinish;
        this.intentActionNext = intentActionNext;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_introduction, container, false);
        initView(view);
        setAction();
        initData();
        return view;
    }

    private void initData() {
        content.setText("《绝地求生：大逃杀》是一款大逃杀类型的游戏，每一局游戏将有100名玩家参与，" +
                "他们将被投放在绝地岛(battlegrounds)的上空，" +
                "游戏开始跳伞时所有人都一无所有。“吃鸡”一词随着游戏《绝地求生：大逃杀》走红。");
    }

    private void setAction() {
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollow){
                    follow.setImageDrawable(null);
                    follow.setImageResource(R.drawable.star);
                }else {
                    follow.setImageDrawable(null);
                    follow.setImageResource(R.drawable.star_lighting);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(intentActionFinish);
                getActivity().sendBroadcast(intent);
            }
        });

        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(intentActionNext);
                getActivity().sendBroadcast(intent);
            }
        });
    }

    private void initView(View view) {
        follow = view.findViewById(R.id.fragment_topic_introduction_follow);
        back = view.findViewById(R.id.fragment_topic_introduction_back);
        content = view.findViewById(R.id.fragment_topic_introduction_content);
        nextPage = view.findViewById(R.id.fragment_topic_introduction_next);
    }
}
