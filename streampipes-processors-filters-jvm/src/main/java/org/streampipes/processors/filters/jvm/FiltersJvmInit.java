/*
 * Copyright 2017 FZI Forschungszentrum Informatik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.streampipes.processors.filters.jvm;

import org.streampipes.container.init.DeclarersSingleton;
import org.streampipes.container.standalone.init.StandaloneModelSubmitter;
import org.streampipes.dataformat.json.JsonDataFormatFactory;
import org.streampipes.processors.filters.jvm.config.FiltersJvmConfig;
import org.streampipes.processors.filters.jvm.processor.numericalfilter.NumericalFilterController;
import org.streampipes.examples.jvm.processor.projection.ProjectionController;
import org.streampipes.processors.filters.jvm.processor.textfilter.TextFilterController;
import org.streampipes.examples.jvm.sink.couchdb.CouchDbController;
import org.streampipes.examples.jvm.sink.dashboard.DashboardController;
import org.streampipes.examples.jvm.sink.kafka.KafkaController;
import org.streampipes.examples.jvm.sink.notification.NotificationController;
import org.streampipes.messaging.kafka.SpKafkaProtocolFactory;
import org.streampipes.processors.filters.jvm.processor.projection.ProjectionController;

public class FiltersJvmInit extends StandaloneModelSubmitter {

  public static void main(String[] args) {
    DeclarersSingleton
            .getInstance()
            .add(new ProjectionController())
            .add(new NumericalFilterController())
            .add(new TextFilterController())
            .add(new NotificationController())
            .add(new KafkaController())
            .add(new DashboardController())
            .add(new CouchDbController());

    DeclarersSingleton.getInstance().registerDataFormat(new JsonDataFormatFactory());
    DeclarersSingleton.getInstance().registerProtocol(new SpKafkaProtocolFactory());

    new FiltersJvmInit().init(FiltersJvmConfig.INSTANCE);
  }
}