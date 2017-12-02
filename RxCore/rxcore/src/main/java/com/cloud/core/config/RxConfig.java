package com.cloud.core.config;

import com.cloud.core.enums.PlatformType;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/17
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class RxConfig {
    /**
     * 应用默认用户token名称
     */
    private String tokenName = "";
    /**
     * 默认广播接收Action
     */
    private String rscReceiveAction = "481228604";
    /**
     * 参数KEY
     */
    private String argParam = "802826781";
    /**
     * 启动登录页面flag键
     */
    private final String startLoginFlagKey = "411237091";
    /**
     * api配置
     */
    private ApiConfig apiConfig = null;
    /**
     * 插件配置
     */
    private PlugConfig plugConfig = null;
    /**
     * 资源配置
     */
    private ResConfig resConfig = null;
    /**
     * 渠道名
     */
    private String channelName = "UMENG_CHANNEL";
    /**
     * app icon
     */
    private int appIcon = 0;
    /**
     * 主题颜色
     */
    private int themeColorResId = 0;
    /**
     * 提示更新的activity名称
     */
    private int updateActivityNamesResId = 0;
    /**
     * 过滤网络状态对应的activity名称
     */
    private int netStateActivityNamesResId = 0;
    /**
     * 平台类型
     */
    private PlatformType platformType = PlatformType.Alibaba;
    /**
     * 阿里信息配置
     */
    private AliConfig aliConfig = null;
    /**
     * 请求更新信息url
     */
    private String requestUpdateInfoUrl = "";

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getRscReceiveAction() {
        return rscReceiveAction;
    }

    public void setRscReceiveAction(String rscReceiveAction) {
        this.rscReceiveAction = rscReceiveAction;
    }

    public String getArgParam() {
        return argParam;
    }

    public void setArgParam(String argParam) {
        this.argParam = argParam;
    }

    public String getStartLoginFlagKey() {
        return startLoginFlagKey;
    }

    public ApiConfig getApiConfig() {
        if (apiConfig == null) {
            apiConfig = new ApiConfig();
        }
        return apiConfig;
    }

    public void setApiConfig(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
    }

    public PlugConfig getPlugConfig() {
        if (plugConfig == null) {
            plugConfig = new PlugConfig();
        }
        return plugConfig;
    }

    public void setPlugConfig(PlugConfig plugConfig) {
        this.plugConfig = plugConfig;
    }

    public ResConfig getResConfig() {
        if (resConfig == null) {
            resConfig = new ResConfig();
        }
        return resConfig;
    }

    public void setResConfig(ResConfig resConfig) {
        this.resConfig = resConfig;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * 获取应用图标
     */
    public int getAppIcon() {
        return appIcon;
    }

    /**
     * 设置应用图标
     *
     * @param appIcon
     */
    public void setAppIcon(int appIcon) {
        this.appIcon = appIcon;
    }

    /**
     * 获取主题颜色
     */
    public int getThemeColorResId() {
        return themeColorResId;
    }

    public void setThemeColorResId(int themeColorResId) {
        this.themeColorResId = themeColorResId;
    }

    public void setUpdateActivityNamesResId(int resId) {
        this.updateActivityNamesResId = resId;
    }

    public int getUpdateActivityNamesResId() {
        return this.updateActivityNamesResId;
    }

    public void setNetStateActivityNamesResId(int resId) {
        this.netStateActivityNamesResId = resId;
    }

    public int getNetStateActivityNamesResId() {
        return this.netStateActivityNamesResId;
    }

    public PlatformType getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
    }

    public AliConfig getAliConfig() {
        if (aliConfig == null) {
            aliConfig = new AliConfig();
        }
        return aliConfig;
    }

    public void setAliConfig(AliConfig aliConfig) {
        this.aliConfig = aliConfig;
    }

    public String getRequestUpdateInfoUrl() {
        return requestUpdateInfoUrl;
    }

    public void setRequestUpdateInfoUrl(String requestUpdateInfoUrl) {
        this.requestUpdateInfoUrl = requestUpdateInfoUrl;
    }
}
