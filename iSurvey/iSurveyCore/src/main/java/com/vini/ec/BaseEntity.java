package com.vini.ec;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class BaseEntity {
    
    @Column(name="updatedOn", columnDefinition="DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;
    
    @Column(name="createdOn", columnDefinition="DATETIME", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdOn;
    
    @Column(name="createdBy", nullable = false)
    private long createdBy;
    
    @Column(name="updatedBy", nullable = false)
    private long updatedBy;
    
    public Date getUpdatedOn() {
        return updatedOn;
    }
    
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }
    
    public Date getCreatedOn() {
        return createdOn;
    }
    
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
    
    public long getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }
    
    public long getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(long updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    @PrePersist
    public void onCreate(){
        this.createdOn = new Date();
        this.updatedOn = new Date();
    }
    
    @PreUpdate
    public void onUpdate(){
        this.updatedOn = new Date();
    }
    
}
