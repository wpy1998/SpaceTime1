package test.haixi.com.spacetime.FunctionUser.Component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import test.haixi.com.spacetime.R;

public class UserImageView extends LinearLayout{
    public ImageView imageView;
    public String message = "userImageView", picturePath;
    public int width, height;
    Context context;
    public UserImageView(Context context) {
        super(context);
        this.context = context;
        initLinearLayout();
    }

    public UserImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initLinearLayout();
    }

    private void initLinearLayout() {
        LayoutInflater.from(context).inflate(R.layout.item_user_image_view, this, true);
        imageView = findViewById(R.id.user_image_view_imageView);
    }
}
