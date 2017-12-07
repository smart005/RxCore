package com.cloud.core.configs;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/12/5
 * @Description:配置项url监听
 * @Modifier:
 * @ModifyContent:
 */
public interface OnConfigItemUrlListener {
    /**
     * 获取配置项url
     *
     * @param urlType url类型
     * @return
     */
    public String getUrl(String urlType);
}
