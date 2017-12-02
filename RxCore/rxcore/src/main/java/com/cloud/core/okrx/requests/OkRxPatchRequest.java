package com.cloud.core.okrx.requests;

import android.content.Context;
import android.text.TextUtils;

import com.cloud.core.Action;
import com.cloud.core.Action2;
import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.core.okrx.RequestContentType;
import com.cloud.core.okrx.RequestState;
import com.cloud.core.utils.JsonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PatchRequest;

import java.util.HashMap;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/15
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class OkRxPatchRequest implements BaseRequest {

    private RequestContentType requestContentType = null;
    private HashMap<String, Object> params = null;
    private String responseString = "";

    public OkRxPatchRequest(RequestContentType requestContentType,
                            HashMap<String, Object> params) {
        this.requestContentType = requestContentType;
        this.params = params;
    }

    @Override
    public void call(Context context,
                     String url,
                     HttpHeaders httpHeaders,
                     HttpParams httpParams,
                     boolean isCache,
                     String cacheKey,
                     long cacheTime,
                     final Action<String> successAction,
                     final Action<RequestState> completeAction,
                     final Action2<String, String> printLogAction,
                     final String apiRequestKey) {
        try {
            if (completeAction == null) {
                return;
            }
            if (context == null || TextUtils.isEmpty(url) || requestContentType == null) {
                completeAction.call(RequestState.Completed);
                return;
            }
            PatchRequest<String> request = OkGo.<String>patch(url).tag(context);
            if (httpHeaders != null) {
                request.headers(httpHeaders);
            }
            if (requestContentType == RequestContentType.Json) {
                if (!ObjectJudge.isNullOrEmpty(params)) {
                    String postJson = JsonUtils.toStr(params);
                    request.upJson(postJson);
                }
            } else {
                if (httpParams != null) {
                    request.params(httpParams);
                }
            }
            if (isCache) {
                if (!TextUtils.isEmpty(cacheKey)) {
                    request.cacheKey(cacheKey);
                }
                request.cacheTime(cacheTime);
                request.cacheMode(CacheMode.IF_NONE_CACHE_REQUEST);
            }
            request.execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        if (successAction != null && response != null) {
                            responseString = response.body();
                            successAction.call(responseString);
                        }
                    } catch (Exception e) {
                        completeAction.call(RequestState.Completed);
                        Logger.L.error(e);
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    completeAction.call(RequestState.Error);
                }

                @Override
                public void onFinish() {
                    completeAction.call(RequestState.Completed);
                    if (printLogAction != null) {
                        printLogAction.call(apiRequestKey, responseString);
                    }
                }
            });
        } catch (Exception e) {
            completeAction.call(RequestState.Completed);
            if (printLogAction != null) {
                printLogAction.call(apiRequestKey, "");
            }
            Logger.L.error(e);
        }
    }
}
