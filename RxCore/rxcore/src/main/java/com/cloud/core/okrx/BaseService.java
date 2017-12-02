package com.cloud.core.okrx;

import android.content.Context;
import android.database.Observable;
import android.text.TextUtils;

import com.cloud.core.Action;
import com.cloud.core.Action2;
import com.cloud.core.Func1;
import com.cloud.core.ObjectJudge;
import com.cloud.core.beans.BaseBean;
import com.cloud.core.beans.RetrofitParams;
import com.cloud.core.enums.RequestType;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.JsonUtils;
import com.cloud.core.utils.PathsUtils;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/14
 * @Description:
 */
public class BaseService {

    /**
     * token值
     */
    private String token = "";
    /**
     * 接口名
     */
    private String apiName = "";

    private BaseSubscriber baseSubscriber = null;
    private HashMap<String, StringBuffer> logmaps = new HashMap<String, StringBuffer>();

    /**
     * 获取token值
     */

    public String getToken() {
        if (token == null) {
            token = "";
        }
        return token;
    }

    /**
     * 设置token值
     *
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取接口名
     */
    public String getApiName() {
        if (apiName == null) {
            apiName = "";
        }
        return apiName;
    }

    /**
     * 设置接口名
     *
     * @param apiName
     */
    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public BaseSubscriber getBaseSubscriber() {
        return this.baseSubscriber;
    }

    public void setBaseSubscriber(BaseSubscriber baseSubscriber) {
        this.baseSubscriber = baseSubscriber;
    }

    /**
     * API请求完成(结束)
     */
    protected void onRequestCompleted() {

    }

    protected void onRequestError() {

    }

    protected <T extends BaseBean> void baseConfig(Context context,
                                                   final BaseService baseService,
                                                   final RetrofitParams retrofitParams,
                                                   OkRxValidParam validParam,
                                                   final Action<T> successAction,
                                                   String apiRequestKey) {
        Observable<T> observable = null;
        final Class<T> dataClass = retrofitParams.getDataClass();
        try {
            if (retrofitParams != null && !TextUtils.isEmpty(retrofitParams.getRequestUrl())) {
                String requestUrl = retrofitParams.getRequestUrl();
                //请求头
                HttpHeaders httpHeaders = new HttpHeaders();
                if (retrofitParams.getHeadParams() != null && retrofitParams.getHeadParams().size() > 0) {
                    HashMap<String, String> headParams = retrofitParams.getHeadParams();
                    for (HashMap.Entry<String, String> entry : headParams.entrySet()) {
                        //参数名
                        String key = entry.getKey();
                        if (TextUtils.isEmpty(key)) {
                            continue;
                        }
                        //参数值
                        String value = entry.getValue();
                        if (TextUtils.isEmpty(value)) {
                            continue;
                        }
                        httpHeaders.put(key, value);
                    }
                }
                //检查头部是否已添加token，没有则添加
                if (!TextUtils.isEmpty(token)) {
                    if (validParam.getApiCheckAnnotation().IsTokenValid()) {
                        String tokenName = retrofitParams.getTokenName();
                        if (!TextUtils.isEmpty(tokenName)) {
                            httpHeaders.put(tokenName, token);
                        }
                    }
                }
                //请求参数
                HttpParams httpParams = getHttpParams(retrofitParams);
                if (retrofitParams.getRequestType() == RequestType.POST) {
                    post(context, requestUrl, httpHeaders, httpParams, retrofitParams, baseService, dataClass, successAction, apiRequestKey);
                } else if (retrofitParams.getRequestType() == RequestType.DELETE) {
                    delete(context, requestUrl, httpHeaders, httpParams, retrofitParams, baseService, dataClass, successAction, apiRequestKey);
                } else if (retrofitParams.getRequestType() == RequestType.PUT) {
                    put(context, requestUrl, httpHeaders, httpParams, retrofitParams, baseService, dataClass, successAction, apiRequestKey);
                } else if (retrofitParams.getRequestType() == RequestType.PATCH) {
                    patch(context, requestUrl, httpHeaders, httpParams, retrofitParams, baseService, dataClass, successAction, apiRequestKey);
                } else if (retrofitParams.getRequestType() == RequestType.HEAD) {
                    head(context, requestUrl, httpHeaders, httpParams, retrofitParams, baseService, dataClass, successAction, apiRequestKey);
                } else if (retrofitParams.getRequestType() == RequestType.POST) {
                    options(context, requestUrl, httpHeaders, httpParams, retrofitParams, baseService, dataClass, successAction, apiRequestKey);
                } else if (retrofitParams.getRequestType() == RequestType.TRACE) {
                    trace(context, requestUrl, httpHeaders, httpParams, retrofitParams, baseService, dataClass, successAction, apiRequestKey);
                } else {
                    get(context, requestUrl, httpHeaders, httpParams, retrofitParams, baseService, dataClass, successAction, apiRequestKey);
                }
            } else {
                baseService.onRequestCompleted();
            }
        } catch (Exception e) {
            baseService.onRequestCompleted();
            Logger.L.error(e);
        }
    }

    private <T extends BaseBean> void successDealWith(Action<T> successAction,
                                                      Class<T> dataClass,
                                                      BaseService baseService,
                                                      String response) {
        if (successAction == null) {
            baseService.onRequestCompleted();
        } else {
            T data = JsonUtils.parseT(response, dataClass);
            if (data != null && TextUtils.isEmpty(data.getCode())) {
                BaseBean baseBean = (BaseBean) JsonUtils.parseT(response, dataClass.getSuperclass());
                data.setCode(baseBean.getCode());
                data.setMessage(baseBean.getMessage());
                data.setHasNextPage(baseBean.isHasNextPage());
                data.setHasPreviousPage(baseBean.isHasPreviousPage());
                data.setFirstPage(baseBean.isFirstPage());
                data.setLastPage(baseBean.isLastPage());
                data.setFirstPage(baseBean.getFirstPage());
                data.setLastPage(baseBean.getLastPage());
                data.setPageNum(baseBean.getPageNum());
                data.setPageSize(baseBean.getPageSize());
                data.setPages(baseBean.getPages());
                data.setTotal(baseBean.getTotal());
            }
            successAction.call(data);
        }
    }

    private <T extends BaseBean> void trace(Context context,
                                            String requestUrl,
                                            HttpHeaders httpHeaders,
                                            HttpParams httpParams,
                                            RetrofitParams retrofitParams,
                                            final BaseService baseService,
                                            final Class<T> dataClass,
                                            final Action<T> successAction,
                                            String apiRequestKey) {
        OkRxManager.getInstance().trace(
                context,
                requestUrl,
                httpHeaders,
                httpParams,
                retrofitParams.isCache(),
                retrofitParams.getCacheKey(),
                retrofitParams.getCacheTime(),
                new Action<String>() {
                    @Override
                    public void call(String response) {
                        successDealWith(successAction, dataClass, baseService, response);
                    }
                },
                new Action<RequestState>() {
                    @Override
                    public void call(RequestState requestState) {
                        if (requestState == RequestState.Error) {
                            baseService.onRequestError();
                        } else {
                            baseService.onRequestCompleted();
                        }
                    }
                },
                new Action2<String, String>() {
                    @Override
                    public void call(String apiRequestKey, String responseString) {
                        finallPrintLog(apiRequestKey, responseString);
                    }
                }, apiRequestKey);
    }

    private <T extends BaseBean> void options(Context context,
                                              String requestUrl,
                                              HttpHeaders httpHeaders,
                                              HttpParams httpParams,
                                              RetrofitParams retrofitParams,
                                              final BaseService baseService,
                                              final Class<T> dataClass,
                                              final Action<T> successAction,
                                              String apiRequestKey) {
        OkRxManager.getInstance().options(
                context,
                requestUrl,
                httpHeaders,
                httpParams,
                retrofitParams.isCache(),
                retrofitParams.getCacheKey(),
                retrofitParams.getCacheTime(),
                retrofitParams.getRequestContentType(),
                retrofitParams.getParams(),
                new Action<String>() {
                    @Override
                    public void call(String response) {
                        successDealWith(successAction, dataClass, baseService, response);
                    }
                },
                new Action<RequestState>() {
                    @Override
                    public void call(RequestState requestState) {
                        baseService.onRequestCompleted();
                    }
                },
                new Action2<String, String>() {
                    @Override
                    public void call(String apiRequestKey, String responseString) {
                        finallPrintLog(apiRequestKey, responseString);
                    }
                }, apiRequestKey);
    }

    private <T extends BaseBean> void head(Context context,
                                           String requestUrl,
                                           HttpHeaders httpHeaders,
                                           HttpParams httpParams,
                                           RetrofitParams retrofitParams,
                                           final BaseService baseService,
                                           final Class<T> dataClass,
                                           final Action<T> successAction,
                                           String apiRequestKey) {
        OkRxManager.getInstance().head(
                context,
                requestUrl,
                httpHeaders,
                httpParams,
                retrofitParams.isCache(),
                retrofitParams.getCacheKey(),
                retrofitParams.getCacheTime(),
                new Action<String>() {
                    @Override
                    public void call(String response) {
                        successDealWith(successAction, dataClass, baseService, response);
                    }
                },
                new Action<RequestState>() {
                    @Override
                    public void call(RequestState requestState) {
                        if (requestState == RequestState.Error) {
                            baseService.onRequestError();
                        } else {
                            baseService.onRequestCompleted();
                        }
                    }
                },
                new Action2<String, String>() {
                    @Override
                    public void call(String apiRequestKey, String responseString) {
                        finallPrintLog(apiRequestKey, responseString);
                    }
                }, apiRequestKey);
    }

    private <T extends BaseBean> void patch(Context context,
                                            String requestUrl,
                                            HttpHeaders httpHeaders,
                                            HttpParams httpParams,
                                            RetrofitParams retrofitParams,
                                            final BaseService baseService,
                                            final Class<T> dataClass,
                                            final Action<T> successAction,
                                            String apiRequestKey) {
        OkRxManager.getInstance().patch(
                context,
                requestUrl,
                httpHeaders,
                httpParams,
                retrofitParams.isCache(),
                retrofitParams.getCacheKey(),
                retrofitParams.getCacheTime(),
                retrofitParams.getRequestContentType(),
                retrofitParams.getParams(),
                new Action<String>() {
                    @Override
                    public void call(String response) {
                        successDealWith(successAction, dataClass, baseService, response);
                    }
                },
                new Action<RequestState>() {
                    @Override
                    public void call(RequestState requestState) {
                        if (requestState == RequestState.Error) {
                            baseService.onRequestError();
                        } else {
                            baseService.onRequestCompleted();
                        }
                    }
                },
                new Action2<String, String>() {
                    @Override
                    public void call(String apiRequestKey, String responseString) {
                        finallPrintLog(apiRequestKey, responseString);
                    }
                }, apiRequestKey);
    }

    private <T extends BaseBean> void put(Context context,
                                          String requestUrl,
                                          HttpHeaders httpHeaders,
                                          HttpParams httpParams,
                                          RetrofitParams retrofitParams,
                                          final BaseService baseService,
                                          final Class<T> dataClass,
                                          final Action<T> successAction,
                                          String apiRequestKey) {
        OkRxManager.getInstance().put(
                context,
                requestUrl,
                httpHeaders,
                httpParams,
                retrofitParams.isCache(),
                retrofitParams.getCacheKey(),
                retrofitParams.getCacheTime(),
                retrofitParams.getRequestContentType(),
                retrofitParams.getParams(),
                new Action<String>() {
                    @Override
                    public void call(String response) {
                        successDealWith(successAction, dataClass, baseService, response);
                    }
                },
                new Action<RequestState>() {
                    @Override
                    public void call(RequestState requestState) {
                        if (requestState == RequestState.Error) {
                            baseService.onRequestError();
                        } else {
                            baseService.onRequestCompleted();
                        }
                    }
                },
                new Action2<String, String>() {
                    @Override
                    public void call(String apiRequestKey, String responseString) {
                        finallPrintLog(apiRequestKey, responseString);
                    }
                }, apiRequestKey);
    }

    private <T extends BaseBean> void delete(Context context,
                                             String requestUrl,
                                             HttpHeaders httpHeaders,
                                             HttpParams httpParams,
                                             RetrofitParams retrofitParams,
                                             final BaseService baseService,
                                             final Class<T> dataClass,
                                             final Action<T> successAction,
                                             String apiRequestKey) {
        OkRxManager.getInstance().delete(
                context,
                requestUrl,
                httpHeaders,
                httpParams,
                retrofitParams.isCache(),
                retrofitParams.getCacheKey(),
                retrofitParams.getCacheTime(),
                retrofitParams.getRequestContentType(),
                retrofitParams.getParams(),
                new Action<String>() {
                    @Override
                    public void call(String response) {
                        successDealWith(successAction, dataClass, baseService, response);
                    }
                },
                new Action<RequestState>() {
                    @Override
                    public void call(RequestState requestState) {
                        if (requestState == RequestState.Error) {
                            baseService.onRequestError();
                        } else {
                            baseService.onRequestCompleted();
                        }
                    }
                },
                new Action2<String, String>() {
                    @Override
                    public void call(String apiRequestKey, String responseString) {
                        finallPrintLog(apiRequestKey, responseString);
                    }
                }, apiRequestKey);
    }

    private <T extends BaseBean> void post(Context context,
                                           String requestUrl,
                                           HttpHeaders httpHeaders,
                                           HttpParams httpParams,
                                           RetrofitParams retrofitParams,
                                           final BaseService baseService,
                                           final Class<T> dataClass,
                                           final Action<T> successAction,
                                           String apiRequestKey) {
        OkRxManager.getInstance().post(
                context,
                requestUrl,
                httpHeaders,
                httpParams,
                retrofitParams.isCache(),
                retrofitParams.getCacheKey(),
                retrofitParams.getCacheTime(),
                retrofitParams.getRequestContentType(),
                retrofitParams.getParams(),
                new Action<String>() {
                    @Override
                    public void call(String response) {
                        successDealWith(successAction, dataClass, baseService, response);
                    }
                },
                new Action<RequestState>() {
                    @Override
                    public void call(RequestState requestState) {
                        if (requestState == RequestState.Error) {
                            baseService.onRequestError();
                        } else {
                            baseService.onRequestCompleted();
                        }
                    }
                },
                new Action2<String, String>() {
                    @Override
                    public void call(String apiRequestKey, String responseString) {
                        finallPrintLog(apiRequestKey, responseString);
                    }
                }, apiRequestKey);
    }

    private <T extends BaseBean> void get(Context context,
                                          String requestUrl,
                                          HttpHeaders httpHeaders,
                                          HttpParams httpParams,
                                          RetrofitParams retrofitParams,
                                          final BaseService baseService,
                                          final Class<T> dataClass,
                                          final Action<T> successAction,
                                          String apiRequestKey) {
        OkRxManager.getInstance().get(
                context,
                requestUrl,
                httpHeaders,
                httpParams,
                retrofitParams.isCache(),
                retrofitParams.getCacheKey(),
                retrofitParams.getCacheTime(),
                new Action<String>() {
                    @Override
                    public void call(String response) {
                        successDealWith(successAction, dataClass, baseService, response);
                    }
                },
                new Action<RequestState>() {
                    @Override
                    public void call(RequestState requestState) {
                        if (requestState == RequestState.Error) {
                            baseService.onRequestError();
                        } else {
                            baseService.onRequestCompleted();
                        }
                    }
                },
                new Action2<String, String>() {
                    @Override
                    public void call(String apiRequestKey, String responseString) {
                        finallPrintLog(apiRequestKey, responseString);
                    }
                }, apiRequestKey);
    }

    private void finallPrintLog(String apiRequestKey, String responseString) {
        try {
            if (!logmaps.containsKey(apiRequestKey)) {
                return;
            }
            StringBuffer buffer = logmaps.get(apiRequestKey);
            if (buffer == null) {
                return;
            }
            if (!TextUtils.isEmpty(responseString)) {
                int length = responseString.length();
                for (int i = 0; i < length; i += 30) {
                    int endIndex = i + 30;
                    if (endIndex >= length) {
                        buffer.append(String.format("%s\n", responseString.substring(i)));
                    } else {
                        buffer.append(String.format("%s\n", responseString.substring(i, endIndex)));
                    }
                }
            }
            buffer.append(String.format("%s\n", responseString));
            buffer.append("===============================================================\n");
            Logger.L.info(buffer.toString());
        } catch (Exception e) {
            Logger.L.error(e);
        } finally {
            if (logmaps.containsKey(apiRequestKey)) {
                logmaps.remove(apiRequestKey);
            }
        }
    }

    private HttpParams getHttpParams(RetrofitParams retrofitParams) {
        HttpParams httpParams = new HttpParams();
        if (retrofitParams.getParams() != null && retrofitParams.getParams().size() > 0) {
            HashMap<String, Object> params = retrofitParams.getParams();
            for (HashMap.Entry<String, Object> entry : params.entrySet()) {
                //参数名
                String key = entry.getKey();
                if (TextUtils.isEmpty(key)) {
                    continue;
                }
                //参数值
                Object value = entry.getValue();
                if (value == null) {
                    continue;
                }
                if (value instanceof Integer) {
                    httpParams.put(key, (Integer) value);
                } else if (value instanceof Long) {
                    httpParams.put(key, (Long) value);
                } else if (value instanceof String) {
                    httpParams.put(key, (String) value);
                } else if (value instanceof Double) {
                    httpParams.put(key, (Double) value);
                } else if (value instanceof Float) {
                    httpParams.put(key, (Float) value);
                } else if (value instanceof Boolean) {
                    httpParams.put(key, (Boolean) value);
                }
            }
        }
        return httpParams;
    }

    /**
     * @param context
     * @param apiClass
     * @param server
     * @param baseSubscriber
     * @param validParam
     * @param urlAction
     * @param decApiAction
     * @param <I>
     * @param <S>
     */
    protected <T extends BaseBean, I, S extends BaseService> void requestObject(Context context,
                                                                                Class<I> apiClass,
                                                                                S server,
                                                                                final BaseSubscriber<T, S> baseSubscriber,
                                                                                OkRxValidParam validParam,
                                                                                Func1<String, String> urlAction,
                                                                                Func1<I, RetrofitParams> decApiAction,
                                                                                OnUnLoginCallInfoListener listener) {
        try {
            if (validParam.isFlag()) {
                if (urlAction == null || server == null) {
                    baseSubscriber.onCompleted();
                } else {
                    I decApi = BaseOkgoValidParsing.getInstance().createAPI(apiClass);
                    if (decApiAction == null || decApi == null || validParam.getApiCheckAnnotation() == null) {
                        baseSubscriber.onCompleted();
                    } else {
                        RetrofitParams retrofitParams = decApiAction.call(decApi);
                        if (retrofitParams == null || !retrofitParams.getFlag()) {
                            baseSubscriber.onCompleted();
                        } else {
                            //若api类未指定base url类型名称则不作请求处理
                            if (retrofitParams.getUrlTypeName() == null) {
                                baseSubscriber.onCompleted();
                            } else {
                                if (TextUtils.isEmpty(retrofitParams.getUrlTypeName().value())) {
                                    baseSubscriber.onCompleted();
                                } else {
                                    //设置此接口允许返回码
                                    if (!ObjectJudge.isNullOrEmpty(retrofitParams.getAllowRetCodes())) {
                                        List<String> allowRetCodes = baseSubscriber.getAllowRetCodes();
                                        allowRetCodes.addAll(retrofitParams.getAllowRetCodes());
                                    }
                                    //设置请求地址
                                    String baseUrl = urlAction.call(retrofitParams.getUrlTypeName().value());
                                    if (retrofitParams.getIsJoinUrl()) {
                                        retrofitParams.setRequestUrl(PathsUtils.combine(baseUrl, retrofitParams.getRequestUrl()));
                                    }
                                    //设置token名字
                                    retrofitParams.setTokenName(retrofitParams.getUrlTypeName().tokenName());
                                    //NO_CACHE: 不使用缓存,该模式下,cacheKey,cacheTime 参数均无效
                                    //DEFAULT: 按照HTTP协议的默认缓存规则，例如有304响应头时缓存。
                                    //REQUEST_FAILED_READ_CACHE：先请求网络，如果请求网络失败，则读取缓存，如果读取缓存失败，本次请求失败。
                                    //IF_NONE_CACHE_REQUEST：如果缓存不存在才请求网络，否则使用缓存。
                                    //FIRST_CACHE_THEN_REQUEST：先使用缓存，不管是否存在，仍然请求网络。
                                    //缓存的过期时间,单位毫秒
                                    //为确保未设置缓存请求几乎不做缓存，此处默认缓存时间暂设为5秒
                                    retrofitParams.setCache(validParam.getApiCheckAnnotation().IsCache());
                                    String cacheKey = MessageFormat.format(validParam.getCacheKey(), apiClass.getSimpleName());
                                    retrofitParams.setCacheKey(cacheKey);
                                    if (retrofitParams.isCache()) {
                                        long milliseconds = ConvertUtils.toMilliseconds(validParam.getApiCheckAnnotation().CacheTime(),
                                                validParam.getApiCheckAnnotation().CacheTimeUnit());
                                        retrofitParams.setCacheTime(milliseconds);
                                    } else {
                                        retrofitParams.setCacheTime(5000);
                                    }
                                    //拼接完整的url
                                    //del请求看delQuery参数是不是为空
                                    if (!ObjectJudge.isNullOrEmpty(retrofitParams.getDelQueryParams())) {
                                        StringBuffer querysb = new StringBuffer();
                                        for (Map.Entry<String, String> entry : retrofitParams.getDelQueryParams().entrySet()) {
                                            querysb.append(MessageFormat.format("{0}={1},", entry.getKey(), entry.getValue()));
                                        }
                                        if (querysb.length() > 0) {
                                            if (retrofitParams.getRequestUrl().indexOf("?") < 0) {
                                                retrofitParams.setRequestUrl(String.format("%s?%s",
                                                        retrofitParams.getRequestUrl(),
                                                        querysb.substring(0, querysb.length() - 1)));
                                            } else {
                                                retrofitParams.setRequestUrl(String.format("%s&%s",
                                                        retrofitParams.getRequestUrl(),
                                                        querysb.substring(0, querysb.length() - 1)));
                                            }
                                        }
                                    }
                                    baseSubscriber.setOnUnLoginCallInfoListener(listener);
                                    String apiRequestKey = GlobalUtils.getNewGuid();
                                    server.<T>baseConfig(context,
                                            server,
                                            retrofitParams,
                                            validParam,
                                            new Action<T>() {
                                                @Override
                                                public void call(T t) {
                                                    baseSubscriber.onNext(t);
                                                }
                                            },
                                            apiRequestKey);
                                    printLog(apiRequestKey, retrofitParams);
                                }
                            }
                        }
                    }
                }
            } else {
                baseSubscriber.onCompleted();
            }
        } catch (Exception e) {
            baseSubscriber.onCompleted();
        }
    }

    private void printLog(String apiRequestKey, RetrofitParams retrofitParams) {
        try {
            if (retrofitParams == null) {
                return;
            }
            if (retrofitParams.isPrintApiLog()) {
                StringBuffer logsb = new StringBuffer();
                logsb.append("===============================================================\n");
                logsb.append(String.format("接口名:%s\n", retrofitParams.getApiName()));
                logsb.append(String.format("请求类型:%s\n", retrofitParams.getRequestType().name()));
                logsb.append(String.format("请求token:%s=%s\n", retrofitParams.getTokenName(), token));
                logsb.append(String.format("请求地址:%s\n", retrofitParams.getRequestUrl()));
                logsb.append(String.format("Header信息:%s\n", JsonUtils.toStr(retrofitParams.getHeadParams())));
                if (retrofitParams.getRequestType() == RequestType.DELETE) {
                    logsb.append(String.format("请求参数:%s\n", JsonUtils.toStr(retrofitParams.getDelQueryParams())));
                } else {
                    logsb.append(String.format("请求参数:%s\n", JsonUtils.toStr(retrofitParams.getParams())));
                }
                logsb.append(String.format("数据提交方式:%s\n", retrofitParams.getRequestContentType().name()));
                logsb.append(String.format("缓存信息:isCache=%s,cacheKey=%s,cacheTime=%s\n",
                        retrofitParams.isCache(),
                        retrofitParams.getCacheKey(),
                        retrofitParams.getCacheTime()));
                logsb.append(String.format("返回数据类名:%s\n", retrofitParams.getDataClass().getSimpleName()));
                logsb.append(String.format("验证是否通过:%s\n", retrofitParams.getFlag()));
                logsb.append(String.format("允许接口返回码:%s\n", retrofitParams.getAllowRetCodes()));
                logmaps.put(apiRequestKey, logsb);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }
}
