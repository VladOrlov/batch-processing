package com.spark.example.batchprocessing.configuration;

import java.io.Serializable;
import java.util.Map;
import lombok.Data;

@Data
public class SparkJobConfiguration implements Serializable {

  private String pathToFile;

  private String dataSourceName;

  private String dataSourceAlias;

  private Map<String, String> fieldsMapping;

}
