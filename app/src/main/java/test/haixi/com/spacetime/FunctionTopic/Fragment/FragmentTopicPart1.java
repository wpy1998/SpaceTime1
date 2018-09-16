package test.haixi.com.spacetime.FunctionTopic.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import test.haixi.com.spacetime.R;
import test.haixi.com.spacetime.FunctionTopic.component.TopicContentView;

public class FragmentTopicPart1 extends Fragment{
    LinearLayout mainView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_part1, container, false);
        initView(view);
        setAction();
        return view;
    }

    private void setAction() {
    }

    private void initView(View view) {
        mainView = view.findViewById(R.id.fragment_topic_part1_mainView);
        mainView.removeAllViews();
        mainView.addView(new TopicContentView(getContext()));
        mainView.addView(new TopicContentView(getContext()));
        mainView.addView(new TopicContentView(getContext()));
        mainView.addView(new TopicContentView(getContext()));
    }
}
