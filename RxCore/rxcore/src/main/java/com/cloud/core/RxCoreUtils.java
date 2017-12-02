package com.cloud.core;

import android.content.Context;
import android.text.TextUtils;

import com.cloud.core.cache.RxCache;
import com.cloud.core.config.RxConfig;
import com.cloud.core.utils.JsonUtils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/17
 * @Description:rx框架初始化类
 * @Modifier:
 * @ModifyContent:
 */
public class RxCoreUtils {

    private static RxCoreUtils rxCoreUtils = null;
    private RxConfig config = null;
    private String key = "f23cff1293a54e6ba94d93d64cdb7916";

    public static RxCoreUtils getInstance() {
        return rxCoreUtils == null ? rxCoreUtils = new RxCoreUtils() : rxCoreUtils;
    }

    public RxConfig getConfig(Context context) {
        if (config == null) {
            String data = RxCache.getCacheData(context, key);
            if (TextUtils.isEmpty(data)) {
                return config = new RxConfig();
            } else {
                RxConfig rxConfig = JsonUtils.parseT(data, RxConfig.class);
                return rxConfig == null ? (config = new RxConfig()) : (config = rxConfig);
            }
        } else {
            return config;
        }
    }

    public void init(Context context, RxConfig config) {
        RxCache.setCacheData(context, key, JsonUtils.toStr(config));
    }

    public void init(Context context) {
        this.init(context, getConfig(context));
    }
}
