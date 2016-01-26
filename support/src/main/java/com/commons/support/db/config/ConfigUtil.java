package com.commons.support.db.config;

import android.content.Context;
import android.text.TextUtils;

import com.commons.support.db.DaoUtil;
import com.commons.support.entity.JSONUtil;

import de.greenrobot.dao.query.DeleteQuery;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by qianjin on 2015/9/7.
 */
public class ConfigUtil {

    private static ConfigDao configDao;

    /**
     * 使用ConfigUtil前需init
     * @param context
     */
    public static void init(Context context) {
        configDao = DaoUtil.getDaoSession(context).getConfigDao();
    }


    public static void save(Config config) {
        Config savedConfig = getConfig(config.getKey());
        if (savedConfig == null) { // 不会出现为空的情况
            configDao.insertOrReplace(config);
        } else {
            savedConfig.setValue(config.getValue());
            configDao.insertOrReplace(savedConfig);
        }
    }

    public static void save(String key, String value) {
        Config config = new Config(key, value);
        save(config);
    }

    public static void save(String key, Object value) {
        Config config = new Config(key, JSONUtil.toJSONString(value));
        save(config);
    }

    public static void save(String key, int value) {
        save(key, String.valueOf(value));
    }

    public static void save(String key, boolean value) {
        save(key, String.valueOf(value));
    }

    private static Config getConfig(String key) {
        QueryBuilder qb = configDao.queryBuilder();
        qb.where(ConfigDao.Properties.Key.eq(key));
        Config config = (Config) qb.unique();
        if (config == null) {
            config = new Config(key, "");
        }
        return config;
    }

    public static String getConfigValue(String key) {
        return getConfig(key).getValue();
    }


    public static int getIntConfigValue(String key) {
        String value = getConfigValue(key);
        if (!TextUtils.isEmpty(value)) {
           return Integer.valueOf(value);
        }
        return 0;
    }

    public static boolean getBooleanConfigValue(String key) {
        String value = getConfigValue(key);
        if (!TextUtils.isEmpty(value)) {
            return Boolean.valueOf(value);
        }
        return false;
    }

    public static void deleteAll() {
        configDao.deleteAll();
    }

    public static void delete(String key) {
        QueryBuilder qb = configDao.queryBuilder();
        DeleteQuery bd = qb.where(ConfigDao.Properties.Key.eq(key)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
    }

    public static boolean exist(String key) {
        QueryBuilder qb = configDao.queryBuilder();
        qb.where(ConfigDao.Properties.Key.eq(key));
        long cnt = qb.count();
        return cnt > 0 ? true : false;
    }

}
