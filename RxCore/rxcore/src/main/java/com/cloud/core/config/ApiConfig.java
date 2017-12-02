package com.cloud.core.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/17
 * @Description:接口参数配置
 * @Modifier:
 * @ModifyContent:
 */
public class ApiConfig {
    /**
     * API返回码(正确回调)
     */
    private List<String> apiRet = Arrays.asList("R0001", "200", "success");
    /**
     * 在接口请求时对包含的特定的接口名称不做rcd=apiRet验证
     */
    private List<String> apiSpecificNameFilter = null;
    /**
     * API返回码(未登录)
     */
    private List<String> apiUnlogin = null;
    /**
     * API消息提示过滤返回码
     */
    private List<String> apiMessagePromptFilter = null;
    /**
     * api名称
     */
    private String apiName = "2f9c0897aea7489f86a047345fb4a527";
    /**
     * api返回结果json
     */
    private String apiResultJson = "8e077672c03c42728af0351bd817efb1";

    public List<String> getApiRet() {
        if (apiRet == null) {
            apiRet = new ArrayList<String>();
        }
        return apiRet;
    }

    public void setApiRet(List<String> apiRet) {
        this.apiRet = apiRet;
    }

    public List<String> getApiSpecificNameFilter() {
        if (apiSpecificNameFilter == null) {
            apiSpecificNameFilter = new ArrayList<String>();
        }
        return apiSpecificNameFilter;
    }

    public void setApiSpecificNameFilter(List<String> apiSpecificNameFilter) {
        this.apiSpecificNameFilter = apiSpecificNameFilter;
    }

    public List<String> getApiUnlogin() {
        if (apiUnlogin == null) {
            apiUnlogin = new ArrayList<String>();
        }
        return apiUnlogin;
    }

    public void setApiUnlogin(List<String> apiUnlogin) {
        this.apiUnlogin = apiUnlogin;
    }

    public List<String> getApiMessagePromptFilter() {
        if (apiMessagePromptFilter == null) {
            apiMessagePromptFilter = new ArrayList<String>();
        }
        return apiMessagePromptFilter;
    }

    public void setApiMessagePromptFilter(List<String> apiMessagePromptFilter) {
        this.apiMessagePromptFilter = apiMessagePromptFilter;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiResultJson() {
        return apiResultJson;
    }

    public void setApiResultJson(String apiResultJson) {
        this.apiResultJson = apiResultJson;
    }
}
