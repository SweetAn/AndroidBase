package com.commons.support.entity;

import java.io.Serializable;

/**
 * Created by qianjin on 2016/3/30.
 */
public class BaseEntity implements Serializable {
    public <T extends BaseEntity> T getEntity() {
        return (T) this;
    }
}
