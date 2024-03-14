package com.jaroso.plantaciones.services;

import com.jaroso.plantaciones.entity.Registro;
import com.jaroso.plantaciones.entity.Sensor;
import com.jaroso.plantaciones.repository.PlantacionRepository;
import com.jaroso.plantaciones.repository.RegistroRepository;
import com.jaroso.plantaciones.repository.SensorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroService {

    @Autowired
    private RegistroRepository registroRepository;

    @Autowired
    private SensorRepository sensorRepository;

    public List<Registro> findAll() {
        return this.registroRepository.findAll();
    }

    public Optional<Registro> findById(Long id) {
        return this.registroRepository.findById(id);
    }

    public Registro crearRegistro(Registro registro, Long id) {
        Sensor sensor = sensorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Sensor no encontrado"));
        registro.setSensor(sensor);
        return this.registroRepository.save(registro);
    }

    public void eliminarRegistro(Long id) {
        this.registroRepository.deleteById(id);
    }

}
