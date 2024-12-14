package com.dental_flamingos.repository;

import com.dental_flamingos.model.CatalogoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogoStatusRepository extends JpaRepository<CatalogoStatus, Integer> {
}
