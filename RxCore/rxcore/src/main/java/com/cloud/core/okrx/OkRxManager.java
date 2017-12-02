package com.cloud.core.okrx;

import android.content.Context;

import com.cloud.core.Action;
import com.cloud.core.Action0;
import com.cloud.core.Action2;
import com.cloud.core.okrx.requests.OkRxDeleteRequest;
import com.cloud.core.okrx.requests.OkRxDownloadFileRequest;
import com.cloud.core.okrx.requests.OkRxGetRequest;
import com.cloud.core.okrx.requests.OkRxHeadRequest;
import com.cloud.core.okrx.requests.OkRxOptionsRequest;
import com.cloud.core.okrx.requests.OkRxPatchRequest;
import com.cloud.core.okrx.requests.OkRxPostRequest;
import com.cloud.core.okrx.requests.OkRxPutRequest;
import com.cloud.core.okrx.requests.OkRxTraceRequest;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.io.File;
import java.util.HashMap;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/15
 * @Description:网络请求类
 * @Modifier:
 * @ModifyContent:
 */
public class OkRxManager {

    private static OkRxManager okRxManager = null;

    public static OkRxManager getInstance() {
        return okRxManager == null ? okRxManager = new OkRxManager() : okRxManager;
    }

    public void get(Context context,
                    String url,
                    HttpHeaders httpHeaders,
                    HttpParams httpParams,
                    boolean isCache,
                    String cacheKey,
                    long cacheTime,
                    Action<String> successAction,
                    Action<RequestState> completeAction,
                    Action2<String, String> printLogAction,
                    String apiRequestKey) {
        OkRxGetRequest request = new OkRxGetRequest();
        request.call(context, url, httpHeaders, httpParams, isCache, cacheKey, cacheTime, successAction, completeAction, printLogAction, apiRequestKey);
    }

    public void post(Context context,
                     String url,
                     HttpHeaders httpHeaders,
                     HttpParams httpParams,
                     boolean isCache,
                     String cacheKey,
                     long cacheTime,
                     RequestContentType requestContentType,
                     HashMap<String, Object> params,
                     Action<String> successAction,
                     Action<RequestState> completeAction,
                     Action2<String, String> printLogAction,
                     String apiRequestKey) {
        OkRxPostRequest request = new OkRxPostRequest(requestContentType, params);
        request.call(context, url, httpHeaders, httpParams, isCache, cacheKey, cacheTime, successAction, completeAction, printLogAction, apiRequestKey);
    }

    public void delete(Context context,
                       String url,
                       HttpHeaders httpHeaders,
                       HttpParams httpParams,
                       boolean isCache,
                       String cacheKey,
                       long cacheTime,
                       RequestContentType requestContentType,
                       HashMap<String, Object> params,
                       Action<String> successAction,
                       Action<RequestState> completeAction,
                       Action2<String, String> printLogAction,
                       String apiRequestKey) {
        OkRxDeleteRequest request = new OkRxDeleteRequest(requestContentType, params);
        request.call(context, url, httpHeaders, httpParams, isCache, cacheKey, cacheTime, successAction, completeAction, printLogAction, apiRequestKey);
    }

    public void put(Context context,
                    String url,
                    HttpHeaders httpHeaders,
                    HttpParams httpParams,
                    boolean isCache,
                    String cacheKey,
                    long cacheTime,
                    RequestContentType requestContentType,
                    HashMap<String, Object> params,
                    Action<String> successAction,
                    Action<RequestState> completeAction,
                    Action2<String, String> printLogAction,
                    String apiRequestKey) {
        OkRxPutRequest request = new OkRxPutRequest(requestContentType, params);
        request.call(context, url, httpHeaders, httpParams, isCache, cacheKey, cacheTime, successAction, completeAction, printLogAction, apiRequestKey);
    }

    public void patch(Context context,
                      String url,
                      HttpHeaders httpHeaders,
                      HttpParams httpParams,
                      boolean isCache,
                      String cacheKey,
                      long cacheTime,
                      RequestContentType requestContentType,
                      HashMap<String, Object> params,
                      Action<String> successAction,
                      Action<RequestState> completeAction,
                      Action2<String, String> printLogAction,
                      String apiRequestKey) {
        OkRxPatchRequest request = new OkRxPatchRequest(requestContentType, params);
        request.call(context, url, httpHeaders, httpParams, isCache, cacheKey, cacheTime, successAction, completeAction, printLogAction, apiRequestKey);
    }

    public void head(Context context,
                     String url,
                     HttpHeaders httpHeaders,
                     HttpParams httpParams,
                     boolean isCache,
                     String cacheKey,
                     long cacheTime,
                     Action<String> successAction,
                     Action<RequestState> completeAction,
                     Action2<String, String> printLogAction,
                     String apiRequestKey) {
        OkRxHeadRequest request = new OkRxHeadRequest();
        request.call(context, url, httpHeaders, httpParams, isCache, cacheKey, cacheTime, successAction, completeAction, printLogAction, apiRequestKey);
    }

    public void options(Context context,
                        String url,
                        HttpHeaders httpHeaders,
                        HttpParams httpParams,
                        boolean isCache,
                        String cacheKey,
                        long cacheTime,
                        RequestContentType requestContentType,
                        HashMap<String, Object> params,
                        Action<String> successAction,
                        Action<RequestState> completeAction,
                        Action2<String, String> printLogAction,
                        String apiRequestKey) {
        OkRxOptionsRequest request = new OkRxOptionsRequest(requestContentType, params);
        request.call(context, url, httpHeaders, httpParams, isCache, cacheKey, cacheTime, successAction, completeAction, printLogAction, apiRequestKey);
    }

    public void trace(Context context,
                      String url,
                      HttpHeaders httpHeaders,
                      HttpParams httpParams,
                      boolean isCache,
                      String cacheKey,
                      long cacheTime,
                      Action<String> successAction,
                      Action<RequestState> completeAction,
                      Action2<String, String> printLogAction,
                      String apiRequestKey) {
        OkRxTraceRequest request = new OkRxTraceRequest();
        request.call(context, url, httpHeaders, httpParams, isCache, cacheKey, cacheTime, successAction, completeAction, printLogAction, apiRequestKey);
    }

    public void download(Context context,
                         String url,
                         HttpHeaders httpHeaders,
                         HttpParams httpParams,
                         String destFileDir,
                         String destFileName,
                         Action<Float> progressAction,
                         Action<File> successAction,
                         Action0 completeAction) {
        OkRxDownloadFileRequest request = new OkRxDownloadFileRequest();
        request.call(context, url, httpHeaders, httpParams, destFileDir, destFileName, progressAction, successAction, completeAction);
    }
}
