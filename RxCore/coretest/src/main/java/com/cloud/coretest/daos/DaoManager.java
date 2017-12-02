package com.cloud.coretest.daos;

import com.cloud.core.Action;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/15
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class DaoManager extends BaseDaoManager {

    private static DaoManager daoManager = null;

    public static DaoManager getInstance() {
        return daoManager == null ? daoManager = new DaoManager() : daoManager;
    }

    public void getTestBeanDao(final Action<TestBeanDao> action) {
        if (action == null) {
            return;
        }
        super.getDao(new Action<DaoSession>() {
            @Override
            public void call(DaoSession daoSession) {
                TestBeanDao testBeanDao = daoSession.getTestBeanDao();
                if (testBeanDao == null) {
                    return;
                }
                TestBeanDao.createTable(daoSession.getDatabase(), true);
                action.call(testBeanDao);
            }
        });
    }
}
