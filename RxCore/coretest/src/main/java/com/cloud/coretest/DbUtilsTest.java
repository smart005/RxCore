package com.cloud.coretest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

import com.cloud.core.Action;
import com.cloud.core.cache.RxCache;
import com.cloud.coretest.beans.TestBean;
import com.cloud.coretest.daos.DaoManager;
import com.cloud.coretest.daos.TestBeanDao;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/27
 * @Description:数据库测试
 * @Modifier:
 * @ModifyContent:
 */
public class DbUtilsTest extends Activity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_utils_test);
        RxCache.setCacheData(this, "key", "测试");
        String key = RxCache.getCacheData(this, "key");
        DaoManager.getInstance().getTestBeanDao(new Action<TestBeanDao>() {
            @Override
            public void call(TestBeanDao beanDao) {
                TestBean testBean = new TestBean();
                testBean.setName("eqweqwe");
                testBean.setAge(29);
                testBean.setGender("男");
                beanDao.insert(testBean);
            }
        });
        DaoManager.getInstance().getTestBeanDao(new Action<TestBeanDao>() {
            @Override
            public void call(TestBeanDao beanDao) {
                TestBean unique = beanDao.queryBuilder().limit(1).unique();
                String name = unique.getName();
            }
        });
    }
}
