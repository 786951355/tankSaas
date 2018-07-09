package com.zhaoguanche.setting.presenter;

import com.zhaoguanche.setting.contract.SettingContract;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class SettingPresenter extends SettingContract.Presenter {

    public SettingPresenter(SettingContract.View view) {
        super(view);
    }

}
