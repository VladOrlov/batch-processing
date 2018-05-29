package com.spark.example.batchprocessing.job;

import static org.apache.spark.sql.functions.avg;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.explode;
import static org.apache.spark.sql.functions.max;
import static org.apache.spark.sql.functions.min;

import com.spark.example.batchprocessing.configuration.SparkJobConfiguration;
import com.spark.example.batchprocessing.dto.EngineCoreTemperatureDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class EngineCoreScheduledJob implements Serializable {

  private final SparkSession session;

  private final SparkJobConfiguration jobConfiguration;

  @Autowired
  public EngineCoreScheduledJob(SparkSession session,
      @Qualifier("EngineJobConfiguration") SparkJobConfiguration jobConfiguration) {
    this.session = session;
    this.jobConfiguration = jobConfiguration;
  }

  @Scheduled(cron = "${jobs.engine.schedule}")
  public void execute() {
    log.info("Executing engine data processing job");

    Dataset<Row> averageEngineSpeed = session.read().option("multiline", true)
        .json(jobConfiguration.getPathToFile())
        .select(col("id"), explode(col(jobConfiguration.getDataSourceName()))
            .as(jobConfiguration.getDataSourceAlias()))
        .withColumn("id", col("id"))
        .withColumn("sensor_zone", col("core_temp.zone"))
        .withColumn("sensor_value", col("core_temp.value"))
        .withColumn("sensor_ratio", col("core_temp.ratio"))
        .groupBy("id", "sensor_zone")
        .agg(avg("sensor_value"),
            max("sensor_value"),
            min("sensor_value"),
            avg("sensor_ratio"))
        .orderBy(col("avg(" + "sensor_value" + ")").desc());

    averageEngineSpeed.printSchema();
    averageEngineSpeed.show();

    List<EngineCoreTemperatureDto> result = averageEngineSpeed.toJavaRDD()
        .map(this::mapToObject)
        .collect();

    log.info(result);
  }

  private EngineCoreTemperatureDto mapToObject(Row row) {

    double ratio = row.getDouble(5);
    if (Double.compare(ratio, 0d) == 0) {
      ratio = 1d;
    }
    return EngineCoreTemperatureDto.builder()
        .engineId(row.getString(0))
        .sensorZone(row.getString(1))
        .avgTemperature(BigDecimal.valueOf(row.getDouble(2) / ratio))
        .maxTemperature(BigDecimal.valueOf(row.getLong(3) / ratio))
        .minTemperature(BigDecimal.valueOf(row.getLong(4) / ratio))
        .build();
  }
}