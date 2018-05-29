package com.spark.example.batchprocessing.configuration;

import org.apache.spark.sql.SparkSession;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class SparkConfiguration {

  @Bean
  public SparkSession sparkSession(){
    return SparkSession.builder()
        .appName("sensor-data-processing")
        .master("local[*]")
        .getOrCreate();
  }
}
