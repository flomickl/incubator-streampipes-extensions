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

package org.apache.streampipes.processors.geo.jvm.jts.processor.trajectory;

import org.apache.streampipes.logging.api.Logger;
import org.apache.streampipes.model.runtime.Event;
import org.apache.streampipes.processors.geo.jvm.jts.helper.SpGeometryBuilder;
import org.apache.streampipes.processors.geo.jvm.jts.helper.SpTrajectoryBuilder;
import org.apache.streampipes.wrapper.context.EventProcessorRuntimeContext;
import org.apache.streampipes.wrapper.routing.SpOutputCollector;
import org.apache.streampipes.wrapper.runtime.EventProcessor;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;


public class CreateTrajectoryFromPoints implements EventProcessor<CreateTrajectoryFromPointsParameter> {

    private static Logger LOG;
    private CreateTrajectoryFromPointsParameter params;
    SpTrajectoryBuilder trajectory;



    @Override
    public void onInvocation(CreateTrajectoryFromPointsParameter params, SpOutputCollector spOutputCollector, EventProcessorRuntimeContext runtimeContext) {

        LOG = params.getGraph().getLogger(CreateTrajectoryFromPointsParameter.class);
        this.params = params;

        trajectory = new SpTrajectoryBuilder(params.getSubpoints(), params.getDescription());
    }

    @Override
    public void onEvent(Event in, SpOutputCollector out) {

        String wkt = in.getFieldBySelector(params.getWkt()).getAsPrimitive().getAsString();
        Integer epsg = in.getFieldBySelector(params.getEpsg()).getAsPrimitive().getAsInt();
        Integer m = in.getFieldBySelector(params.getM()).getAsPrimitive().getAsInt();

        Point eventGeom =  (Point) SpGeometryBuilder.createSPGeom(wkt,epsg);

        //creates single trajectory
        trajectory.addPointToTrajectory(eventGeom, m);
        LineString geom = trajectory.returnAsLineString(eventGeom.getFactory());

        in.addField(CreateTrajectoryFromPointsController.WKT, geom.toString());
        in.addField(CreateTrajectoryFromPointsController.DESCRIPTION, trajectory.getDescription());
        out.collect(in);

    }

    @Override
    public void onDetach() {

    }
}

