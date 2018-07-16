package com.tanker.homemodule.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.tanker.basemodule.adapter.CommonAdapter;
import com.tanker.basemodule.adapter.ViewHolder;
import com.tanker.basemodule.model.home_model.BillDetailModel;
import com.tanker.basemodule.model.home_model.BillModel;
import com.tanker.homemodule.R;

import java.util.List;

/**
* @author lwj
* @ClassName: BillDetailAdapter
* @Description: 账单详情列表适配器
* @date 2018/7/16 14:02
*/
public class BillDetailAdapter extends CommonAdapter<BillDetailModel> {
    public BillDetailAdapter(Context context, int layoutId, List<BillDetailModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, BillDetailModel model, int position) {
        //账单月份
        TextView tv_bill_month = holder.getView(R.id.tv_bill_month);
    }
}
