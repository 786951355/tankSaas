package com.tanker.basemodule.base;

/**
 * Created by :luore
 * Date: 2017/8/19
 */

public interface BasePresenter {
    /**
     * 默认的开始,在activity中初始化
     */
    void start();

    /**
     * 在activity中的ondestoy 调用 在此方法中将资源至null,
     * 此处略嫌麻烦,但是如果把presenter层搞成抽象类,在里面添加成员变量和方法体,
     * 就有点失去了味道,所以还是选择了这种方式代替下列注释的部分.
     */
    void onDestroy();
}
