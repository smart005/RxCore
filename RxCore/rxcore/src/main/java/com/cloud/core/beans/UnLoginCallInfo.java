package com.cloud.core.beans;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2017/11/29
 * Description:未登录检测返回信息
 * Modifier:
 * ModifyContent:
 */
public class UnLoginCallInfo {
    /**
     * api名称
     */
    private String apiName = "";
    /**
     * api返回信息
     */
    private String response = "";

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
