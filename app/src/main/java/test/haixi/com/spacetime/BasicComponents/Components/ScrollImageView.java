package test.haixi.com.spacetime.BasicComponents.Components;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import test.haixi.com.spacetime.R;

public class ScrollImageView extends RelativeLayout {
    private int[] imageResIds; //存放图片资源id的数组
    private ArrayList<ImageView> imageViews;
    private String[] contentDescs;
    private LinearLayout ll_point;
    private int lastPosition, imageViewNumber = 0;
    private ViewPager viewPager;
    TextView introduction;
    Context context;

    Activity activity;

    public ScrollImageView(Context context) {
        super(context);
        this.context = context;
    }

    public ScrollImageView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.context = context;
    }

    public void start(String[] contentDescs, int[] imageResIds){
        this.contentDescs = contentDescs;
        this.imageResIds = imageResIds;
        imageViewNumber = imageResIds.length;
        initView();
        initData();
        initAdapter();
        setAction();
    }

    private void initView(){
        LayoutInflater.from(context).inflate(R.layout.item_scroll_imageview, this, true);
        ll_point = findViewById(R.id.scroll_imageView_point);
        viewPager = findViewById(R.id.scroll_imageView_viewPager);
        introduction = findViewById(R.id.scroll_imageView_introduction);

        activity = (Activity) context;
    }

    private void initData() {
        //保存图片资源的集合
        imageViews = new ArrayList<>();
        ImageView imageView;
        View pointView;
        //循环遍历图片资源，然后保存到集合中
        for (int i = 0; i < imageResIds.length; i++){
            //添加图片到集合中
            imageView = new ImageView(getContext());
            imageView.setBackgroundResource(imageResIds[i]);
            imageViews.add(imageView);

            //加小白点，指示器（这里的小圆点定义在了drawable下的选择器中了，也可以用小图片代替）
            pointView = new View(getContext());
            pointView.setBackgroundResource(R.color.selectot_bg_point); //使用选择器设置背景
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(8, 8);
            if (i != 0){
                //如果不是第一个点，则设置点的左边距
                layoutParams.leftMargin = 10;
            }
            pointView.setEnabled(false); //默认都是暗色的
            ll_point.addView(pointView, layoutParams);
        }
    }

    private void initAdapter() {
        ll_point.getChildAt(0).setEnabled(true); //初始化控件时，设置第一个小圆点为亮色
        introduction.setText(contentDescs[0]); //设置第一个图片对应的文字
        lastPosition = 0; //设置之前的位置为第一个
        viewPager.setAdapter(new MyPagerAdapter());
        //设置默认显示中间的某个位置（这样可以左右滑动），这个数只有在整数范围内，可以随便设置
        viewPager.setCurrentItem(0);
    }

    private void setAction() {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //--------------以下是设置ViewPager的滚动监听所需实现的方法--------
            //页面滑动
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //新的页面被选中
            @Override
            public void onPageSelected(int position) {
                //当前的位置可能很大，为了防止下标越界，对要显示的图片的总数进行取余
                int newPosition = position % imageViewNumber;
                //设置描述信息
                introduction.setText(contentDescs[newPosition]);
                //设置小圆点为高亮或暗色
                ll_point.getChildAt(lastPosition).setEnabled(false);
                ll_point.getChildAt(newPosition).setEnabled(true);
                lastPosition = newPosition; //记录之前的点
            }

            //页面滑动状态发生改变
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyPagerAdapter extends PagerAdapter {

        //返回显示数据的总条数，为了实现无限循环，把返回的值设置为最大整数
        @Override
        public int getCount() {
            return imageViewNumber;
        }

        //指定复用的判断逻辑，固定写法：view == object
        @Override
        public boolean isViewFromObject(View view, Object object) {
            //当创建新的条目，又反回来，判断view是否可以被复用(即是否存在)
            return view == object;
        }

        //返回要显示的条目内容
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //container  容器  相当于用来存放imageView
            //从集合中获得图片
            int newPosition = position % imageViewNumber; //数组中总共有5张图片，超过数组长度时，取摸，防止下标越界
            ImageView imageView = imageViews.get(newPosition);
            //把图片添加到container中
            container.addView(imageView);
            //把图片返回给框架，用来缓存
            return imageView;
        }

        //销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //object:刚才创建的对象，即要销毁的对象
            container.removeView((View) object);
        }
    }
}
