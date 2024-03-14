package com.jaroso.plantaciones.controller;

import com.jaroso.plantaciones.dto.TemperaturaDTO;
import com.jaroso.plantaciones.entity.Plantacion;
import com.jaroso.plantaciones.entity.Registro;
import com.jaroso.plantaciones.services.PlantacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class PlantacionController {

    @Autowired
    private PlantacionService plantacionService;

    @GetMapping("/plantaciones")
    public ResponseEntity<List<Plantacion>> findAll() {
        List<Plantacion> plantaciones = this.plantacionService.findAll();

        return ResponseEntity.ok( plantaciones );
    }

    @PostMapping("/plantaciones")
    public ResponseEntity<Plantacion> create(@RequestBody Plantacion plantacion) {
        this.plantacionService.crearPlantacion(plantacion);
        return ResponseEntity.ok(plantacion);
    }

    @GetMapping("/plantaciones/{id}")
    public ResponseEntity<Plantacion> findById(@PathVariable Long id) {
        Optional<Plantacion> plantacion = this.plantacionService.findById(id);
        return ResponseEntity.ok(plantacion.get());
    }

    @DeleteMapping("/plantaciones/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return this.plantacionService.findById(id).map( plantacion -> {
                    this.plantacionService.eliminarPorId(id);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/plantaciones/registros/{id}")
    public ResponseEntity<List<Registro>> registros(@PathVariable Long id) {
        List<Registro> registros = this.plantacionService.registros(id);
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/plantacion/{id}/fecha/{fecha}")
    public ResponseEntity<List<Registro>> registrosFecha(@PathVariable Long id, @PathVariable LocalDate fecha) {
        List<Registro> registros = this.plantacionService.registrosFecha(id,fecha);
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/plantacion/{id}/promedio/fecha/{fecha}")
    public ResponseEntity<TemperaturaDTO> promedioFecha(@PathVariable Long id, @PathVariable LocalDate fecha) {
        TemperaturaDTO temperatura = this.plantacionService.promedioFecha(id,fecha);
        return ResponseEntity.ok(temperatura);
    }


}
