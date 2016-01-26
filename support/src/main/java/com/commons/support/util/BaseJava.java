package com.commons.support.util;

import java.util.List;

/**
 * Created by qianjin on 2016/1/26.
 */
public class BaseJava {

    public boolean objectNotNull(Object object) {
        if (object == null) {
            return false;
        }
        return true;
    }

    public boolean objectIsNull(Object object) {
        return !objectNotNull(object);
    }

    public boolean strNotEmpty(String str) {
        return !(str == null || str.length() == 0);
    }

    public boolean strIsEmpty(String str) {
        return !strNotEmpty(str);
    }

    public boolean listNotEmpty(List list) {
        if (list == null || list.size() == 0) {
            return false;
        }
        return true;
    }



}
