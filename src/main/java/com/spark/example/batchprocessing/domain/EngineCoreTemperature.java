package com.spark.example.batchprocessing.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Builder
@Table
public class EngineCoreTemperature implements Serializable {

  @PrimaryKeyColumn(name = "id", ordinal = 2,
      type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
  private UUID id;

  @PrimaryKeyColumn(name = "engine_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private String engineId;

  @PrimaryKeyColumn(name = "sensor_zone", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
  private String sensorZone;

  @Column
  private BigDecimal avgTemperature;

  @Column
  private BigDecimal maxTemperature;

  @Column
  private BigDecimal minTemperature;

}
