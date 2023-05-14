package thkoeln.st.st2praktikum.exercise.coordinate;

import thkoeln.st.st2praktikum.exercise.UUidable;

public class Coordinate implements Measureable {
 private final Integer XAxes;
 private final Integer YAxes;

 public Coordinate(Integer XAxes, Integer YAxes){
     this.XAxes = XAxes;
     this.YAxes = YAxes;
 }

    @Override
    public String toString() {
        return "("+this.XAxes+","+this.YAxes+")";
    }

    @Override
    public Coordinate getCoordinate() {
        return this;
    }

    @Override
    public Integer getXAxes() {
        return this.XAxes;
    }

    @Override
    public Integer getYAxes() {
        return this.YAxes;
    }
}
