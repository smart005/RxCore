package com.cloud.core.beans;

public class ApkInfo extends BaseBean {
    /**
     * 版本号
     */
    private int versionCode;
    /**
     * 版本名称
     */
    private String versionName;
    /**
     * 最小版本号
     */
    private double minVersionCode = 0;
    /**
     * 是否强制更新 0否 1是
     */
    private int updateType = 0;
    /**
     * 是否启用局部更新
     */
    private boolean enablePart = false;
    /**
     * 更新用户(客户端设备号用,号分隔)
     */
    private String updateUsers = "";
    /**
     * apk名称
     */
    private String apkName = "";
    /**
     * apk下载地址
     */
    private String apkUrl = "";
    /**
     * 更新描述
     */
    private String updateDescription = "";
    /**
     * 下载方式(1:弹窗;2:状态栏下载;)
     */
    private int downloadType = 0;
    /**
     * 应用程序包名
     */
    private String apkPackgeName = "";
    /**
     * 插件参数
     */
    private String plugParams = "";

    /**
     * @return 获取版本号
     */
    public int getVersionCode() {
        return versionCode;
    }

    /**
     * 设置版本号
     *
     * @param versionCode
     */
    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * @return 获取版本名称
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * 设置版本名称
     *
     * @param versionName
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * @return 获取最小版本号
     */
    public double getMinVersionCode() {
        return minVersionCode;
    }

    /**
     * 设置最小版本号
     *
     * @param minVersionCode
     */
    public void setMinVersionCode(double minVersionCode) {
        this.minVersionCode = minVersionCode;
    }

    /**
     * @return 获取是否强制更新0否1是
     */
    public int getUpdateType() {
        return updateType;
    }

    /**
     * 设置是否强制更新0否1是
     *
     * @param updateType
     */
    public void setUpdateType(int updateType) {
        this.updateType = updateType;
    }

    /**
     * @return 获取是否启用局部更新
     */
    public boolean isEnablePart() {
        return enablePart;
    }

    /**
     * 设置是否启用局部更新
     *
     * @param enablePart
     */
    public void setEnablePart(boolean enablePart) {
        this.enablePart = enablePart;
    }

    /**
     * @return 获取更新用户(客户端设备号用号分隔)
     */
    public String getUpdateUsers() {
        return updateUsers;
    }

    /**
     * @param 设置更新用户 (客户端设备号用号分隔)
     */
    public void setUpdateUsers(String updateUsers) {
        this.updateUsers = updateUsers;
    }

    /**
     * @return 获取apk名称
     */
    public String getApkName() {
        return apkName;
    }

    /**
     * @param 设置apk名称
     */
    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    /**
     * @return 获取apk下载地址
     */
    public String getApkUrl() {
        return apkUrl;
    }

    /**
     * @param 设置apk下载地址
     */
    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    /**
     * @return 获取更新描述
     */
    public String getUpdateDescription() {
        return updateDescription;
    }

    /**
     * @param 设置更新描述
     */
    public void setUpdateDescription(String updateDescription) {
        this.updateDescription = updateDescription;
    }

    /**
     * @return 获取下载方式(1:弹窗;2:状态栏下载;)
     */
    public int getDownloadType() {
        return downloadType;
    }

    /**
     * @param 设置下载方式 (1:弹窗;2:状态栏下载;)
     */
    public void setDownloadType(int downloadType) {
        this.downloadType = downloadType;
    }

    /**
     * @return 获取应用程序包名
     */
    public String getApkPackgeName() {
        return apkPackgeName;
    }

    /**
     * @param 设置应用程序包名
     */
    public void setApkPackgeName(String apkPackgeName) {
        this.apkPackgeName = apkPackgeName;
    }

    /**
     * 获取插件参数
     */
    public String getPlugParams() {
        if (plugParams == null) {
            plugParams = "";
        }
        return plugParams;
    }

    /**
     * 设置插件参数
     *
     * @param plugParams
     */
    public void setPlugParams(String plugParams) {
        this.plugParams = plugParams;
    }
}