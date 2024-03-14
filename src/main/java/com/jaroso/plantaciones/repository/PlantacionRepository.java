package com.jaroso.plantaciones.repository;

import com.jaroso.plantaciones.entity.Plantacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantacionRepository extends JpaRepository<Plantacion, Long> {
}
