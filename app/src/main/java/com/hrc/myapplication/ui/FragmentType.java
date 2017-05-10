package com.hrc.myapplication.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrc.myapplication.R;
import com.hrc.myapplication.common.LogUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 更多分类页面
 */

public class FragmentType extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_type,container,false);
        TextView tv= (TextView) view.findViewById(R.id.textview);
        //tv.setText(getString());
        LogUtil.d("分类");
        return view;
    }

    private String getString(){
        String s="";
        try {
            URL url=new URL("10.0.2.2/newsClient/Images/2.jpg");
            HttpURLConnection http= (HttpURLConnection) url.openConnection();
            http.setRequestMethod("GET");
            if (http.getResponseCode()==200){
                InputStream in=http.getInputStream();
                BufferedReader reader=new BufferedReader(new InputStreamReader(in));
                StringBuilder sb=null;
                String line=null;
                byte[] b=new byte[1024];
                while((line=reader.readLine())!=null){
                    sb.append(line+"\n");
                }
                s=sb.toString();
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
