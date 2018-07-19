package com.tanker.homemodule.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tanker.basemodule.adapter.MultiItemTypeAdapter;
import com.tanker.basemodule.base.BaseActivity;
import com.tanker.basemodule.base.CustomToolbar;
import com.tanker.basemodule.model.home_model.BillCostTypeModel;
import com.tanker.homemodule.R;
import com.tanker.homemodule.adapter.BillCostTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
* @author lwj
* @ClassName: AddBillActivity
* @Description:  账本添加界面
* @date 2018/7/17 16:19
*/
public class AddBillActivity extends BaseActivity {
    //类别RecyclerView控件
    private RecyclerView rv_type;
    //类别数据集合
    private List<BillCostTypeModel> datas;
    //类别适配器
    private BillCostTypeAdapter typeAdapter;
    @Override
    protected void initView() {
        rv_type=findViewById(R.id.rv_type);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        rv_type.setLayoutManager(gridLayoutManager);
        //获取费用类别集合数据
        getCostTypes();
        //类别适配器
        typeAdapter = new BillCostTypeAdapter(mContext, R.layout.homemodule_recycle_bill_choose_type_item, datas);
        rv_type.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                showMessage(datas.get(position).toString());
                BillCostTypeModel model=datas.get(position);
                for(BillCostTypeModel typeModel:datas) {
                    typeModel.setSelected(false);
                }
                model.setSelected(true);
                datas.set(position, model);//修改对应的元素
                typeAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

    }

    /**
     *  @author lwj
     *  @describe 费用类型数据集合
     *  @params
     *  @time 2018/7/18 16:29
     */
    private void getCostTypes() {
        datas = new ArrayList<>();
        BillCostTypeModel costTypeModel1=new BillCostTypeModel("1","过路费",false);
        BillCostTypeModel costTypeModel2=new BillCostTypeModel("2","加油费",false);
        BillCostTypeModel costTypeModel3=new BillCostTypeModel("3","维修费",false);
        BillCostTypeModel costTypeModel4=new BillCostTypeModel("4","罚款",false);
        BillCostTypeModel costTypeModel5=new BillCostTypeModel("5","住宿费",false);
        BillCostTypeModel costTypeModel6=new BillCostTypeModel("6","其它",false);
        datas.add(costTypeModel1);
        datas.add(costTypeModel2);
        datas.add(costTypeModel3);
        datas.add(costTypeModel4);
        datas.add(costTypeModel5);
        datas.add(costTypeModel6);
    }

    @Override
    public int getContentView() {
       return R.layout.homemodule_activity_bill_add;
    }

    @Override
    public void configToolbar(CustomToolbar rToolbar) {
        rToolbar.setTitle(mContext.getString(R.string.homemodule_title_bill_add));
    }
}
