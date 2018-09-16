package test.haixi.com.spacetime.BasicComponents.Components;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import test.haixi.com.spacetime.R;

public class Lines extends LinearLayout {
    Context context;
    public Lines(Context context) {
        super(context);
        this.context = context;
        initLinearLayout();
    }

    private void initLinearLayout() {
        LayoutInflater.from(context).inflate(R.layout.item_lines, this, true);
    }
}
