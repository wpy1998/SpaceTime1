package test.haixi.com.spacetime.BasicComponents.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.haixi.com.spacetime.R;

public class MessagePart2 extends LinearLayout {
    Context context;
    TextView name;
    EditText content;
    public MessagePart2(Context context) {
        super(context);
        this.context = context;
        initLinearLayout();
    }

    private void initLinearLayout() {
        LayoutInflater.from(context).inflate(R.layout.item_message_part2, this, true);
        name = findViewById(R.id.message_part_name);
        content = findViewById(R.id.message_part_content);
    }

    public void setName(String name1){
        name.setText(name1 + "");
    }

    public void setContent(String content1){
        content.setText(content1 + "");
    }
}
