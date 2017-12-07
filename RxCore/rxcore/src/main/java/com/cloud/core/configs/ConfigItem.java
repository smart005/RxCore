package com.cloud.core.configs;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/12/5
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ConfigItem {

    /**
     * 配置key
     */
    private String key = "";
    /**
     * 配置value
     */
    private Object value = null;
    /**
     * 名称
     */
    private String name = "";
    /**
     * 类型
     */
    private String type = "";
    /**
     * 版本检测
     */
    private boolean state = true;
    /**
     * url类型
     */
    private String urlType = "";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}
