package com.sctdroid.app.samples.modules.gallery;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by lixindong on 2018/4/21.
 */

public abstract class GridDecoration extends RecyclerView.ItemDecoration {
    private int   lineWidth;//px 分割线宽

    public GridDecoration(Context context, float lineWidthDp) {
        this.lineWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, lineWidthDp, context.getResources().getDisplayMetrics());
    }

    public GridDecoration(Context context, int lineWidthDp) {
        this(context, (float) lineWidthDp);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        boolean[] sideOffsetBooleans = getItemSidesIsHaveOffsets(itemPosition);

        //如果是设置左边或者右边的边距，就只设置成指定宽度的一半，
        // 因为这个项目中的 Grid 是一行二列，如果不除以二的话，那么中间的间距就会很宽，
        //可根据实际项目需要修改成合适的值
        int left = sideOffsetBooleans[0] ? lineWidth / 2 : 0;
        int top = sideOffsetBooleans[1] ? lineWidth / 2 : 0;
        int right = sideOffsetBooleans[2] ? lineWidth / 2 : 0;
        int bottom = sideOffsetBooleans[3] ? lineWidth / 2 : 0;

        outRect.set(left, top, right, bottom);

    }

    abstract boolean[] getItemSidesIsHaveOffsets(int itemPosition);
}
