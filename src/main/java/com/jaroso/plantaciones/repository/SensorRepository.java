package com.jaroso.plantaciones.repository;

import com.jaroso.plantaciones.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    @Query("SELECT sensor FROM Sensor sensor WHERE sensor.idSensor = :sensorId AND sensor.fechaInstalacion BETWEEN :fechaInicio AND :fechaFinal")
    List<Sensor> findByIdAndTimestampsBetween(@Param("sensorId") Long sensorId,
                                              @Param("fechaInicio") Date fechaInicio,
                                              @Param("fechaFinal") Date fechaFinal);
}
