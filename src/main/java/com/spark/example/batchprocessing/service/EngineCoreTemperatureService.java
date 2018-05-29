package com.spark.example.batchprocessing.service;

import com.datastax.driver.core.utils.UUIDs;
import com.spark.example.batchprocessing.domain.EngineCoreTemperature;
import com.spark.example.batchprocessing.dto.EngineCoreTemperatureDto;
import com.spark.example.batchprocessing.repository.EngineCoreTemperatureRepository;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EngineCoreTemperatureService implements Serializable {

  private final EngineCoreTemperatureRepository engineRepository;

  private final ModelMapper modelMapper;

  @Autowired
  public EngineCoreTemperatureService(EngineCoreTemperatureRepository engineRepository, ModelMapper modelMapper) {
    this.engineRepository = engineRepository;
    this.modelMapper = modelMapper;
  }

  public List<EngineCoreTemperature> addAll(List<EngineCoreTemperatureDto> temperatures) {
    return engineRepository.saveAll(temperatures.stream()
        .map(this::getDomainObject)
        .collect(Collectors.toList()));
  }

  private EngineCoreTemperature getDomainObject(EngineCoreTemperatureDto dto) {
    EngineCoreTemperature engineData = modelMapper.map(dto, EngineCoreTemperature.class);
    engineData.setId(UUIDs.timeBased());
    return engineData;
  }


}
