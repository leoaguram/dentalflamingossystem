package com.dental_flamingos.repository;

import com.dental_flamingos.model.Dentista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistaRepository extends JpaRepository<Dentista, Integer> {
    Dentista findByUsuario(String usuario);

}
