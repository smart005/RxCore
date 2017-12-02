package com.cloud.core.okrx.requests;

import android.content.Context;
import android.text.TextUtils;

import com.cloud.core.Action;
import com.cloud.core.Action0;
import com.cloud.core.logger.Logger;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;

import java.io.File;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/15
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class OkRxDownloadFileRequest implements BaseFileRequest {
    @Override
    public void call(Context context,
                     String url,
                     HttpHeaders httpHeaders,
                     HttpParams httpParams,
                     String destFileDir,
                     String destFileName,
                     final Action<Float> progressAction,
                     final Action<File> successAction,
                     final Action0 completeAction) {
        try {
            if (completeAction == null) {
                return;
            }
            if (context == null || TextUtils.isEmpty(url)) {
                completeAction.call();
                return;
            }
            GetRequest<File> request = OkGo.<File>get(url).tag(context);
            if (httpHeaders != null) {
                request.headers(httpHeaders);
            }
            if (httpParams != null) {
                request.params(httpParams);
            }
            request.execute(new FileCallback(destFileDir, destFileName) {
                @Override
                public void downloadProgress(Progress progress) {
                    if (progressAction != null) {
                        progressAction.call((float) (progress.currentSize * 1.0 / progress.totalSize));
                    }
                }

                @Override
                public void onSuccess(Response<File> response) {
                    try {
                        if (successAction != null && response != null) {
                            successAction.call(response.body());
                        }
                    } catch (Exception e) {
                        completeAction.call();
                        Logger.L.error(e);
                    }
                }

                @Override
                public void onError(Response<File> response) {
                    completeAction.call();
                }

                @Override
                public void onFinish() {
                    completeAction.call();
                }
            });
        } catch (Exception e) {
            completeAction.call();
            Logger.L.error(e);
        }
    }
}
