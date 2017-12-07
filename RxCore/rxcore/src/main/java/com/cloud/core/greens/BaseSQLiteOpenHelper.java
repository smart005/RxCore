package com.cloud.core.greens;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.cloud.core.daos.DaoMaster;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2017/11/14
 * Description:
 * Modifier:
 * ModifyContent:
 */
public class BaseSQLiteOpenHelper extends DaoMaster.OpenHelper {

    private Class<? extends AbstractDao<?, ?>>[] daoClasses = null;

    public BaseSQLiteOpenHelper(Context context,
                                String name,
                                SQLiteDatabase.CursorFactory factory,
                                Class<? extends AbstractDao<?, ?>>... daoClasses) {
        super(context, name, factory);
        this.daoClasses = daoClasses;
    }

    @Override
    public void onUpgrade(Database db,
                          int oldVersion,
                          int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, daoClasses);
    }
}
