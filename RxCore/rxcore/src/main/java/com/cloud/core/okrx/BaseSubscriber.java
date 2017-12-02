package com.cloud.core.okrx;

import android.content.Context;
import android.text.TextUtils;

import com.cloud.core.RxCoreUtils;
import com.cloud.core.beans.BaseBean;
import com.cloud.core.beans.UnLoginCallInfo;
import com.cloud.core.cache.RxCache;
import com.cloud.core.config.RxConfig;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.JsonUtils;
import com.cloud.core.utils.StorageUtils;
import com.cloud.core.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/14
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public abstract class BaseSubscriber<T extends BaseBean, BaseT extends BaseService> {

    private Context context = null;
    private BaseT baseT = null;
    private String reqKey = "";
    private String apiName = "";
    private Object extra = null;
    private List<String> allowRetCodes = new ArrayList<String>();
    private boolean isLoginValid = true;
    private List<String> apiUnloginRetCodes = new ArrayList<String>();
    /**
     * 启动登录页时间戳
     */
    public static long START_LOGIN_TIME_STMPT = 0;
    /**
     * 未登录接口对应的本地文件名
     */
    private static String API_UNLOGIN_FILE_NAME = "6cedf5f448c84b528f7cbbb72ac691d5.txt";
    private static String API_ERROR_FILE_NAME = "680b2ce7ba2e4873b9de9b7ce9d301d5";

    private OnUnLoginCallInfoListener onUnLoginCallInfoListener = null;

    private void onCacheSuccessful(T t) {
        onSuccessful(t);
        onSuccessful(t, reqKey);
        onSuccessful(t, reqKey, extra);
    }

    public void setOnUnLoginCallInfoListener(OnUnLoginCallInfoListener listener) {
        this.onUnLoginCallInfoListener = listener;
    }

    public void setReqKey(String reqKey) {
        this.reqKey = reqKey;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public List<String> getAllowRetCodes() {
        return this.allowRetCodes;
    }

    public void setLoginValid(boolean isLoginValid) {
        this.isLoginValid = isLoginValid;
    }

    public void setApiUnloginRetCodes(List<String> apiUnloginRetCodes) {
        this.apiUnloginRetCodes = apiUnloginRetCodes;
    }

    /**
     * API请求成功
     *
     * @param t
     * @param reqKey 请求唯一标识符
     * @param extra  额外数据
     */
    protected void onSuccessful(T t, String reqKey, Object extra) {

    }

    /**
     * API请求成功
     *
     * @param t
     * @param reqKey 请求唯一标识符
     */
    protected void onSuccessful(T t, String reqKey) {

    }

    /**
     * API请求成功
     *
     * @param t
     */
    protected void onSuccessful(T t) {

    }

    public <ExtraT> BaseSubscriber(Context context, BaseT cls) {
        this.context = context;
        this.baseT = cls;
        if (baseT != null) {
            baseT.setBaseSubscriber(this);
        }
    }

    public void onCompleted() {
        if (baseT != null) {
            baseT.onRequestCompleted();
        }
    }

    public void onNext(T t) {
        try {
            if (t == null) {
                return;
            }
            if (TextUtils.isEmpty(apiName)) {
                apiName = baseT.getApiName();
            }
            RxConfig config = RxCoreUtils.getInstance().getConfig(context);
            if (TextUtils.isEmpty(t.getCode()) ||
                    config.getApiConfig().getApiRet().contains(t.getCode()) ||
                    config.getApiConfig().getApiSpecificNameFilter().contains(apiName) ||
                    (allowRetCodes != null && allowRetCodes.contains(t.getCode()))) {
                onCacheSuccessful(t);
            } else {
                if (config.getApiConfig().getApiUnlogin().contains(t.getCode())) {
                    recordAPIClassInfo(t, API_UNLOGIN_FILE_NAME);
                    long currtime = System.currentTimeMillis();
                    if (START_LOGIN_TIME_STMPT == 0 || ((currtime - START_LOGIN_TIME_STMPT) > 3000)) {
                        if (context != null) {
                            START_LOGIN_TIME_STMPT = currtime;
                            boolean loginFlag = RxCache.getCacheFlag(context, config.getStartLoginFlagKey());
                            if (!loginFlag) {
                                RxCache.setCacheFlag(context, config.getStartLoginFlagKey(), true);
                                if (baseT != null) {
                                    //请求token api请求中的token清空
                                    baseT.setToken("");
                                }
                                if (onUnLoginCallInfoListener != null) {
                                    UnLoginCallInfo callInfo = new UnLoginCallInfo();
                                    callInfo.setApiName(apiName);
                                    callInfo.setResponse(JsonUtils.toStr(t));
                                    onUnLoginCallInfoListener.onCallInfo(callInfo);
                                }
                            }
                        }
                    }
                } else {
                    if (TextUtils.isEmpty(t.getCode()) ||
                            !config.getApiConfig().getApiMessagePromptFilter().contains(t.getCode())) {
                        if (context != null) {
                            recordAPIClassInfo(t, API_ERROR_FILE_NAME);
                            String message = t.getMessage();
                            if (!TextUtils.isEmpty(message)) {
                                message = message.trim().toLowerCase();
                                if (!TextUtils.equals(message, "success")) {
                                    ToastUtils.showLong(context, t.getMessage(), -100);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.warn("interface success ret error:", e);
        }
    }

    private void recordAPIClassInfo(T t, String fileName) {
        try {
            String className = t.getClass().getName();
            File tobeProcessedDir = StorageUtils.getTobeProcessed();
            File tobeFile = new File(tobeProcessedDir, fileName);
            StringBuffer unprocesssb = new StringBuffer();
            unprocesssb.append("\n");
            unprocesssb.append(apiName);
            unprocesssb.append("\n");
            unprocesssb.append(className);
            unprocesssb.append("\n");
            unprocesssb.append(JsonUtils.toStr(t));
            unprocesssb.append("\n");
            unprocesssb.append("--------------------------------------------------------------------------------------------------------------------");
            unprocesssb.append("\n");
            StorageUtils.appendContent(unprocesssb.toString(), tobeFile);
        } catch (Exception e) {
            Logger.L.warn("record api unlogin class info error:", e);
        }
    }
}
