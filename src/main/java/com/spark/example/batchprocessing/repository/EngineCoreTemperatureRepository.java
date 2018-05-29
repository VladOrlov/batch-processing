package com.spark.example.batchprocessing.repository;

import com.spark.example.batchprocessing.domain.EngineCoreTemperature;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface EngineCoreTemperatureRepository extends CassandraRepository<EngineCoreTemperature, UUID> {

}
