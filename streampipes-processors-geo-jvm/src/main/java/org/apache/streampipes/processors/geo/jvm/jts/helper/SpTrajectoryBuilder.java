package org.apache.streampipes.processors.geo.jvm.jts.helper;


import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

public class SpTrajectoryBuilder {

    private int numberSubPoints;
    private String description;

    private CoordinateList coordinateList;


    public SpTrajectoryBuilder(int numberSubPoints, String desription) {
        this.numberSubPoints = numberSubPoints;
        this.description = desription;
    }


    public boolean addPointToTrajectory(Point point) {
        // removes last point if max numbers 
        if (!coordinateList.isEmpty() && coordinateList.size() > numberSubPoints){
            coordinateList.remove(0);
        }
        coordinateList.add(point.getCoordinate());


    }



}
