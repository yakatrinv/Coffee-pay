
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

import static util.DataModels.ATTR_DB_CUSTOMER_ID;
import static util.DataModels.TABLE_DB_CREDIT_CARD;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = TABLE_DB_CREDIT_CARD)
public class CreditCard extends DataEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @ToString.Include
    private String number;

    @ManyToOne
    @JoinColumn(name = ATTR_DB_CUSTOMER_ID)
    private Customer customer;
}
