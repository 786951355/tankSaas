package com.tanker.basemodule.model.home_model;

import java.io.Serializable;

/**
 * 获取抢单列表顶部统计数字
 */
public class GraborderOrderCountModel implements Serializable {
    /**
     * 带抢单新增条数
     */
    private String awaitOrderCount;
    /**
     * 已报价新增条数
     */
    private String aleadQuoteOrderCount;
    /**
     * 竞价成功新增条数
     */
    private String successBiddingOrder;

    public String getAwaitOrderCount() {
        return awaitOrderCount;
    }

    public void setAwaitOrderCount(String awaitOrderCount) {
        this.awaitOrderCount = awaitOrderCount;
    }

    public String getAleadQuoteOrderCount() {
        return aleadQuoteOrderCount;
    }

    public void setAleadQuoteOrderCount(String aleadQuoteOrderCount) {
        this.aleadQuoteOrderCount = aleadQuoteOrderCount;
    }

    public String getSuccessBiddingOrder() {
        return successBiddingOrder;
    }

    public void setSuccessBiddingOrder(String successBiddingOrder) {
        this.successBiddingOrder = successBiddingOrder;
    }
}
