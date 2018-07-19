package com.tanker.homemodule.presenter;

import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.home_model.FaultDetailModel;
import com.tanker.basemodule.utils.FileUtils;
import com.tanker.basemodule.utils.ImageUtils;
import com.tanker.basemodule.utils.ShowMassageUtil;
import com.tanker.homemodule.api.HomeApi;
import com.tanker.homemodule.contract.FaultAddContract;

import java.io.File;

import io.reactivex.Observable;

/**
 * author zhanglei
 * date 2018/7/16
 * description 故障上报数据返回
 **/
public class FaultAddPresenter extends FaultAddContract.Presenter {


    public FaultAddPresenter(FaultAddContract.View view) {
        super(view);
    }

    @Override
    public void addFault() {
        Observable<HttpResult<FaultDetailModel>> resultObservable = HomeApi.getInstance().addFault();
        toSubscribe(resultObservable, new CommonObserver<FaultDetailModel>(mView.getContext()) {
            @Override
            public void onNext(FaultDetailModel model) {
//                    Intent intent = new Intent(mView.getContext(), FaultDetailActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra("model", model);
//                    mView.navigationTo(intent);
//                    mView.getContext().finish();
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.showMessage(t.message);
            }
        });
    }

    @Override
    public void upload(String path) {
        String tempPath = FileUtils.getTempPicFile() + File.separator + "upload" + ".jpg";
        ImageUtils.compressImg(path, tempPath, 800);
        Observable<HttpResult<String>> resultObservable = HomeApi.getInstance().uploadFile(tempPath);
        toSubscribe(resultObservable, new CommonObserver<String>(mView.getContext(), false) {
            @Override
            public void onNext(String netPath) {
                mView.refreshImageView(netPath);
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                if (t.code == ExceptionEngine.ERROR.NETWORD_ERROR) {
                    ShowMassageUtil.showErrorToast(t.message);
                }
            }
        }, false);
    }
}
