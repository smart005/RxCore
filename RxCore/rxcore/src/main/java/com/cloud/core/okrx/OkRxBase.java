package com.cloud.core.okrx;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Author Gs
 * Email:gs_12@foxmail.com
 * CreateTime:2017/6/1
 * Description: OkGo基础
 * Modifier:
 * ModifyContent:
 */

public class OkRxBase {

    private static OkRxBase okRxBase = null;

    public static OkRxBase getInstance() {
        if (okRxBase == null) {
            okRxBase = new OkRxBase();
        }
        return okRxBase;
    }

    public <AT extends Application> void init(AT at) {
        init(at, new OkgoConfigBean());
    }

    public <AT extends Application> void init(AT at, OkgoConfigBean okgoConfigBean) {
        OkGo okgo = OkGo.getInstance();
        if (okgo != null) {
            okgo.init(at);
            //全局缓存模式,默认不缓存
            okgo.setCacheMode(okgoConfigBean.getCacheMode());
            //可以全局统一设置缓存时间,默认永久
            okgo.setCacheTime(okgoConfigBean.getCacheTime());
            //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
            okgo.setRetryCount(okgoConfigBean.getRetryCount());
            //添加公共头信息
            if (okgoConfigBean.getCommonHeaders() != null) {
                okgo.addCommonHeaders(okgoConfigBean.getCommonHeaders());
            }
            //添加公共参数信息
            if (okgoConfigBean.getCommonParams() != null) {
                okgo.addCommonParams(okgoConfigBean.getCommonParams());
            }
        }
        if (okgoConfigBean != null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //全局的连接超时时间
            if (okgoConfigBean.getConnectTimeout() > 0) {
                builder.connectTimeout(okgoConfigBean.getConnectTimeout(), TimeUnit.MILLISECONDS);
            }
            //全局的读取超时时间
            if (okgoConfigBean.getReadTimeOut() > 0) {
                builder.readTimeout(okgoConfigBean.getReadTimeOut(), TimeUnit.MILLISECONDS);
            }
            //全局的写入超时时间
            if (okgoConfigBean.getWriteTimeOut() > 0) {
                builder.writeTimeout(okgoConfigBean.getWriteTimeOut(), TimeUnit.MILLISECONDS);
            }
            //框架管理cookie 0:内存缓存（app退出后，cookie消失）1:持久化存储，如果cookie不过期，则一直有效
            if (okgoConfigBean.isEnableCookieStore()) {
                if (okgoConfigBean.getManageCookieType() == 1) {
                    builder.cookieJar(new CookieJarImpl(new SPCookieStore(at)));
                } else {
                    builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));
                }
            }
            //信任所有证书,不安全有风险  如果有证书就写入证书，具体介绍看github
            HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
            if (sslParams1 != null) {
                builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
            }
            okgo.setOkHttpClient(builder.build());
        }
    }
}
