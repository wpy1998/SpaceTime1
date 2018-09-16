package test.haixi.com.spacetime.BasicComponents.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import test.haixi.com.spacetime.R;

public class FunctionContentView extends LinearLayout {
    Context context;
    TextView time, theme;
    ImageView collection;
    public LinearLayout mainView;
    boolean isCollection = false;
    public FunctionContentView(Context context) {
        super(context);
        this.context = context;
        initLinearLayout();
        setAction();
    }

    private void initLinearLayout(){
        LayoutInflater.from(context).inflate(R.layout.item_function, this, true);
        time = findViewById(R.id.functionContentView_time);
        theme = findViewById(R.id.functionContentView_theme);
        collection = findViewById(R.id.functionContentView_collection);
        mainView = findViewById(R.id.functionContentView_mainView);
    }

    protected void setAction(){
        collection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollection){
                    isCollection = false;
                    collection.setImageDrawable(null);
                    collection.setImageResource(R.drawable.star);
                }else {
                    isCollection = true;
                    collection.setImageDrawable(null);
                    collection.setImageResource(R.drawable.star_lighting);
                }
            }
        });
    }
}
