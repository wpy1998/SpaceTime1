package test.haixi.com.spacetime.BasicComponents.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.haixi.com.spacetime.R;

public class Tags extends LinearLayout {
    Context context;
    TextView content;
    public Tags(Context context) {
        super(context);
        this.context = context;
        initLinearLayout();
    }

    private void initLinearLayout() {
        LayoutInflater.from(context).inflate(R.layout.item_tags, this, true);
        content = findViewById(R.id.tags_content);
    }

    public void setContent(String content){
        this.content.setText(content + "");
    }
}
