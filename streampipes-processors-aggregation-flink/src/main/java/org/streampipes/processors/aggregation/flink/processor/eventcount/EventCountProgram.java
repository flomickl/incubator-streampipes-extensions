/*
 * Copyright 2018 FZI Forschungszentrum Informatik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * you may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.streampipes.processors.aggregation.flink.processor.eventcount;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.streampipes.model.runtime.Event;
import org.streampipes.processors.aggregation.flink.AbstractAggregationProgram;
import org.streampipes.processors.aggregation.flink.processor.count.TimeWindowConverter;

public class EventCountProgram extends AbstractAggregationProgram<EventCountParameters> {

  public EventCountProgram(EventCountParameters params, boolean debug) {
    super(params, debug);
  }

  @Override
  protected DataStream<Event> getApplicationLogic(DataStream<Event>... dataStreams) {
    return dataStreams[0]
            .map(new EventCountMapper())
            .timeWindowAll(new TimeWindowConverter().makeTimeWindow(bindingParams.getTimeWindowSize(), bindingParams.getTimeWindowScale()))
            .sum(0)
            .map(new EventCountOutputMapper());
  }
}