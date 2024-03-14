package com.jaroso.plantaciones.services;

import com.jaroso.plantaciones.dto.TemperaturaDTO;
import com.jaroso.plantaciones.entity.Plantacion;
import com.jaroso.plantaciones.entity.Registro;
import com.jaroso.plantaciones.entity.Sensor;
import com.jaroso.plantaciones.repository.PlantacionRepository;
import com.jaroso.plantaciones.repository.RegistroRepository;
import com.jaroso.plantaciones.repository.SensorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Autowired
    private PlantacionRepository plantacionRepository;

    @Autowired
    private RegistroRepository registroRepository;

    public Sensor crearSensor(Sensor sensor, Long plantacionId){
        Objects.requireNonNull(plantacionId);
        Plantacion plantacion = plantacionRepository.findById(plantacionId)
                .orElseThrow(() -> new EntityNotFoundException("Plantacion no encontrada"));
        sensor.setPlantacion(plantacion);
        return this.sensorRepository.save(sensor);
    }

    public void eliminarSensor(Long id) {
        this.sensorRepository.deleteById(id);
    }

    public List<Sensor> findAll() {
        return this.sensorRepository.findAll();
    }

    public Optional<Sensor> findById(Long id) {
        return this.sensorRepository.findById(id);
    }

    public TemperaturaDTO temperaturaHumedad(Sensor sensor, LocalDate fechaInicio, LocalDate fechaFin) {
        var registros = this.registroRepository.findBySensorAndFechaBetween(sensor,fechaInicio,fechaFin);
        Double temperatura = 0.0;
        Double humedad = 0.0;

        for(Registro registro : registros) {
            temperatura += registro.getTemperatura();
            humedad += registro.getHumedad();
        }

        Double TempFinal = temperatura / registros.size();
        Double HumedadFinal = humedad / registros.size();

        return new TemperaturaDTO(TempFinal , HumedadFinal);

    }

    public List<Registro> registros(Long id) {
        Sensor sensor = this.sensorRepository.findById(id).orElse(null);
        return sensor.getRegistros();
    }

    // REGISTROS SENSOR EN UNA FECHA
    public List<Registro> registrosFecha(Long id, LocalDate fecha) {
        Sensor sensor = this.sensorRepository.findById(id).orElse(null);
        return this.registroRepository.findBySensorAndFecha(sensor,fecha);
    }

    // MEDIA HISTORICA TEMPERATURA Y HUMEDAD
    public TemperaturaDTO promedioHistorico(Long id) {
        Sensor sensor = this.sensorRepository.findById(id).orElse(null);
        List<Registro> registros = sensor.getRegistros();

        Double temperatura = 0.0;

        temperatura = registros.stream().mapToDouble(Registro::getTemperatura).average().orElse(0);

        Double humedad = 0.0;

        humedad = registros.stream().mapToDouble(Registro::getHumedad).average().orElse(0);

        return new TemperaturaDTO(temperatura,humedad);
    }



}
