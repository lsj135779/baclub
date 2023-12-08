package com.sparta.baclub.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createTimestamp;

    @Column @UpdateTimestamp
    private Timestamp updateTimestamp;
}
