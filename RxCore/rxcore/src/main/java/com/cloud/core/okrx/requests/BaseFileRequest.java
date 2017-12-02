package com.cloud.core.okrx.requests;

import android.content.Context;

import com.cloud.core.Action;
import com.cloud.core.Action0;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import java.io.File;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/15
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public interface BaseFileRequest {
    public void call(Context context,
                     String url,
                     HttpHeaders httpHeaders,
                     HttpParams httpParams,
                     String destFileDir,
                     String destFileName,
                     Action<Float> progress,
                     Action<File> successAction,
                     Action0 completeAction);
}
