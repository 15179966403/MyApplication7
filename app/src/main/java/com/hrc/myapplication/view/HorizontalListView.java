package com.hrc.myapplication.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Scroller;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 横向滑动的ListView
 */

public class HorizontalListView extends AdapterView {
    public boolean mAlwaysOverrideTouch=true;

    /**适配器*/
    protected ListAdapter mAdapter;
    /**左视图索引*/
    private int mLeftViewIndex=-1;
    /**右视图索引*/
    private int mRightViewIndex=0;
    /**当前x坐标*/
    protected int mCurrentX;
    /**下一个X坐标*/
    protected int mNextX;
    /**最大值*/
    private int mMaxX=Integer.MAX_VALUE;
    /**偏移显示*/
    private int mDisplayOffset=0;
    /**卷*/
    protected Scroller mScroller;
    /**手势识别*/
    private GestureDetector mGesture;
    /**移动view 的队列*/
    private Queue<View> mRemovedViewQueue=new LinkedList<View>();
    /**单项选择监听*/
    private OnItemSelectedListener mOnItemSelected;
    /**单项点击监听*/
    private OnItemClickListener mOnItemClicked;
    private boolean mDataChanged=false;

    public HorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private synchronized void initView() {
        mLeftViewIndex=-1;
        mRightViewIndex=0;
        mDisplayOffset=0;
        mCurrentX=0;
        mNextX=0;
        mMaxX=Integer.MAX_VALUE;
        mScroller=new Scroller(getContext());
//        mGesture=new GestureDetector(getContext(),)
    }

    @Override
    public Adapter getAdapter() {
        return null;
    }

    @Override
    public void setAdapter(Adapter adapter) {

    }

    @Override
    public View getSelectedView() {
        return null;
    }

    @Override
    public void setSelection(int position) {

    }

    protected boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
        synchronized (HorizontalListView.this){
            mScroller.fling(mNextX,0,(int)-velocityX,0,0,mMaxX,0,0);
        }
        requestLayout();
        return true;
    }

    protected boolean onDown(MotionEvent e){
        mScroller.forceFinished(true);
        return true;
    }

    /**手势监听*/
    private GestureDetector.OnGestureListener mOnGesture=new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onDown(MotionEvent e) {
            return HorizontalListView.this.onDown(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return HorizontalListView.this.onFling(e1,e2,velocityX,velocityY);
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            synchronized (HorizontalListView.this){
                mNextX+=(int)distanceX;
            }
            requestLayout();
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Rect viewRect=new Rect();
            for (int i=0;i<getChildCount();i++){
                View child=getChildAt(i);
                int left=child.getLeft();
                int right=child.getRight();
                int top=child.getTop();
                int bottom=child.getBottom();
                viewRect.set(left,top,right,bottom);
                if (viewRect.contains((int)e.getX(),(int)e.getY())){
                    if (mOnItemClicked!=null){
                        //mOnItemClicked.onItemClick(HorizontalListView.this,child,);
                    }
                }
            }
            return super.onSingleTapConfirmed(e);
        }
    };
}
