package com.coffeepay.repository;

import com.coffeepay.model.ModelMachine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ModelMachineRepository extends JpaRepository<ModelMachine, Long>, JpaSpecificationExecutor<ModelMachine> {
}
