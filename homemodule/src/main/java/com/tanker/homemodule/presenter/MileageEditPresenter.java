package com.tanker.homemodule.presenter;

import com.tanker.basemodule.http.CommonObserver;
import com.tanker.basemodule.http.ExceptionEngine;
import com.tanker.basemodule.http.api.HttpResult;
import com.tanker.basemodule.model.home_model.MileageDetailModel;
import com.tanker.basemodule.utils.FileUtils;
import com.tanker.basemodule.utils.ImageUtils;
import com.tanker.basemodule.utils.ShowMassageUtil;
import com.tanker.homemodule.api.HomeApi;
import com.tanker.homemodule.contract.MileageEditContract;

import java.io.File;

import io.reactivex.Observable;

/**
 * author zhanglei
 * date 2018/7/16
 * description 里程上报数据返回
 **/
public class MileageEditPresenter extends MileageEditContract.Presenter {


    public MileageEditPresenter(MileageEditContract.View view) {
        super(view);
    }

    @Override
    public void getMileageDetail() {
        Observable<HttpResult<MileageDetailModel>> resultObservable = HomeApi.getInstance().getMileageDetail();
        toSubscribe(resultObservable, new CommonObserver<MileageDetailModel>(mView.getContext()) {
            @Override
            public void onNext(MileageDetailModel mileageDetailModel) {
                mView.refreshUI(mileageDetailModel);
            }

            @Override
            public void onError(ExceptionEngine.ResponseThrowable t) {
                mView.showMessage(t.message);
            }
        });
    }

    @Override
    public void addMileage() {
        Observable<HttpResult<MileageDetailModel>> resultObservable = HomeApi.getInstance().addOrUpdateMileage();
        toSubscribe(resultObservable, new CommonObserver<MileageDetailModel>(mView.getContext()) {
            @Override
            public void onNext(MileageDetailModel model) {
//                int start = mView.getContext().getIntent().getIntExtra(AppConstants.INTENT_START, -1);
//                if (start == AppConstants.FROM_DISPATCHER_CAR) {
//                    Intent intent = new Intent();
//                    intent.putExtra(AppConstants.INTENT_DATA, model);
//                    mView.getContext().setResult(AppConstants.ADD_DRIVER_RESULT_CODE, intent);
//                    mView.getContext().finish();
//                } else {
//                    Intent intent = new Intent(mView.getContext(), DriverDetailActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.putExtra(MineConstants.DRIVER_MODEL, model);
//                    intent.putExtra(AppConstants.DRIVER_ROLE, model.getRole());
//                    mView.navigationTo(intent);
//                    mView.getContext().finish();
//                }
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
