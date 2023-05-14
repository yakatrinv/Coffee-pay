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

import static util.DataModels.ATTR_DB_USE_CREDIT_CARD;
import static util.DataModels.ATTR_DB_USE_PHONE_NUMBER;
import static util.DataModels.TABLE_DB_TYPE_PAYMENT;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = TABLE_DB_TYPE_PAYMENT)
public class TypePayment extends DataEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @ToString.Include
    private String name;

    @Column(name = ATTR_DB_USE_CREDIT_CARD)
    private Boolean useCreditCard;

    @Column(name = ATTR_DB_USE_PHONE_NUMBER)
    private Boolean usePhoneNumber;
}
