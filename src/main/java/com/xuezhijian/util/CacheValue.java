package com.xuezhijian.util;

import java.io.Serializable;

public class CacheValue implements Serializable {
    private static final long serialVersionUID = 800800964682488492L;
    private String key;
    private Object value;
    private Long createTime;
    private Long timeout;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public Object getValue() {
        isTimeout();
        return value;
    }
    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isTimeout() {
        boolean flag = true;
        if (value != null) {
            flag = System.currentTimeMillis() - createTime >= timeout;
            if (flag) {
                value = null;
            }
        }
        return flag;
    }

}
