package com.tanker.basemodule.common;

import com.orhanobut.hawk.Hawk;
import com.tanker.basemodule.AppConstants;
import com.tanker.basemodule.model.login_model.ConfigInfo;

public class ConfigManager {

    private ConfigInfo configInfo;

    public ConfigManager() {
        if (configInfo == null) {
            configInfo = Hawk.get(AppConstants.HAWK_CONFIG);
        }
    }

    public ConfigInfo getConfigInfo() {
        if (configInfo == null) {
            configInfo = Hawk.get(AppConstants.HAWK_CONFIG);
        }
        return configInfo;
    }

    public void setConfigInfo(ConfigInfo configInfo) {
        Hawk.delete(AppConstants.HAWK_CONFIG);
        Hawk.put(AppConstants.HAWK_CONFIG, configInfo);
        this.configInfo = configInfo;
    }

    public boolean isNeedUpdate() {
        return configInfo.isNeedUpdateJson();
    }

    public void setNeedUpdate(boolean isNeedUpdate) {
        configInfo.setNeedUpdate(isNeedUpdate);
        setConfigInfo(configInfo);
    }
}
