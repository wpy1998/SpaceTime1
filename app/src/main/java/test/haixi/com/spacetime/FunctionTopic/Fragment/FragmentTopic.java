package test.haixi.com.spacetime.FunctionTopic.Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import test.haixi.com.spacetime.BasicComponents.Adapter.FragmentAdapter;
import test.haixi.com.spacetime.BasicComponents.Components.CustomViewPager;
import test.haixi.com.spacetime.R;

public class FragmentTopic extends Fragment{
    CustomViewPager viewPager;
    TextView collection, recommend;
    FragmentAdapter fragmentAdapter;
    List<Fragment> fragments;
    FragmentTopicPart1 recommendF, collectionF;
    ImageView select, search;

    View myLoginView;
    AlertDialog alertDialog;
    RadioButton gender0, gender1, gender2, condition0, condition1, condition2;
    SeekBar distance, age;
    Switch aSwitch;
    String genderTarget, conditionTarget;
    float distanceTarget, ageTarget;
    boolean isStudentFirst = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topic, container, false);
        initView(view);
        setAction();
        initAlertDialog();
        recommend.performClick();
        return view;
    }

    private void setAction() {
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getContext(), "position = " + position, Toast.LENGTH_SHORT).show();
                if (position == 0){
                    recommend.performClick();
                }else if (position == 1){
                    collection.performClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.fragment_topic_viewPager);
        recommend = view.findViewById(R.id.fragment_topic_recommend);
        collection = view.findViewById(R.id.fragment_topic_collection);
        search = view.findViewById(R.id.fragment_topic_search);
        select = view.findViewById(R.id.fragment_topic_select);

        fragments = new ArrayList<>();
        recommendF = new FragmentTopicPart1();
        collectionF = new FragmentTopicPart1();
        fragments.add(recommendF);
        fragments.add(collectionF);
        fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments);
    }

    private void initAlertDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        myLoginView = layoutInflater.inflate(R.layout.alertdialog_choose_target, null);
        alertDialog = new AlertDialog.Builder(getContext()).setView(myLoginView)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", null).create();
        gender0 = myLoginView.findViewById(R.id.alertDialog_choose_target_gender_0);
        gender1 = myLoginView.findViewById(R.id.alertDialog_choose_target_gender_1);
        gender2 = myLoginView.findViewById(R.id.alertDialog_choose_target_gender_2);
        condition0 = myLoginView.findViewById(R.id.alertDialog_choose_target_condition_0);
        condition1 = myLoginView.findViewById(R.id.alertDialog_choose_target_condition_1);
        condition2 = myLoginView.findViewById(R.id.alertDialog_choose_target_condition_2);
        distance = myLoginView.findViewById(R.id.alertDialog_choose_target_distance);
        age = myLoginView.findViewById(R.id.alertDialog_choose_target_age);
        aSwitch = myLoginView.findViewById(R.id.alertDialog_choose_target_switch);

        gender0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderTarget = "保密";
            }
        });

        gender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderTarget = "男";
            }
        });

        gender2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderTarget = "女";
            }
        });

        condition0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionTarget = "不限";
            }
        });

        condition1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionTarget = "学生";
            }
        });

        condition2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conditionTarget = "工作";
            }
        });

        distance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceTarget = 30 * progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        age.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ageTarget = (float) (progress * 0.06 + 16);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    isStudentFirst = true;
                }else {
                    isStudentFirst = false;
                }
            }
        });
    }
}
