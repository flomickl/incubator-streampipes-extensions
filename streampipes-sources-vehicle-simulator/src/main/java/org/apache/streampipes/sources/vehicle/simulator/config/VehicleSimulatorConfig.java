/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.streampipes.sources.vehicle.simulator.config;

import org.apache.streampipes.config.SpConfig;
import org.apache.streampipes.container.model.PeConfig;

public enum VehicleSimulatorConfig implements PeConfig {
  INSTANCE;


  private final static String service_id = "pe/org.apache.streampipes.sources.vehicle.simulator";
  private final static String service_name = "Sources Vehicle Simulator";
  private final static String service_container_name = "sources-vehicle-simulator";

  private SpConfig config;
  public static String serverUrl;

  VehicleSimulatorConfig() {
    config = SpConfig.getSpConfig(service_id);

    /*
      TUTORIAL:
      The second parameter is the default value for the configuration property.
      This value is set in Consul when the parameter does not exist.
      Important. Changes here are not effective if the configuration parameter is already set in consul. In
      such cases the value has to be changed in consul directly.
    */
    config.register(ConfigKeys.HOST, service_container_name, "Hostname for the examples-sources project");
    config.register(ConfigKeys.PORT, 8090, "Port of the sources project");
    config.register(ConfigKeys.KAFKA_HOST, "kafka", "Host for kafka of the pe demonstrator project");
    config.register(ConfigKeys.KAFKA_PORT, 9092, "Port for kafka of the pe demonstrator project");
    config.register(ConfigKeys.SERVICE_NAME, service_name, "StreamPipes example sources");
  }

  static {
    serverUrl = VehicleSimulatorConfig.INSTANCE.getHost() + ":" + VehicleSimulatorConfig.INSTANCE.getPort();
  }

  @Override
  public String getHost() {
    return config.getString(ConfigKeys.HOST);
  }

  @Override
  public int getPort() {
    return config.getInteger(ConfigKeys.PORT);
  }

  public String getKafkaHost() {
    return config.getString(ConfigKeys.KAFKA_HOST);
  }

  public int getKafkaPort() {
    return config.getInteger(ConfigKeys.KAFKA_PORT);
  }

  @Override
  public String getId() {
    return service_id;
  }

  @Override
  public String getName() {
    return config.getString(ConfigKeys.SERVICE_NAME);
  }

}
