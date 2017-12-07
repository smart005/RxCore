package com.cloud.core.greens;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cloud.core.Action;
import com.cloud.core.daos.CacheDataItemDao;
import com.cloud.core.daos.DaoMaster;
import com.cloud.core.logger.Logger;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoMaster;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2017/11/14
 * Description:数据库管理类
 * Modifier:
 * ModifyContent:
 */
public class DBManager {

    private static DBManager dbManager = null;
    private DaoMaster daoMaster = null;
    private static RxSqliteOpenHelper helper = null;
    private static RxSqliteOpenHelper mhelper = null;

    public static DBManager getInstance() {
        return dbManager == null ? dbManager = new DBManager() : dbManager;
    }

    public void initializeBaseDb(Context context,
                                 String databaseName,
                                 Class<? extends AbstractDao<?, ?>>... daoClasses) {
        try {
            if (helper == null) {
                helper = new RxSqliteOpenHelper(
                        context,
                        "8ed5f9e2403140f9b03be95b9674b900",
                        null,
                        CacheDataItemDao.class);
            }
            if (mhelper == null) {
                mhelper = new RxSqliteOpenHelper(
                        context,
                        databaseName,
                        null,
                        daoClasses);
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public class RxSqliteOpenHelper extends BaseSQLiteOpenHelper {
        public RxSqliteOpenHelper(Context context,
                                  String name,
                                  SQLiteDatabase.CursorFactory factory,
                                  Class<? extends AbstractDao<?, ?>>... daoClasses) {
            super(context, name, factory, daoClasses);
        }
    }

    public DaoMaster getCacheDaoMaster() {
        if (daoMaster == null && helper != null) {
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public void getHelper(Action<RxSqliteOpenHelper> helperAction) {
        if (mhelper == null || helperAction == null) {
            return;
        }
        helperAction.call(mhelper);
    }

    public void destroy() {
        try {
            if (helper == null) {
                helper.close();
            }
            if (mhelper != null) {
                mhelper.close();
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }
}
