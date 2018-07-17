package com.tanker.homemodule.presenter;

import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.home_model.BillDetailListModel;
import com.tanker.basemodule.model.home_model.BillDetailModel;
import com.tanker.homemodule.api.HomeApi;
import com.tanker.homemodule.contract.BillDetailContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
* @author lwj
* @ClassName: BillPresenter
* @Description:  账单详情
* @date 2018/7/16 16:44
*/
public class BillDetailPresenter extends BillDetailContract.Presenter {
    private static final String TAG = BillDetailPresenter.class.getName();

    public BillDetailPresenter(BillDetailContract.View view) {
        super(view);
    }

    /**
     *  @author lwj
     *  @describe 获取账单列表数据
     *  @params
     *  @time 2018/7/16 14:22
     */
    @Override
    public void getBillDetailInfoList(int page) {
        Observable<HttpResult<BillDetailListModel>> resultObservable = HomeApi.getInstance().getBillDetailInfoList(page+"");
        toSubscribe(resultObservable, new CommonObserver<BillDetailListModel>(mView.getContext(),false) {
            @Override
            public void onNext(BillDetailListModel model) {
                mView.dismissSwipeRefresh();
                mView.getBillDetailInfoList(page,getBillInfoList());
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.dismissSwipeRefresh();
                mView.showMessage(t.message);
                mView.getBillDetailInfoList(page,getBillInfoList());
            }

        });
    }

    private BillDetailListModel getBillInfoList(){
        BillDetailListModel model=new BillDetailListModel();
        List<BillDetailModel> billInfoList=new ArrayList<>();
        BillDetailModel billModel;
        for (int i = 1; i < 15; i++) {
            billModel=new BillDetailModel();
            billModel.setBillId(i+"");
            billModel.setOrderNo("Y100716000"+i);
            if(i%2==0){
                billModel.setBillstatus("1");
            }else{
                billModel.setBillstatus("2");
            }

            billModel.setBillstartAreaName("河南马鞍山河口厂区（华翔）");
            billModel.setBillendAreaName("上海杨浦区五角场百联又一城（嘉联）");
            billModel.setBillTime("2018-07-16 17:0"+i);
            billModel.setBillMoney("2000.0"+i);
            billInfoList.add(billModel);
        }
        model.setTotal("1");
        model.setBillDetailInfoList(billInfoList);
        return model;
    }
}
