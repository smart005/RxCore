package com.cloud.coretest.daos;

import com.cloud.core.Action;
import com.cloud.core.greens.DBManager;
import com.cloud.core.logger.Logger;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/15
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseDaoManager {
    protected void getDao(final Action<DaoSession> action) {
        try {
            if (action == null) {
                return;
            }
            DBManager.getInstance().getHelper(new Action<DBManager.RxSqliteOpenHelper>() {
                @Override
                public void call(DBManager.RxSqliteOpenHelper helper) {
                    DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
                    if (daoMaster == null) {
                        return;
                    }
                    DaoSession daoSession = daoMaster.newSession();
                    if (daoSession == null) {
                        return;
                    }
                    action.call(daoSession);
                }
            });
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }
}
