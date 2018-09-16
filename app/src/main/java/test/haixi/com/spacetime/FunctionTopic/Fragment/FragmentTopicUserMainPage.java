package test.haixi.com.spacetime.FunctionTopic.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import test.haixi.com.spacetime.R;

@SuppressLint("ValidFragment")
public class FragmentTopicUserMainPage extends Fragment{
    String intentActionFinish, intentActionNext;

    public FragmentTopicUserMainPage(String intentActionFinish, String intentActionNext){
        this.intentActionFinish = intentActionFinish;
        this.intentActionNext = intentActionNext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic_user_main_page, container, false);
        return view;
    }
}
