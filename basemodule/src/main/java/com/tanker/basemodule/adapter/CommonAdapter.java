package com.tanker.basemodule.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.List;

/**
 * Created by ronny on 2018/3/25.
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    //    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    public CommonAdapter(final Context context, final int layoutId, List<T> datas) {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }

            @Override
            public void convert(ViewHolder holder, T t, int position, List<Object> payloads) {
                CommonAdapter.this.convert(holder, t, position, payloads);
            }
        });
    }

    public abstract void convert(ViewHolder holder, T t, int position);

    public void convert(ViewHolder holder, T t, int position, List<Object> payloads) {
    }


    //    public CommonAdapter(Context context, int layoutId, List<T> datas)
//    {
//        mContext = context;
//        mInflater = LayoutInflater.from(context);
//        mLayoutId = layoutId;
//        if (datas==null){
//            mDatas=new ArrayList<>();
//        }else{
//            mDatas = datas;
//        }
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
//    {
//        ViewHolder viewHolder = ViewHolder.createViewHolder(mContext, parent, mLayoutId);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position)
//    {
//        convert(holder, mDatas.get(position),position);
//    }
//
//
//    @Override
//    public int getItemCount()
//    {
//        return mDatas.size();
//    }
//
//    public void addData(List<T> moreData){
//        mDatas.putAll(moreData);
//    }
//
    public void setData(List<T> newData) {
        mDatas = newData;
    }
//
//    public T getItem(int position) {
//        return mDatas.get(position);
//    }
}
