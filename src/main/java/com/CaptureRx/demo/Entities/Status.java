package com.CaptureRx.demo.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "status")
public class Status {

    public static final Long NEW_STATUS = 1L;
    public static final Long IN_PROCESSING_STATUS = 2L;
    public static final Long DONE_STATUS = 3L;

    public Status(){}

    @Id
    private long statusId;
    private String name;

    public long getStatusId() {
        return statusId;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
