package test.haixi.com.spacetime.BasicComponents.Components;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {
    private boolean isSlide = true;
    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSlide(boolean slide) {
        isSlide = slide;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        super.onTouchEvent(arg0);
        return isSlide;
    }
}
