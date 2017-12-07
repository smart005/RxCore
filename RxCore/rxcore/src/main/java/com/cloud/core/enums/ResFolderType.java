package com.cloud.core.enums;

import android.text.TextUtils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/12/5
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public enum ResFolderType {
    String("strings"),
    Array("arrays"),
    Color("colors"),
    Dimen("dimens"),
    Drawable("drawable"),
    Mipmap("mipmap");
    private String value = "";

    ResFolderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static final ResFolderType getResFolderType(String value) {
        ResFolderType currEnum = null;
        for (ResFolderType e : ResFolderType.values()) {
            if (TextUtils.equals(e.getValue(), value)) {
                currEnum = e;
                break;
            }
        }
        return currEnum;
    }
}
