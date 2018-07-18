package com.tanker.homemodule.constants;

public class HomeConstants {
    /*账单*/
    /**
     * 账单状态 1：已确认 2：待确认
     */
    public interface BILL_STATUS {
        /**
         * 已确认
         */
        int STATUS_CONFIRM = 1;
        /**
         * 待确认
         */
        int STATUS_NO_CONFIRM = 2;
    }
    /**
     * 运单号
      */
    public static final String ORDER_NO = "orderNo";
}
