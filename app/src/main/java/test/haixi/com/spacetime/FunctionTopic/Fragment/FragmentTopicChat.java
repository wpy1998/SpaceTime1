package test.haixi.com.spacetime.FunctionTopic.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import test.haixi.com.spacetime.BasicComponents.ChatView;
import test.haixi.com.spacetime.R;

@SuppressLint("ValidFragment")
public class FragmentTopicChat extends Fragment{
    TextView time;
    ImageView back, decision, chooseTrue, chooseFalse;
    View chooseView;
    AlertDialog choose;

    String intentActionFinish, intentActionNext;

    public FragmentTopicChat(String intentActionFinish, String intentActionNext){
        this.intentActionFinish = intentActionFinish;
        this.intentActionNext = intentActionNext;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_chat, container, false);
        initView(view);
        setAction();
        replaceFragment(new ChatView());
        return view;
    }

    private void setAction() {
        decision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose.show();
            }
        });

        chooseTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "you choose true", Toast.LENGTH_SHORT).show();
            }
        });

        chooseFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "you choose false", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(intentActionFinish);
                getActivity().sendBroadcast(intent);
            }
        });
    }

    private void initView(View view) {
        time = view.findViewById(R.id.fragment_topic_chat_time);
        back = view.findViewById(R.id.fragment_topic_chat_back);
        decision = view.findViewById(R.id.fragment_topic_chat_decision);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        chooseView = layoutInflater.inflate(R.layout.alertdialog_fragment_topic_chat_choose, null);
        choose = new AlertDialog.Builder(getContext()).setView(chooseView).create();

        chooseTrue = chooseView.findViewById(R.id.alertDialog_fragment_topic_chat_true);
        chooseFalse = chooseView.findViewById(R.id.alertDialog_fragment_topic_chat_false);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_topic_chat_frameLayout, fragment);
        transaction.commit();
    }
}
