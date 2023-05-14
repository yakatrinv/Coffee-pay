package com.coffeepay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Product extends DataEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column
    @EqualsAndHashCode.Include
    private String name;

    @Column
    @EqualsAndHashCode.Include
    private float price;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = TABLE_DB_MACHINE_PRODUCT,
//            joinColumns = {@JoinColumn(name = ATTR_DB_PRODUCT_ID)},
//            inverseJoinColumns = {@JoinColumn(name = ATTR_DB_MACHINE_ID)})
//    private Set<Machine> machines;
}
