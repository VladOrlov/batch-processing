package com.spark.example.batchprocessing.configuration;

import java.io.Serializable;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@SpringBootConfiguration
@ConfigurationProperties(prefix = "jobs.engine")
public class EngineJobProperties implements Serializable {

  private String path;

  private MappingOptions mappingOptions = new MappingOptions();

  @Getter
  @Setter
  public static class MappingOptions implements Serializable {

    private Map<String, String> dataSource;

    private Map<String, String> fields;

  }


}