package com.hrc.myapplication.ui;

import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hrc.myapplication.R;
import com.hrc.myapplication.common.LoadImage;
import com.hrc.myapplication.common.LogUtil;
import com.hrc.myapplication.receiver.DownloadCompleteReceiver;

/**
 * 右边侧拉界面
 */

public class FragmentMenuRight extends Fragment implements LoadImage.ImageLoadListener{
    private View view;
    private RelativeLayout relativeLayout_unlogin;
    private RelativeLayout relativeLayout_login;
    private boolean islogin;
    private SharedPreferences sharedPreferences;
    private ImageView imageView1,iv_pic;
    private TextView textView1,updateTv;
    private String [] str;
    DownloadCompleteReceiver receiver;
    /**
     * 分享到微信
     */
    private ImageView iv_friend;
    /**
     * 分享到qq
     */
    private ImageView iv_qq;
    /**
     * 分享到朋友圈
     */
    private ImageView iv_friends;
    /**
     * 分享到微博
     */
    private ImageView iv_weibo;
    /**
     * 分享位置规定
     */
    private static final int WEBCHAT=1,QQ=2,WEBCHATMOMENTS=3,SINA=4;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_menu_right,container,false);
        sharedPreferences=getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        islogin=sharedPreferences.getBoolean("islogin",false);
        relativeLayout_unlogin= (RelativeLayout) view.findViewById(R.id.relativelayout_unlogin);
        relativeLayout_login= (RelativeLayout) view.findViewById(R.id.relativelayout_logined);
        imageView1= (ImageView) view.findViewById(R.id.imageView1);
        textView1= (TextView) view.findViewById(R.id.textView1);
        updateTv= (TextView) view.findViewById(R.id.update_version);
        //初始化分享功能控件
        iv_friend= (ImageView) view.findViewById(R.id.fun_friend);
        iv_qq= (ImageView) view.findViewById(R.id.fun_qq);
        iv_friends= (ImageView) view.findViewById(R.id.fun_friends);
        iv_weibo= (ImageView) view.findViewById(R.id.fun_weibo);

        imageView1.setOnClickListener(l);
        textView1.setOnClickListener(l);

        iv_friend.setOnClickListener(l);
        iv_qq.setOnClickListener(l);
        iv_friends.setOnClickListener(l);
        iv_weibo.setOnClickListener(l);
        //用户登录后
        relativeLayout_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"跳转至用户主页",Toast.LENGTH_SHORT).show();
            }
        });
        receiver=new DownloadCompleteReceiver();
        //版本更新
        updateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"检测版本是否需要更新",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void imageLoadOk(Bitmap bitmap, String url) {

    }

    private View.OnClickListener l=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //判断登录
            if (v.getId()==R.id.imageView1||v.getId()==R.id.textView1){
                ((ActivityMain)getActivity()).showFragmentLogin();
            }
            //判断分享
            switch (v.getId()){
                case R.id.fun_friend:
                    Toast.makeText(getActivity(),"微信分享",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fun_qq:
                    Toast.makeText(getActivity(),"QQ分享",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fun_friends:
                    Toast.makeText(getActivity(),"朋友圈分享",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.fun_weibo:
                    Toast.makeText(getActivity(),"微博分享",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(receiver);
    }
}
