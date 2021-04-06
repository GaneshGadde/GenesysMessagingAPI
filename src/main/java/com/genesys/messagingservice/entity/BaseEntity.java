package com.genesys.messagingservice.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Generated(value = GenerationTime.INSERT)
    @ColumnDefault("CURRENT_TIMESTAMP(6)")
    @Column(nullable = false)
    protected LocalDateTime createdAt;

    @Generated(value = GenerationTime.INSERT)
    @ColumnDefault("CURRENT_TIMESTAMP(6) on update CURRENT_TIMESTAMP(6)")
    @Column(nullable = false)
    protected LocalDateTime lastUpdatedAt;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "createdAt=" + createdAt +
                ", lastUpdatedAt=" + lastUpdatedAt +
                '}';
    }
}