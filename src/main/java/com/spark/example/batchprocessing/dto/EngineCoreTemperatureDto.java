package com.spark.example.batchprocessing.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EngineCoreTemperatureDto implements Serializable {

  private String engineId;

  private String sensorZone;

  private BigDecimal avgTemperature;

  private BigDecimal maxTemperature;

  private BigDecimal minTemperature;

}
