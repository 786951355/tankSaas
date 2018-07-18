package com.tanker.homemodule.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.tanker.basemodule.adapter.CommonAdapter;
import com.tanker.basemodule.adapter.ViewHolder;
import com.tanker.basemodule.model.home_model.BillDetailModel;
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
        //运单号
        TextView tv_bill_detail_no = holder.getView(R.id.tv_bill_detail_no);
        tv_bill_detail_no.setText(model.getOrderNo());
        //账单状态
        TextView tv_bill_detail_status = holder.getView(R.id.tv_bill_detail_status);
        tv_bill_detail_status.setText(model.getBillstatusName());
        //起点
        TextView tv_bill_detail_startAreaName = holder.getView(R.id.tv_bill_detail_startAreaName);
        tv_bill_detail_startAreaName.setText(model.getBillstartAreaName());
        //卸点
        TextView tv_bill_detail_endAreaName = holder.getView(R.id.tv_bill_detail_endAreaName);
        tv_bill_detail_endAreaName.setText(model.getBillendAreaName());
        //发单时间
        TextView tv_bill_detail_time = holder.getView(R.id.tv_bill_detail_time);
        String billTime=model.getBillTime();
        if(!TextUtils.isEmpty(billTime)){
            tv_bill_detail_time.setText(mContext.getString(R.string.homemodule_bill_detail_order_time_str)+billTime);
        }
        //账单金额
        TextView tv_bill_detail_money = holder.getView(R.id.tv_bill_detail_money);
        String billMoney=model.getBillMoney();
        if(!TextUtils.isEmpty(billMoney)){
            tv_bill_detail_money.setText(mContext.getString(R.string.homemodule_bill_detail_money_str)+billMoney);
        }
    }
}
