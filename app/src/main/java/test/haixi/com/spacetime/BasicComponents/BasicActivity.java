package test.haixi.com.spacetime.BasicComponents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class BasicActivity extends AppCompatActivity {
    public static String token = "";
    public static String phoneNumber = "";
    public static String password = "";
    public static String accessKeyId = "";
    public static String accessKeySecret = "";
    public String intentActionFinish = "wpy.example.com.customcontroller.BasicActivity.finish";
    public int fragmentNumber = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        //隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar.isShowing()){
            actionBar.hide();
        }
    }
}
