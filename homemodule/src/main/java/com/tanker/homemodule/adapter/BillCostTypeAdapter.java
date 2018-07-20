package com.tanker.homemodule.adapter;


import android.content.Context;
import android.widget.Button;

import com.tanker.basemodule.adapter.CommonAdapter;
import com.tanker.basemodule.adapter.ViewHolder;
import com.tanker.basemodule.model.home_model.BillCostTypeModel;
import com.tanker.homemodule.R;

import java.util.List;

/**
* @author lwj
* @ClassName: BillCostTypeAdapter
* @Description:  账单添加-费用类型选择适配器
* @date 2018/7/18 16:25
*/
public class BillCostTypeAdapter extends CommonAdapter<BillCostTypeModel> {

    public BillCostTypeAdapter(Context context, int layoutId, List<BillCostTypeModel> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final BillCostTypeModel model, final int position) {

        //费用类型
        Button btn_cost_type = holder.getView(R.id.btn_cost_type);
        btn_cost_type.setText(model.getValue());
        boolean isselected=model.isSelected();
        if(isselected){
            btn_cost_type.setSelected(isselected);
        }else{
            btn_cost_type.setSelected(isselected);
        }
    }


}
