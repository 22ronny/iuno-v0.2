package com.iuno.fuel.repository;

import com.iuno.fuel.entity.FuelStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelRepository extends JpaRepository<FuelStation, Long> {
}
