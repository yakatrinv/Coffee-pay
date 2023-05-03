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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Set;

import static util.DataModels.ATTR_DB_ROLE_ID;
import static util.DataModels.ATTR_DB_USER_ID;
import static util.DataModels.TABLE_DB_USERS;
import static util.DataModels.TABLE_DB_USER_ROLE;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = TABLE_DB_USERS)
public class User extends DataEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @EqualsAndHashCode.Include
    private String username;

    @Column
    private String password;
    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = TABLE_DB_USER_ROLE,
            joinColumns = {@JoinColumn(name = ATTR_DB_USER_ID)},
            inverseJoinColumns = {@JoinColumn(name = ATTR_DB_ROLE_ID)})
    @ToString.Exclude
    private Set<Role> roles;
}
