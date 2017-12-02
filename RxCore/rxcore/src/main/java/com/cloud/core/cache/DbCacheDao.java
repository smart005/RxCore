package com.cloud.core.cache;

import com.cloud.core.daos.CacheDataItemDao;
import com.cloud.core.daos.DaoMaster;
import com.cloud.core.daos.DaoSession;
import com.cloud.core.greens.DBManager;
import com.cloud.core.logger.Logger;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/11/14
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class DbCacheDao {
    private DaoMaster daoMaster = null;

    public CacheDataItemDao getCacheDao() {
        CacheDataItemDao dataItemDao = null;
        try {
            daoMaster = DBManager.getInstance().getCacheDaoMaster();
            if (daoMaster != null) {
                DaoSession daoSession = daoMaster.newSession();
                dataItemDao = daoSession.getCacheDataItemDao();
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return dataItemDao;
    }
}
