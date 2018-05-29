package com.spark.example.batchprocessing.configuration;

import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.MapUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Log4j2
@EnableScheduling
@EnableCassandraRepositories
@SpringBootConfiguration
public class ApplicationConfiguration {

  private final EngineJobProperties engineJobProperties;

  @Autowired
  public ApplicationConfiguration(EngineJobProperties engineJobProperties) {
    this.engineJobProperties = engineJobProperties;
  }

  @Bean(name = "EngineJobConfiguration")
  public SparkJobConfiguration sparkJobConfiguration() {
    SparkJobConfiguration sparkJobConfiguration = new SparkJobConfiguration();
    sparkJobConfiguration.setPathToFile(engineJobProperties.getPath());

    Map<String, String> dataSource = engineJobProperties.getMappingOptions().getDataSource();

    if (dataSource.size() != 1) {
      throw new IllegalArgumentException("Wrong engine core job data source configuration!");
    }

    dataSource.forEach((alias, name) -> {
      sparkJobConfiguration.setDataSourceAlias(alias);
      sparkJobConfiguration.setDataSourceName(name);
    });

    if (MapUtils.isEmpty(engineJobProperties.getMappingOptions().getFields())) {
      throw new IllegalArgumentException("Wrong engine core job fields configuration!");
    }
    sparkJobConfiguration.setFieldsMapping(engineJobProperties.getMappingOptions().getFields());

    return sparkJobConfiguration;
  }

  @Bean
  public ModelMapper modelMapper(){
    return new ModelMapper();
  }
}