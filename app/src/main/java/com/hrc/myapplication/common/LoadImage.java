package com.hrc.myapplication.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 加载图片
 */

public class LoadImage {
    private static LruCache<String,Bitmap> cache=new LruCache<String,Bitmap>(1024);

    private Context context;
    private ImageLoadListener listener;
    public LoadImage(Context context,ImageLoadListener listener){
        this.context=context;
        this.listener=listener;
    }

    public interface ImageLoadListener{
        void imageLoadOk(Bitmap bitmap,String url);
    }

    public Bitmap getBitmap(String url){
        Bitmap bitmap=null;
        if (url==null||url.length()<=0){
            return bitmap;
        }
        //内存中是否存在
        bitmap=getBitmapFromReference(url);
        if (bitmap!=null){
            LogUtil.d("1.内存中的图片");
            return bitmap;
        }
        //缓存文件中是否存在
        bitmap=getBitmapFromCache(url);
        if (bitmap!=null){
            //存入内存
            cache.put(url,bitmap);
            LogUtil.d("2.缓存中的图片");
            return bitmap;
        }
        //异步加载文件
        getBitmapAsync(url);
        return bitmap;
    }

    private void getBitmapAsync(String url) {
        ImageAsyncTask imageAsyncTask=new ImageAsyncTask();
        imageAsyncTask.execute(url);
    }

    private Bitmap getBitmapFromCache(String url) {
        String name=url.substring(url.lastIndexOf("/")+1);
        File cacheDir=context.getExternalCacheDir();
        if (cacheDir==null)
            return null;
        File[] files=cacheDir.listFiles();
        if (files==null){
            return null;
        }
        File bitmapFile=null;
        for (File file:files){
            if (file.getName().equals(name)){
                bitmapFile=file;
                break;
            }
        }
        if (bitmapFile==null){
            return null;
        }
        Bitmap bitmap= BitmapFactory.decodeFile(bitmapFile.getAbsolutePath());
        if (bitmap==null){
            return null;
        }
        return bitmap;
    }

    private Bitmap getBitmapFromReference(String url) {
        Bitmap bitmap=null;
        bitmap=cache.get(url);
        return bitmap;
    }

    private class ImageAsyncTask extends AsyncTask<String,Void,Bitmap>{
        private String url;

        @Override
        protected Bitmap doInBackground(String... params) {
            url=params[0];
            Bitmap bitmap=null;
            try{
                URL url=new URL(params[0]);
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                InputStream is=conn.getInputStream();
                bitmap=BitmapFactory.decodeStream(is);
                cache.put(params[0],bitmap);
                //缓存文件
                saveCacheFile(params[0],bitmap);
                LogUtil.d("网络中的图片");
            }catch (Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (listener!=null){
                listener.imageLoadOk(bitmap,url);
            }
        }
    }
    public void saveCacheFile(String url,Bitmap bitmap){
        String name=url.substring(url.lastIndexOf("/")+1);
        File cacheDir=context.getExternalCacheDir();
        if (!cacheDir.exists()){
            cacheDir.mkdirs();
        }
        OutputStream stream;
        try{
            stream = new FileOutputStream(new File(cacheDir,name));
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
