package com.cloud.core.okrx.requests;

import android.content.Context;

import com.cloud.core.Action;
import com.cloud.core.Action2;
import com.cloud.core.okrx.RequestState;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/15
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public interface BaseRequest {
    public void call(Context context,
                     String url,
                     HttpHeaders httpHeaders,
                     HttpParams httpParams,
                     boolean isCache,
                     String cacheKey,
                     long cacheTime,
                     Action<String> successAction,
                     Action<RequestState> completeAction,
                     Action2<String, String> printLogAction,
                     String apiRequestKey);
}
