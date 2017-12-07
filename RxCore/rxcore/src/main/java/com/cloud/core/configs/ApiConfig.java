package com.cloud.core.configs;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/12/5
 * @Description:api配置
 * @Modifier:
 * @ModifyContent:
 */
public class ApiConfig {
    /**
     * 接口消息提醒过滤
     */
    private List<String> apiMessagePromptFilter = null;
    /**
     * 接口请求时对包含的特定的接口名称,返回时不做验证
     */
    private List<String> apiSpecificNameFilter = null;
    /**
     * 接口请求成功返回码
     */
    private List<String> apiSuccessRet = null;
    /**
     * 接口未授权返回码
     */
    private List<String> apiUnauthorizedRet = null;

    public List<String> getApiMessagePromptFilter() {
        if (apiMessagePromptFilter == null) {
            apiMessagePromptFilter = new ArrayList<String>();
        }
        return apiMessagePromptFilter;
    }

    public void setApiMessagePromptFilter(List<String> apiMessagePromptFilter) {
        this.apiMessagePromptFilter = apiMessagePromptFilter;
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

    public List<String> getApiSuccessRet() {
        if (apiSuccessRet == null) {
            apiSuccessRet = new ArrayList<String>();
        }
        if (!apiSuccessRet.contains("R0001")) {
            apiSuccessRet.add("R0001");
        }
        if (!apiSuccessRet.contains("200")) {
            apiSuccessRet.add("200");
        }
        if (!apiSuccessRet.contains("success")) {
            apiSuccessRet.add("success");
        }
        return apiSuccessRet;
    }

    public void setApiSuccessRet(List<String> apiSuccessRet) {
        this.apiSuccessRet = apiSuccessRet;
    }

    public List<String> getApiUnauthorizedRet() {
        if (apiUnauthorizedRet == null) {
            apiUnauthorizedRet = new ArrayList<String>();
        }
        return apiUnauthorizedRet;
    }

    public void setApiUnauthorizedRet(List<String> apiUnauthorizedRet) {
        this.apiUnauthorizedRet = apiUnauthorizedRet;
    }
}
