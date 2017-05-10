package com.hrc.myapplication.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.hrc.myapplication.R;
import com.hrc.myapplication.ui.adapter.LeadImgAdapter;
import com.hrc.myapplication.ui.base.MyBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 引导界面
 */

public class ActivityLead extends MyBaseActivity {
    private ViewPager viewPager;
    private ImageView[] points=new ImageView[4];
    private LeadImgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        //xml文件存储   偏好--音乐 音效 皮肤
        SharedPreferences preferences=getSharedPreferences("runconfig",MODE_PRIVATE);
        //从文件中获取存储的数据，默认为true
        boolean isFirst=preferences.getBoolean("isFirstRun",true);
        //如果不是第一次打开，则直接跳转到Logo界面
        if (!isFirst){
            openActivity(ActivityLogo.class);
            finish();
            return;
        }
        points[0]= (ImageView) findViewById(R.id.iv_p1);
        points[1]= (ImageView) findViewById(R.id.iv_p2);
        points[2]= (ImageView) findViewById(R.id.iv_p3);
        points[3]= (ImageView) findViewById(R.id.iv_p4);
        setPoint(0);
        //初始化控件
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        //设置每一个具体界面的样式
        List<View> viewList=new ArrayList<View>();
        viewList.add(getLayoutInflater().inflate(R.layout.lead_1,null));
        viewList.add(getLayoutInflater().inflate(R.layout.lead_2,null));
        viewList.add(getLayoutInflater().inflate(R.layout.lead_3,null));
        viewList.add(getLayoutInflater().inflate(R.layout.lead_4,null));
        //初始化适配器
        adapter=new LeadImgAdapter(viewList);
        //设置适配器
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(listener);
    }

    private void setPoint(int index){
        for (int i=0;i<points.length;i++){
            if (i==index){
                points[i].setAlpha(1.0f);
            }else{
                points[i].setAlpha(0.1f);
            }
        }
    }

    private ViewPager.OnPageChangeListener listener=new ViewPager.OnPageChangeListener() {
        /**当界面切换时调用*/
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        /**当界面切换后调用*/
        @Override
        public void onPageSelected(int position) {
            setPoint(position);
            if (position>=3){
                openActivity(ActivityLogo.class);
                finish();
                SharedPreferences preferences=getSharedPreferences("runconfig",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("isFirstRun",false);
                editor.commit();
            }
        }
        /**滑动状态变化时调用*/
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
