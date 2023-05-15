package com.coffeepay.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

import static util.DataModels.ATTR_DB_CREDIT_CARD_ID;
import static util.DataModels.ATTR_DB_DISCOUNT_ID;
import static util.DataModels.ATTR_DB_MACHINE_ID;
import static util.DataModels.ATTR_DB_PRODUCT_ID;
import static util.DataModels.ATTR_DB_TYPE_PAYMENT_ID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Purchase extends DataEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = ATTR_DB_MACHINE_ID)
    @ToString.Exclude
    private Machine machine;

    @ManyToOne
    @JoinColumn(name = ATTR_DB_PRODUCT_ID)
    @ToString.Exclude
    private Product product;

    @Column
    private float price;

    @ManyToOne
    @JoinColumn(name = ATTR_DB_DISCOUNT_ID)
    @ToString.Exclude
    private Discount discount;

    @Column
    private float sum;

    @ManyToOne
    @JoinColumn(name = ATTR_DB_CREDIT_CARD_ID)
    private CreditCard creditCard;

    @ManyToOne
    @JoinColumn(name = ATTR_DB_TYPE_PAYMENT_ID)
    private TypePayment typePayment;
}
