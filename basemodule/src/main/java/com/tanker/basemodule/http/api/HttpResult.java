package com.tanker.basemodule.http.api;

/**
 * 网络返回基类 支持泛型
 * Created by Ronny on 2018/3/22.
 */
public class HttpResult<T> {
    private T data;
    private String code;
    private String message;
    private String requestUUID;
    private String responseTime;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestUUID() {
        return requestUUID;
    }

    public void setRequestUUID(String requestUUID) {
        this.requestUUID = requestUUID;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public boolean isOk() {
        if (code == null) {
            code = "999";
        }
        return code.equals("0") || code.isEmpty();
    }


//  private String code="";
//  private String message ="";
//  private String responseTime="";
//  private T data;
//  private String requestUUID="";


    /**---------------------------------


     public String getRequestUUID() {
     return requestUUID;
     }

     public void setRequestUUID(String requestUUID) {
     this.requestUUID = requestUUID;
     }

     public String getCode() {
     return code;
     }

     public void setCode(String code) {
     this.code = code;
     }

     public String getResponseTime() {
     return responseTime;
     }

     public void setResponseTime(String responseTime) {
     this.responseTime = responseTime;
     }

     public String getMessage() {
     return message;
     }

     public void setMessage(String message) {
     this.message = message;
     }

     public T getData() {
     return data;
     }

     public void setData(T data) {
     this.data = data;
     }

     public boolean isOk() {
     if (code==null){
     code="999";
     }
     return code.equals("0")||code.isEmpty();
     }


     **/


}
