package com.tanker.resmodule.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * author zhanglei
 * date 2018/5/2 14:08
 * description 列表间距
*/
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int horizontalSpace;
    private int verticalSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = horizontalSpace;
        outRect.right = horizontalSpace;
        outRect.bottom = verticalSpace;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = verticalSpace;
        }

    }

    public SpaceItemDecoration(int horizontalSpace, int verticalSpace) {
        this.horizontalSpace = horizontalSpace;
        this.verticalSpace = verticalSpace;
    }
}