package com.tanker.basemodule.adapter;

import java.util.List;

/**
 * Created by ronny on 2018/3/29.
 */

public interface ItemViewDelegate<T> {
    int getItemViewLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

    void convert(ViewHolder holder, T t, int position, List<Object> payloads);
}
