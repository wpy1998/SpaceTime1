package test.haixi.com.spacetime.FunctionTopic.component;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import test.haixi.com.spacetime.BasicComponents.Components.FunctionContentView;
import test.haixi.com.spacetime.FunctionTopic.TopicActivity;

public class TopicContentView extends FunctionContentView {
    public TopicContentView(Context context) {
        super(context);
    }

    @Override
    protected void setAction() {
        super.setAction();
        mainView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TopicActivity.class);
                getContext().startActivity(intent);
            }
        });
    }
}
