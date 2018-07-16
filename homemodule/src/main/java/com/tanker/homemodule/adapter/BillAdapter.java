package com.tanker.homemodule.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tanker.basemodule.adapter.CommonAdapter;
import com.tanker.basemodule.adapter.ViewHolder;
import com.tanker.basemodule.model.home_model.BillModel;
import com.tanker.basemodule.utils.StringUtils;
import com.tanker.homemodule.R;

import java.util.List;

/**
* @author lwj
* @ClassName: BillAdapter
* @Description: 账单列表适配器
* @date 2018/7/16 14:02
*/
public class BillAdapter extends CommonAdapter<BillModel> {
    public BillAdapter(Context context, int layoutId, List<BillModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, BillModel model, int position) {
        //账单月份
        TextView tv_bill_month = holder.getView(R.id.tv_bill_month);
        tv_bill_month.setText(model.getBillTime());
        //待确认金额
        TextView tv_bill_noconfirm_money = holder.getView(R.id.tv_bill_noconfirm_money);
        String noconfirmMoney=model.getBillNoConfirmMoney();
        if(!TextUtils.isEmpty(noconfirmMoney)){
            tv_bill_noconfirm_money.setVisibility(View.VISIBLE);
            tv_bill_noconfirm_money.setText(mContext.getString(R.string.homemodule_bills_noconfirm_money_txt)+noconfirmMoney);
        }else{
            tv_bill_noconfirm_money.setVisibility(View.GONE);
        }
        //已确认金额
        TextView tv_bill_confirm_money = holder.getView(R.id.tv_bill_confirm_money);
        tv_bill_confirm_money.setText(mContext.getString(R.string.homemodule_bills_confirm_money_txt)+model.getBillConfirmMoney());

    }
}
