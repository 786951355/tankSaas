package com.tanker.homemodule.presenter;

import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.home_model.BillListModel;
import com.tanker.basemodule.model.home_model.BillModel;
import com.tanker.homemodule.api.HomeApi;
import com.tanker.homemodule.contract.BillContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
* @author lwj
* @ClassName: BillPresenter
* @Description:  账单
* @date 2018/7/16 16:44
*/
public class BillPresenter extends BillContract.Presenter {
    private static final String TAG = BillPresenter.class.getName();

    public BillPresenter(BillContract.View view) {
        super(view);
    }

    /**
     *  @author lwj
     *  @describe 获取账单列表数据
     *  @params
     *  @time 2018/7/16 14:22
     */
    @Override
    public void getBillInfoList(int page) {
        Observable<HttpResult<BillListModel>> resultObservable = HomeApi.getInstance().getBillInfoList(page+"");
        toSubscribe(resultObservable, new CommonObserver<BillListModel>(mView.getContext(),false) {
            @Override
            public void onNext(BillListModel model) {
                mView.dismissSwipeRefresh();
                mView.getBillInfoList(page,getBillInfoList());
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.dismissSwipeRefresh();
                mView.showMessage(t.message);
                mView.getBillInfoList(page,getBillInfoList());
            }

        });
    }

    private BillListModel getBillInfoList(){
        BillListModel model=new BillListModel();
        List<BillModel> billInfoList=new ArrayList<>();
        BillModel billModel;
        for (int i = 7; i > 0; i--) {
            billModel=new BillModel();
            billModel.setBillId(i+"");
            billModel.setBillTime("2018-0"+i);
            billModel.setBillNoConfirmMoney("2000.0"+i);
            billModel.setBillConfirmMoney("3000.0"+i);
            billModel.setOrderNo("Y100716000"+i);
            billInfoList.add(billModel);
        }
        model.setTotal("1");
        model.setBillInfoList(billInfoList);
        return model;
    }
}
