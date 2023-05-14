package thkoeln.st.st2praktikum.exercise.world2.coordinate;
public class Coordinate implements CoordinateController {

    private final Integer x_axis;
    private final Integer y_axis;

    public Coordinate(Integer x_axis, Integer y_axis) {
        this.x_axis = x_axis;
        this.y_axis = y_axis;
    }

    @Override
    public String toString() {
        return "("+this.x_axis+","+this.y_axis+")";
    }

    @Override
    public Coordinate getCoordinates() {
        return this;
    }

    @Override
    public Integer getXAxis() {
        return this.x_axis;
    }

    @Override
    public Integer getYAxis() {
        return this.y_axis;
    }
}
