package com.commons.support.db.config;

import android.content.Context;

import com.commons.support.db.DaoUtil;

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
        if (savedConfig == null) {
            configDao.insertOrReplace(config);
        } else {
            savedConfig.setValue(config.getValue());
            configDao.insertOrReplace(savedConfig);
        }
    }

    public static Config getConfig(String key) {
        QueryBuilder qb = configDao.queryBuilder();
        qb.where(ConfigDao.Properties.Key.eq(key));
        Config config = (Config) qb.unique();
        if (config == null) {
            config = new Config(key,"no data");
        }
        return config;
    }

    public static String getConfigValue(String key) {
        QueryBuilder qb = configDao.queryBuilder();
        qb.where(ConfigDao.Properties.Key.eq(key));
        Config config = (Config) qb.unique();
        if (config == null) {
            return "";
        }
        return config.getValue();
    }

    public static void deleteAll() {
        configDao.deleteAll();
    }

    public static void delete(String key) {
        QueryBuilder qb = configDao.queryBuilder();
        DeleteQuery bd = qb.where(ConfigDao.Properties.Key.eq(key)).buildDelete();
        bd.executeDeleteWithoutDetachingEntities();
        //or
        //Config config = getConfig(key);
        //configDao.delete(config);
    }

    public static boolean exist(String key) {
        QueryBuilder qb = configDao.queryBuilder();
        qb.where(ConfigDao.Properties.Key.eq(key));
        long cnt = qb.count();
        return cnt > 0 ? true : false;
    }

}
