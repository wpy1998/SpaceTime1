package test.haixi.com.spacetime;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import test.haixi.com.spacetime.BasicComponents.BasicActivity;
import test.haixi.com.spacetime.FunctionConversation.FragmentConversation;
import test.haixi.com.spacetime.FunctionTopic.Fragment.FragmentTopic;
import test.haixi.com.spacetime.FunctionUser.Fragment.FragmentUser;
import test.haixi.com.spacetime.FunctionUser.UserActivity;

public class MainActivity extends BasicActivity {
    ImageView conversation, personal, function;
    FragmentTopic fragmentTopic;
    FragmentConversation fragmentConversation;
    boolean isUser = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setAction();
        function.performClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView(){
        conversation = findViewById(R.id.main_conversation);
        personal = findViewById(R.id.main_personal);
        function = findViewById(R.id.main_function);
    }

    private void setAction() {
        conversation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser = false;
                conversation.setImageDrawable(null);
                personal.setImageDrawable(null);
                conversation.setImageResource(R.drawable.conversation_lighting);
                personal.setImageResource(R.drawable.person);
                fragmentConversation = new FragmentConversation();
                replaceFragment(fragmentConversation);
            }
        });

        function.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser = false;
                conversation.setImageDrawable(null);
                personal.setImageDrawable(null);
                conversation.setImageResource(R.drawable.conversation);
                personal.setImageResource(R.drawable.person);
                fragmentTopic = new FragmentTopic();
                replaceFragment(fragmentTopic);
            }
        });

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUser = true;
                conversation.setImageDrawable(null);
                personal.setImageDrawable(null);
                conversation.setImageResource(R.drawable.conversation);
                personal.setImageResource(R.drawable.person_lighting);
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("type", "fragmentUser");
                startActivity(intent);
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fragment_change_animation, 0, 0, 0);
        transaction.replace(R.id.main_fragment, fragment);
        transaction.commit();
    }
}
