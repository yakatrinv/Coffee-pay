package com.coffeepay.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

import static util.DataModels.ATTR_DB_CREATE_DATE;
import static util.DataModels.ATTR_DB_UPDATE_DATE;

@Data
@NoArgsConstructor
@MappedSuperclass
public class DataEntity {
    @CreationTimestamp
    @Column(name = ATTR_DB_CREATE_DATE, updatable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = ATTR_DB_UPDATE_DATE, insertable = false)
    private LocalDateTime updateDate;
}
