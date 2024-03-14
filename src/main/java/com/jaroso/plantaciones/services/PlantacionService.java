package com.jaroso.plantaciones.services;

import com.jaroso.plantaciones.controller.RegistroController;
import com.jaroso.plantaciones.dto.TemperaturaDTO;
import com.jaroso.plantaciones.entity.Plantacion;
import com.jaroso.plantaciones.entity.Registro;
import com.jaroso.plantaciones.repository.PlantacionRepository;
import com.jaroso.plantaciones.repository.RegistroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlantacionService {

    private final PlantacionRepository plantacionRepository;

    @Autowired
    public PlantacionService(PlantacionRepository plantacionRepository) {
        this.plantacionRepository = plantacionRepository;
    }

    @Autowired
    public RegistroRepository registroRepository;

    public Plantacion crearPlantacion(Plantacion plantacion) {
        return this.plantacionRepository.save(plantacion);
    }

    public void eliminarPorId(Long id) {
        this.plantacionRepository.deleteById(id);
    }

    public List<Plantacion> findAll() {
        return this.plantacionRepository.findAll();
    }

    public Optional<Plantacion> findById(Long id) {
        return this.plantacionRepository.findById(id);
    }

    public List<Registro> registros(Long id) {
        Plantacion plantacion = this.plantacionRepository.findById(id).orElse(null);

        List<Registro> registro = plantacion.getSensores().stream().map(sensor -> sensor.getRegistros())
                .flatMap(List::stream).toList();

        return  registro;
    }

    public List<Registro> registrosFecha(Long id, LocalDate fecha) {
        Plantacion plantacion = this.plantacionRepository.findById(id).orElse(null);

        List<Registro> registros = plantacion.getSensores().stream()
                .map(sensor -> this.registroRepository.findBySensorAndFecha(sensor,fecha)).flatMap(List::stream).toList();

        return  registros;
    }

    public TemperaturaDTO promedioFecha(Long id, LocalDate fecha) {
        Plantacion plantacion = this.plantacionRepository.findById(id).orElse(null);

        List<Registro> registros = plantacion.getSensores().stream()
                .map(sensor -> this.registroRepository.findBySensorAndFecha(sensor,fecha)).flatMap(List::stream).toList();

        Double humedad = registros.stream().mapToDouble(Registro::getHumedad).average().orElse(0);

        Double temperatura = registros.stream().mapToDouble(Registro::getTemperatura).average().orElse(0);

        return new TemperaturaDTO(temperatura,humedad);
    }
}
