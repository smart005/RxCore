package com.cloud.coretest;

import android.app.Application;

import com.cloud.core.beans.StorageInitParam;
import com.cloud.core.greens.DBManager;
import com.cloud.core.okrx.OkRxBase;
import com.cloud.core.utils.StorageUtils;
import com.cloud.coretest.daos.TestBeanDao;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/1
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class TestApplication extends Application {

    static {
        //目录配置
        StorageUtils.setOnStorageInitListener(new StorageUtils.OnStorageInitListener() {
            @Override
            public StorageInitParam getStorageInit() {
                StorageInitParam storageInitParam = new StorageInitParam();
                storageInitParam.setAppDir("mibao");
                return storageInitParam;
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //OkGo初始化
        OkRxBase.getInstance().init(this);
        DBManager.getInstance().initializeBaseDb(
                this,
                "coretest.db",
                TestBeanDao.class);
    }
}
