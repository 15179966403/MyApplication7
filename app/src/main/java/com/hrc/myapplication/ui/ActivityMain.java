package com.hrc.myapplication.ui;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.hrc.myapplication.R;
import com.hrc.myapplication.common.CommonUtil;
import com.hrc.myapplication.common.LogUtil;
import com.hrc.myapplication.ui.base.MyBaseActivity;
import com.hrc.myapplication.view.slidingmenu.SlidingMenu;

import java.util.ArrayList;

/**
 * 主界面
 */

public class ActivityMain extends MyBaseActivity{

    private Fragment fragmentMenu,fragmentMenuRight;
    private Fragment fragmentType,fragmentMain,fragmentLogin,fragmentRegister,fragmentFavorite,fragmentForgerPass;
    public static SlidingMenu slidingMenu;
    private ImageView iv_set,iv_user;
    private TextView textView_title;
    //用来存储当前显示的Fragment
    private Fragment mContentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView_title= (TextView) findViewById(R.id.textView1);
        iv_set= (ImageView) findViewById(R.id.imageView_set);
        iv_user= (ImageView) findViewById(R.id.imageView_user);
        iv_set.setOnClickListener(onClickListener);
        iv_user.setOnClickListener(onClickListener);
        textView_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> strs=new ArrayList<String>();
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(ActivityMain.this,android.R.layout.simple_list_item_1,strs);
                strs.add("加入收藏");
                strs.add("跟帖");
                View contentView=getLayoutInflater().from(ActivityMain.this).inflate(R.layout.layout_pop,null);
                ListView lv= (ListView) contentView.findViewById(R.id.list_item);
                lv.setAdapter(adapter);
                PopupWindow pwindow=new PopupWindow(contentView,400,500,true);
                pwindow.setFocusable(true);
                pwindow.setBackgroundDrawable(getWallpaper());
                pwindow.showAsDropDown(textView_title,100,100);
            }
        });
        initSlidingMenu();
        showFragmentMain();
    }

    /**
     * 显示：“显示新闻列表的Fragment”
     */
    public void showFragmentMain() {
        setTitle("资讯");
        slidingMenu.showContent();
        if (fragmentMain==null){
            fragmentMain=new FragmentMain();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,fragmentMain).commit();
        setmContentFragment(fragmentMain);
        LogUtil.d("当前显示的Fragment:"+getmContentFragment());
    }

    /**
     * 显示：“登录的Fragment”
     */
    public void showFragmentLogin(){
        setTitle("用户登录");
        slidingMenu.showContent();
        //if not new the fragmentLogin,old fragmentLogin's data will save
//        if (fragmentLogin==null){
//            fragmentLogin=new FragmentLogin();
//        }
        fragmentLogin=new FragmentLogin();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,fragmentLogin).commit();
        setmContentFragment(fragmentLogin);
        LogUtil.d("当前显示的Fragment:"+getmContentFragment());
    }

    /**
     * 显示：“注册的Fragment”
     */
    public void showFragmentRegister(){
        setTitle("注册");
        if (fragmentRegister==null){
            fragmentRegister=new FragmentRegister();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,fragmentRegister).commit();
    }

    /**
     * 显示：“忘记密码的Fragment”
     */
    public void showFragmentForgetPass(){
        setTitle("忘记密码");
        if (fragmentForgerPass==null)
            fragmentForgerPass=new FragmentForgetPass();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,fragmentForgerPass).commit();
    }
    /**
     *显示：“分类的Fragment”
     */
    public void showFragmentType(){
        setTitle("分类");
        slidingMenu.showContent();
        if(fragmentType==null)
            fragmentType=new FragmentType();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_content,fragmentType).commit();
    }

    /**初始化侧滑菜单*/
    private void initSlidingMenu() {
        fragmentMenu=new FragmentMenu();
        fragmentMenuRight=new FragmentMenuRight();

        slidingMenu=new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);

        slidingMenu.setMenu(R.layout.layout_menu);
        slidingMenu.setSecondaryMenu(R.layout.layout_menu_right);

        getSupportFragmentManager().beginTransaction().replace(R.id.layout_menu,fragmentMenu).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.layout_menu_right,fragmentMenuRight).commit();
    }

    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageView_set:
                    if (slidingMenu!=null&&slidingMenu.isMenuShowing()){
                        slidingMenu.showContent();
                    }else if (slidingMenu!=null){
                        slidingMenu.showMenu();
                    }
                    break;
                case R.id.imageView_user:
                    if (slidingMenu!=null&&slidingMenu.isMenuShowing()){
                        slidingMenu.showContent();
                    }else if (slidingMenu!=null){
                        slidingMenu.showSecondaryMenu();
                    }
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        //if mContentFragment not FragmentMain type.use showFragmentMain() method
        if (!(mContentFragment instanceof FragmentMain)){
            showFragmentMain();
            return;
        }

        if (slidingMenu.isMenuShowing()){
            slidingMenu.showContent();
        }else{
            exitTwice();
        }
    }

    //两次退出
    private boolean isFirstExit=true;
    private void exitTwice(){
        if (isFirstExit){
            Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
            isFirstExit=false;
            new Thread(){
                public void run(){
                    try {
                        Thread.sleep(3*1000);
                        isFirstExit=true;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }else{
            finish();
        }
    }

    public void changeFragmentUser(){

    }

    public void setmContentFragment(Fragment fragment){
        this.mContentFragment=fragment;
    }

    public Fragment getmContentFragment(){
        return this.mContentFragment;
    }

    /**
     * 更换当前界面的title
     * @param title 界面的title
     */
    private void setTitle(String title){
        textView_title.setText(title);
    }
}
