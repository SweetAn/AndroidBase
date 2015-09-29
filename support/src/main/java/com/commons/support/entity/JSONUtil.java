package com.commons.support.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

import java.util.List;

/**
 * Created by qianjin on 2015/9/24.
 */
public class JSONUtil {

    public static final <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz, new Feature[0]);
    }

    public static final <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

}
