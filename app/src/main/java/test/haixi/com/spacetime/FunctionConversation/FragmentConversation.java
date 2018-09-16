package test.haixi.com.spacetime.FunctionConversation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import test.haixi.com.spacetime.BasicComponents.Adapter.FragmentAdapter;
import test.haixi.com.spacetime.BasicComponents.Components.CustomViewPager;
import test.haixi.com.spacetime.R;

public class FragmentConversation extends Fragment {
    RadioButton personConversation, friend;
    FragmentAdapter fragmentAdapter;
    CustomViewPager viewPager;
    List<Fragment> fragments;
    Fragment fragment3PersonConversation, fragment3Friend;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        initView(view);
        setAction();
        return view;
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.fragment_conversation_viewPager);
        fragments = new ArrayList<Fragment>();
        fragment3PersonConversation = new Fragment();
        fragment3Friend = new Fragment();
        fragments.add(fragment3PersonConversation);
        fragments.add(fragment3Friend);
        fragmentAdapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments);

        friend = view.findViewById(R.id.fragment_conversation_friend);
        personConversation = view.findViewById(R.id.fragment_conversation_personConversation);
    }

    private void setAction() {
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(2);
        personConversation.performClick();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getContext(), "position = " + position, Toast.LENGTH_SHORT).show();
                if (position == 0){
                    personConversation.performClick();
                }else if (position == 1) {
                    friend.performClick();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        personConversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0, true);
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1, true);
            }
        });
    }
}
