package com.jaroso.plantaciones.controller;

import com.jaroso.plantaciones.dto.TemperaturaDTO;
import com.jaroso.plantaciones.entity.Plantacion;
import com.jaroso.plantaciones.entity.Registro;
import com.jaroso.plantaciones.entity.Sensor;
import com.jaroso.plantaciones.repository.PlantacionRepository;
import com.jaroso.plantaciones.services.SensorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @Autowired
    private PlantacionRepository plantacionRepository;

    @GetMapping("/sensores")
    public ResponseEntity<List<Sensor>> findAll() {
        List<Sensor> sensores = this.sensorService.findAll();
        return ResponseEntity.ok(sensores);
    }

    @GetMapping("/sensores/{id}")
    public ResponseEntity<Sensor> findById(@PathVariable Long id)  {
        Optional<Sensor> sensor = this.sensorService.findById(id);
        return ResponseEntity.ok(sensor.get());
    }

    @PostMapping("/sensores/{id}")
    public ResponseEntity<?> create(@RequestBody Sensor sensor,@PathVariable Long id) {
        try {
            this.sensorService.crearSensor(sensor, id);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>("Plantacion no encontrada con Id:" + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(sensor);
    }

    @DeleteMapping("/sensores/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return this.sensorService.findById(id)
                .map( m -> {
                    this.sensorService.eliminarSensor(id);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/informes/sensor/{id}/fechaInicio/{fi}/fechaFin/{ff}")
    public ResponseEntity<TemperaturaDTO> promedio(@PathVariable Long id, @PathVariable LocalDate fi, @PathVariable LocalDate ff) {
        var sensor = this.sensorService.findById(id).orElse(null);

        TemperaturaDTO temperatura = this.sensorService.temperaturaHumedad(sensor,fi,ff);
        return ResponseEntity.ok(temperatura);
    }

    @GetMapping("/sensor/{id}")
    public ResponseEntity<List<Registro>> registros(@PathVariable Long id) {
        List<Registro> registros = this.sensorService.registros(id);
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/sensor/{id}/fecha/{fecha}")
    public ResponseEntity<List<Registro>> registroFecha(@PathVariable Long id, @PathVariable LocalDate fecha) {
        List<Registro> registros = this.sensorService.registrosFecha(id,fecha);
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/sensor/{id}/media")
    public ResponseEntity<TemperaturaDTO> promedioHistorico(@PathVariable Long id){
        TemperaturaDTO temperatura = this.sensorService.promedioHistorico(id);
        return ResponseEntity.ok(temperatura);
    }
}
