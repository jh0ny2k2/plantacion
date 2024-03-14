package com.jaroso.plantaciones.repository;

import com.jaroso.plantaciones.entity.Registro;
import com.jaroso.plantaciones.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {
    List<Registro> findBySensorAndFechaBetween(Sensor sensor, LocalDate fechaIni, LocalDate fechaFin);
    List<Registro> findBySensorAndFecha(Sensor sensor, LocalDate fecha);
}
