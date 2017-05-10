package com.hrc.myapplication.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Toast;

import com.hrc.myapplication.NAdapter;
import com.hrc.myapplication.R;
import com.hrc.myapplication.common.CommonUtil;
import com.hrc.myapplication.common.LogUtil;
import com.hrc.myapplication.model.entity.News;
import com.hrc.myapplication.ui.adapter.NewsTypeAdapter;
import com.hrc.myapplication.view.xlistview.XListView;

/**
 * 新闻列表界面
 */

public class FragmentMain extends Fragment {
    //填充view
    private View view;
    //新闻列表
    private XListView listView;
    //新闻适配器
//    private NewsAdapter newsAdapter;
    private NAdapter adapter;
    //分类列表
    private HorizontalScrollView hl_type;
    //分类适配器
    private NewsTypeAdapter typeAdapter;
    //新闻分类编号 默认为1
    private int subId=1;
    //模式 1上拉 2下拉
    private int mode;
    //数据库

    //当前Activity
    private ActivityMain mainActivity;
    //更多分类
    private ImageView btn_moretype;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            i();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_newslist,container,false);
        mainActivity= (ActivityMain) getActivity();
        listView= (XListView) view.findViewById(R.id.listview);
        btn_moretype= (ImageView) view.findViewById(R.id.iv_moretype);
        btn_moretype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"更多分类",Toast.LENGTH_SHORT).show();
            }
        });
        //加载新闻分类
        //加载新闻列表
//        if (newsAdapter==null){
//            newsAdapter=new NewsAdapter(getActivity(),listView);
//        }
        adapter=new NAdapter(getActivity());
        for (int i=0;i<20;i++){
            News news=new News(i,i,"第"+i,null,"标题:"+i,"summary:"+i,"link:"+i);
            adapter.appendData(news,false);
        }
        listView.setAdapter(adapter);
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(ixListViewListener);
        listView.setOnItemClickListener(newsItemListener);
        return view;
    }

    private XListView.IXListViewListener ixListViewListener=new XListView.IXListViewListener() {
        @Override
        public void onRefresh() {
            LogUtil.d("模仿耗时线程");
            //加载数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(3*1000);
                        handler.sendEmptyMessage(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        }

        @Override
        public void onLoadMore() {
            //加载数据
        }
    };
    private void i(){
        listView.stopLoadMore();
        listView.stopRefresh();
        listView.setRefreshTime(CommonUtil.getSystime());
        LogUtil.d("耗时线程结束");
    }

    /**新闻单项点击事件*/
    private AdapterView.OnItemClickListener newsItemListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtil.d("新闻单项被点击");
        }
    };
}
