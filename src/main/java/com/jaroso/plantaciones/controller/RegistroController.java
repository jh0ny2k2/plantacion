package com.jaroso.plantaciones.controller;

import com.jaroso.plantaciones.entity.Registro;
import com.jaroso.plantaciones.services.RegistroService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class RegistroController {

    @Autowired
    private RegistroService registroService;

    @GetMapping("/registros")
    public ResponseEntity<List<Registro>> findAll() {
        List<Registro> registros = this.registroService.findAll();
        return ResponseEntity.ok(registros);
    }

    @GetMapping("/registros/{id}")
    public ResponseEntity<Registro> findById(@PathVariable Long id) {
        Optional<Registro> registro = this.registroService.findById(id);
        return ResponseEntity.ok(registro.get());
    }

    @PostMapping("/registros/{id}")
    public ResponseEntity<?> create(@RequestBody Registro registro, @PathVariable Long id) {
        try {
            this.registroService.crearRegistro(registro, id);
        } catch (EntityNotFoundException ex) {
            return new ResponseEntity<>("Sensor no encontrado con ID: " + id, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(registro);
    }

    @DeleteMapping("/registros/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return this.registroService.findById(id)
                .map( m -> {
                    this.registroService.eliminarRegistro(id);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }



}
