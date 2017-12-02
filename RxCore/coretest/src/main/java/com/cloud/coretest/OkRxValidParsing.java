package com.cloud.coretest;

import android.content.Context;

import com.cloud.core.Action;
import com.cloud.core.okrx.BaseOkgoValidParsing;
import com.cloud.core.okrx.BaseService;
import com.cloud.core.okrx.OkRxValidParam;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/8
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class OkRxValidParsing {
    public static <T extends BaseService> OkRxValidParam check(Context context, T t) {
        return BaseOkgoValidParsing.getInstance().check(context, t, new Action<T>() {
            @Override
            public void call(T t) {
                t.setToken("ee8c20d1b16aa0e3ff9c595447232d4f");
            }
        });
    }
}
