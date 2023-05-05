package com.coffeepay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

import static util.DataModels.ATTR_DB_USER_ID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table
public class Customer extends DataEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @EqualsAndHashCode.Include
    private String name;

    @Column
    @EqualsAndHashCode.Include
    private String surname;

    @Column
    @EqualsAndHashCode.Include
    private String phone;

    @Column
    @EqualsAndHashCode.Include
    private String email;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = ATTR_DB_USER_ID)
    @ToString.Exclude
    private User user;
}