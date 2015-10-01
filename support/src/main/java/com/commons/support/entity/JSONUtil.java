package com.commons.support.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.Feature;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by qianjin on 2015/9/24.
 */
public class JSONUtil {

    public static final <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz, new Feature[0]);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T parseObject(byte[] input, Type clazz, Feature... features) {
        try {
            return (T) JSON.parseObject(new String(input, "UTF-8"), clazz, features);
        } catch (UnsupportedEncodingException e) {
            throw new JSONException("parseObject error", e);
        }
    }

    public static final <T> List<T> parseArray(String text, Class<T> clazz) {
        return JSON.parseArray(text, clazz);
    }

    public static String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

}
