package com.cloud.core.cache;

import android.content.Context;
import android.text.TextUtils;

import com.cloud.core.EmulatorJudge;
import com.cloud.core.ObjectJudge;
import com.cloud.core.daos.CacheDataItemDao;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;
import com.cloud.core.utils.JsonUtils;
import com.cloud.core.utils.SharedPrefUtils;
import com.cloud.core.utils.StorageUtils;

import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.String.valueOf;

/**
 * Author lijinghuan
 * Email:ljh0576123@163.com
 * CreateTime:2016/10/14
 * Description:
 * Modifier:
 * ModifyContent:
 */

public class RxCache {

    private static HashMap<String, Object> redisCacheList = new HashMap<String, Object>();

    //0:内存;1:sp缓存;2:文件缓存;3:数据库缓存;
    private static int depthCacheType(Context context) {
        boolean hasSDCard = ObjectJudge.hasSDCard();
        if (context == null) {
            return hasSDCard ? 2 : 0;
        } else {
            if (!EmulatorJudge.isEmulator(context)) {
                return 3;
            } else {
                return 1;
            }
        }
    }

    /**
     * 设置缓存数据
     *
     * param cacheKey 缓存键
     * param value    缓存数据
     * param saveTime 缓存时间
     * param timeUnit 时间单位
     */
    private static <T> void setBaseCacheData(Context context,
                                             String cacheKey,
                                             T value,
                                             long saveTime,
                                             TimeUnit timeUnit) {
        try {
            if (TextUtils.isEmpty(cacheKey) || value == null) {
                return;
            }
            remove(context, cacheKey);
            CacheDataItem dataItem = new CacheDataItem();
            if (saveTime > 0 && timeUnit != null) {
                int time = ConvertUtils.toMilliseconds(saveTime, timeUnit);
                if (time > 0) {
                    long mtime = System.currentTimeMillis() + time;
                    dataItem.setKey(cacheKey);
                    setCacheValue(value, dataItem);
                    dataItem.setEffective(mtime);
                } else {
                    dataItem.setKey(cacheKey);
                    setCacheValue(value, dataItem);
                }
            } else {
                dataItem.setKey(cacheKey);
                setCacheValue(value, dataItem);
            }
            int depthCacheType = depthCacheType(context);
            if (depthCacheType == 0) {
                redisCacheList.put(cacheKey, dataItem);
            } else if (depthCacheType == 1) {
                String cacheJson = JsonUtils.toStr(dataItem);
                SharedPrefUtils.setPrefString(context, cacheKey, cacheJson);
            } else if (depthCacheType == 2) {
                String cacheJson = JsonUtils.toStr(dataItem);
                File dir = StorageUtils.getDataCachesDir();
                File file = new File(dir, cacheKey);
                StorageUtils.save(cacheJson, file);
            } else if (depthCacheType == 3) {
                DbCacheDao dbCacheDao = new DbCacheDao();
                CacheDataItemDao cacheDao = dbCacheDao.getCacheDao();
                if (cacheDao != null) {
                    CacheDataItemDao.createTable(cacheDao.getDatabase(), true);
                    cacheDao.insertOrReplace(dataItem);
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private static <T> void setCacheValue(T value, CacheDataItem dataItem) {
        if (value instanceof String) {
            dataItem.setValue(valueOf(value));
        } else if (value instanceof Boolean) {
            dataItem.setFlag((Boolean) value);
        } else if (value instanceof Integer) {
            dataItem.setIniValue(ConvertUtils.toInt(value));
        } else if (value instanceof Long) {
            dataItem.setLongValue(ConvertUtils.toLong(value));
        }
    }

    /**
     * 获取缓存数据
     *
     * param cacheKey 缓存键
     * return
     */
    private static CacheDataItem getBaseCacheData(Context context, String cacheKey) {
        try {
            if (TextUtils.isEmpty(cacheKey)) {
                return null;
            }
            CacheDataItem dataItem = null;
            CacheDataItem first = null;
            int depthCacheType = depthCacheType(context);
            if (depthCacheType == 0) {
                dataItem = new CacheDataItem();
                if (redisCacheList.containsKey(cacheKey)) {
                    first = (CacheDataItem) redisCacheList.get(cacheKey);
                }
            } else if (depthCacheType == 1) {
                dataItem = new CacheDataItem();
                String cacheJson = SharedPrefUtils.getPrefString(context, cacheKey);
                first = JsonUtils.parseT(cacheJson, CacheDataItem.class);
            } else if (depthCacheType == 2) {
                dataItem = new CacheDataItem();
                File dir = StorageUtils.getDataCachesDir();
                File file = new File(dir, cacheKey);
                String content = StorageUtils.readContent(file);
                first = JsonUtils.parseT(content, CacheDataItem.class);
            } else if (depthCacheType == 3) {
                DbCacheDao dbCacheDao = new DbCacheDao();
                CacheDataItemDao cacheDao = dbCacheDao.getCacheDao();
                if (cacheDao != null) {
                    CacheDataItemDao.createTable(cacheDao.getDatabase(), true);
                    QueryBuilder<CacheDataItem> builder = cacheDao.queryBuilder();
                    QueryBuilder<CacheDataItem> where = builder.where(CacheDataItemDao.Properties.Key.eq(cacheKey));
                    QueryBuilder<CacheDataItem> limit = where.limit(1);
                    if (limit != null) {
                        first = limit.unique();
                    }
                }
            }
            if (first == null) {
                return dataItem;
            }
            if (first.getEffective() > 0) {
                if (first.getEffective() > System.currentTimeMillis()) {
                    return first;
                } else {
                    clear(context, depthCacheType, true, cacheKey);
                    return dataItem;
                }
            } else {
                return first;
            }
        } catch (Exception e) {
            Logger.L.error("get cache data error:", e);
        }
        return null;
    }

    /**
     * 获取缓存数据
     *
     * param cacheKey 缓存键
     * return
     */
    public static String getCacheData(Context context, String cacheKey) {
        if (redisCacheList.containsKey(cacheKey)) {
            Object o = redisCacheList.get(cacheKey);
            if (o == null) {
                CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
                if (dataItem == null) {
                    return "";
                } else {
                    if (dataItem.getEffective() != 0) {
                        redisCacheList.put(cacheKey, dataItem.getValue());
                    }
                    return dataItem.getValue();
                }
            } else {
                String result = String.valueOf(o);
                if (TextUtils.isEmpty(result)) {
                    CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
                    if (dataItem == null) {
                        return "";
                    } else {
                        if (dataItem.getEffective() != 0) {
                            redisCacheList.put(cacheKey, dataItem.getValue());
                        }
                        return dataItem.getValue();
                    }
                } else {
                    return result;
                }
            }
        } else {
            CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
            if (dataItem == null) {
                return "";
            } else {
                if (dataItem.getEffective() != 0) {
                    redisCacheList.put(cacheKey, dataItem.getValue());
                }
                return dataItem.getValue();
            }
        }
    }

    /**
     * 设置缓存数据
     *
     * param cacheKey 缓存键
     * param value    缓存数据
     * param saveTime 缓存时间
     * param timeUnit 时间单位
     */
    public static void setCacheData(Context context,
                                    String cacheKey,
                                    String value,
                                    long saveTime,
                                    TimeUnit timeUnit) {
        setBaseCacheData(context, cacheKey, value, saveTime, timeUnit);
    }

    /**
     * 设置缓存数据
     *
     * param cacheKey 缓存键
     * param value    缓存数据
     */
    public static void setCacheData(Context context, String cacheKey, String value) {
        setBaseCacheData(context, cacheKey, value, 0, null);
    }

    /**
     * 清空所有缓存
     */
    public static void clear(Context context) {
        try {
            int depthCacheType = depthCacheType(context);
            List<String> keys = new ArrayList<String>();
            for (Map.Entry<String, Object> entry : redisCacheList.entrySet()) {
                if (entry.getValue() instanceof CacheDataItem) {
                    keys.add(entry.getKey());
                }
            }
            File dir = StorageUtils.getDataCachesDir();
            for (String key : keys) {
                redisCacheList.remove(key);
                if (depthCacheType == 1) {
                    SharedPrefUtils.setPrefString(context, key, "");
                } else if (depthCacheType == 2) {
                    StorageUtils.deleteQuietly(new File(dir, key));
                }
            }
            if (depthCacheType == 3) {
                DbCacheDao dbCacheDao = new DbCacheDao();
                CacheDataItemDao cacheDao = dbCacheDao.getCacheDao();
                if (cacheDao != null) {
                    CacheDataItemDao.createTable(cacheDao.getDatabase(), true);
                    cacheDao.deleteAll();
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    private static void clear(Context context, int depthCacheType, boolean isBlurClear, String containsKey) {
        try {
            List<String> keys = new ArrayList<String>();
            for (Map.Entry<String, Object> entry : redisCacheList.entrySet()) {
                if (entry.getValue() instanceof CacheDataItem) {
                    CacheDataItem value = (CacheDataItem) entry.getValue();
                    if (isBlurClear) {
                        if (value.getKey().contains(containsKey)) {
                            keys.add(entry.getKey());
                        }
                    } else {
                        if (TextUtils.equals(value.getKey(), containsKey)) {
                            keys.add(entry.getKey());
                        }
                    }
                }
            }
            File dir = StorageUtils.getDataCachesDir();
            for (String key : keys) {
                redisCacheList.remove(key);
                if (depthCacheType == 1) {
                    SharedPrefUtils.setPrefString(context, key, "");
                } else if (depthCacheType == 2) {
                    StorageUtils.deleteQuietly(new File(dir, key));
                }
            }
            if (depthCacheType == 3) {
                DbCacheDao dbCacheDao = new DbCacheDao();
                CacheDataItemDao cacheDao = dbCacheDao.getCacheDao();
                if (cacheDao != null) {
                    CacheDataItemDao.createTable(cacheDao.getDatabase(), true);
                    if (isBlurClear) {
                        QueryBuilder<CacheDataItem> builder = cacheDao.queryBuilder();
                        QueryBuilder<CacheDataItem> where = builder.where(CacheDataItemDao.Properties.Key.like(containsKey));
                        List<CacheDataItem> dataItems = where.list();
                        if (!ObjectJudge.isNullOrEmpty(dataItems)) {
                            cacheDao.deleteInTx(dataItems);
                        }
                    } else {
                        QueryBuilder<CacheDataItem> builder = cacheDao.queryBuilder();
                        QueryBuilder<CacheDataItem> where = builder.where(CacheDataItemDao.Properties.Key.eq(containsKey));
                        QueryBuilder<CacheDataItem> limit = where.limit(1);
                        if (limit != null) {
                            CacheDataItem unique = limit.unique();
                            if (unique == null) {
                                cacheDao.delete(unique);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    public static void clearForKey(Context context, String key) {
        int depthCacheType = depthCacheType(context);
        clear(context, depthCacheType, false, key);
    }

    public static void clearContainerKey(Context context, String containsKey) {
        int depthCacheType = depthCacheType(context);
        clear(context, depthCacheType, true, containsKey);
    }

    /**
     * 移除指定缓存
     *
     * param cacheKey 缓存key
     */
    public static void remove(Context context, String cacheKey) {
        try {
            int depthCacheType = depthCacheType(context);
            String thisCacheKey = "";
            for (Map.Entry<String, Object> entry : redisCacheList.entrySet()) {
                if (entry.getValue() instanceof CacheDataItem) {
                    CacheDataItem value = (CacheDataItem) entry.getValue();
                    if (TextUtils.equals(value.getKey(), cacheKey)) {
                        thisCacheKey = entry.getKey();
                        break;
                    }
                }
            }
            if (!TextUtils.isEmpty(thisCacheKey)) {
                redisCacheList.remove(thisCacheKey);
            }
            if (depthCacheType == 1) {
                SharedPrefUtils.setPrefString(context, thisCacheKey, "");
            } else if (depthCacheType == 2) {
                File dir = StorageUtils.getDataCachesDir();
                StorageUtils.deleteQuietly(new File(dir, thisCacheKey));
            } else if (depthCacheType == 3) {
                DbCacheDao dbCacheDao = new DbCacheDao();
                CacheDataItemDao cacheDao = dbCacheDao.getCacheDao();
                if (cacheDao != null) {
                    CacheDataItemDao.createTable(cacheDao.getDatabase(), true);
                    QueryBuilder<CacheDataItem> builder = cacheDao.queryBuilder();
                    QueryBuilder<CacheDataItem> where = builder.where(CacheDataItemDao.Properties.Key.eq(cacheKey));
                    QueryBuilder<CacheDataItem> limit = where.limit(1);
                    if (limit != null) {
                        CacheDataItem unique = limit.unique();
                        if (unique == null) {
                            cacheDao.delete(unique);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }

    /**
     * 设置缓存数据
     *
     * param cacheKey 缓存键
     * param value    缓存数据
     * param saveTime 缓存时间
     * param timeUnit 时间单位
     */
    public static void setJsonObject(Context context,
                                     String cacheKey,
                                     JSONObject value,
                                     long saveTime,
                                     TimeUnit timeUnit) {
        if (TextUtils.isEmpty(cacheKey) || value == null) {
            return;
        }
        setBaseCacheData(context, cacheKey, value.toString(), saveTime, timeUnit);
    }

    /**
     * 设置缓存数据
     *
     * param cacheKey 缓存键
     * param value    缓存数据
     */
    public static void setJsonObject(Context context, String cacheKey, JSONObject value) {
        if (TextUtils.isEmpty(cacheKey) || value == null) {
            return;
        }
        setBaseCacheData(context, cacheKey, value.toString(), 0, null);
    }

    /**
     * 获取缓存数据
     *
     * param cacheKey 缓存键
     * return
     */
    public static JSONObject getJsonObject(Context context, String cacheKey) {
        try {
            CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
            return dataItem == null ? new JSONObject() : new JSONObject(dataItem.getValue());
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    /**
     * 设置缓存数据
     *
     * param cacheKey 缓存键
     * param value    缓存数据
     * param saveTime 缓存时间
     * param timeUnit 时间单位
     */
    public static void setJsonArray(Context context, String cacheKey, JSONArray value, long saveTime, TimeUnit timeUnit) {
        if (TextUtils.isEmpty(cacheKey) || value == null) {
            return;
        }
        setBaseCacheData(context, cacheKey, value.toString(), saveTime, timeUnit);
    }

    /**
     * 设置缓存数据
     *
     * param cacheKey 缓存键
     * param value    缓存数据
     */
    public static void setJsonArray(Context context, String cacheKey, JSONArray value) {
        setJsonArray(context, cacheKey, value, 0, null);
    }

    /**
     * 获取缓存数据
     *
     * param cacheKey 缓存键
     * return
     */
    public static JSONArray getJsonArray(Context context, String cacheKey) {
        try {
            CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
            return dataItem == null ? new JSONArray() : new JSONArray(dataItem.getValue());
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    public static <T> void setCacheObject(Context context,
                                          String cacheKey,
                                          T data,
                                          long saveTime,
                                          TimeUnit timeUnit) {
        if (data == null) {
            return;
        }
        String value = JsonUtils.toStr(data);
        setBaseCacheData(context, cacheKey, value, saveTime, timeUnit);
    }

    public static <T> T getCacheObject(Context context, String cacheKey, Class<T> dataClass) {
        CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
        return dataItem == null ? JsonUtils.newNull(dataClass) : JsonUtils.parseT(dataItem.getValue(), dataClass);
    }

    public static <T> List<T> getCacheList(Context context, String cacheKey, Class<T> dataClass) {
        CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
        return dataItem == null ? new ArrayList<T>() : JsonUtils.parseArray(dataItem.getValue(), dataClass);
    }

    public static void setCacheFlag(Context context, String cacheKey, boolean flag) {
        setBaseCacheData(context, cacheKey, flag, 0, null);
    }

    public static boolean getCacheFlag(Context context, String cacheKey, boolean defaultValue) {
        if (redisCacheList.containsKey(cacheKey)) {
            Object o = redisCacheList.get(cacheKey);
            if (o == null) {
                CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
                boolean value = (dataItem == null ? defaultValue : dataItem.getFlag());
                redisCacheList.put(cacheKey, value);
                return value;
            } else {
                return (boolean) o;
            }
        } else {
            CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
            return dataItem == null ? defaultValue : dataItem.getFlag();
        }
    }

    public static boolean getCacheFlag(Context context, String cacheKey) {
        return getCacheFlag(context, cacheKey, false);
    }

    public static void setCacheInt(Context context, String cacheKey, int value) {
        setBaseCacheData(context, cacheKey, value, 0, null);
    }

    public static int getCacheInt(Context context, String cacheKey) {
        if (redisCacheList.containsKey(cacheKey)) {
            Object o = redisCacheList.get(cacheKey);
            if (o == null) {
                CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
                int value = dataItem == null ? 0 : dataItem.getIniValue();
                redisCacheList.put(cacheKey, value);
                return value;
            } else {
                return ConvertUtils.toInt(o);
            }
        } else {
            CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
            int value = dataItem == null ? 0 : dataItem.getIniValue();
            redisCacheList.put(cacheKey, value);
            return value;
        }
    }

    public static void setCacheLong(Context context, String cacheKey, long value) {
        setBaseCacheData(context, cacheKey, value, 0, null);
    }

    public static long getCacheLong(Context context, String cacheKey) {
        if (redisCacheList.containsKey(cacheKey)) {
            Object o = redisCacheList.get(cacheKey);
            if (o == null) {
                CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
                long value = dataItem == null ? 0 : dataItem.getLongValue();
                redisCacheList.put(cacheKey, value);
                return value;
            } else {
                return ConvertUtils.toLong(o);
            }
        } else {
            CacheDataItem dataItem = getBaseCacheData(context, cacheKey);
            long value = dataItem == null ? 0 : dataItem.getLongValue();
            redisCacheList.put(cacheKey, value);
            return value;
        }
    }
}
