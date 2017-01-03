package com.jason.black.entity.base;

import javax.persistence.MappedSuperclass;

/**
 * Created by fuyongde on 2016/11/12.
 */
@MappedSuperclass
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
