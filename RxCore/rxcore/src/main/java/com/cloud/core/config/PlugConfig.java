package com.cloud.core.config;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/17
 * @Description:插件配置
 * @Modifier:
 * @ModifyContent:
 */
public class PlugConfig {
    /**
     * 插件页面跳转
     */
    private String plugGoPage = "9c93d818bab349ce906081cc523de1c2";
    /**
     * 插件停止
     */
    private String plugStop = "c92c1fa4c5a942b9865bd2b3f652378e";

    public String getPlugGoPage() {
        return plugGoPage;
    }

    public void setPlugGoPage(String plugGoPage) {
        this.plugGoPage = plugGoPage;
    }

    public String getPlugStop() {
        return plugStop;
    }

    public void setPlugStop(String plugStop) {
        this.plugStop = plugStop;
    }
}
