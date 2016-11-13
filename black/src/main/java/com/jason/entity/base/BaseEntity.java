package com.jason.entity.base;

/**
 * Created by fuyongde on 2016/11/12.
 */
public class BaseEntity {

    private long created;
    private long updated;

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }
}
