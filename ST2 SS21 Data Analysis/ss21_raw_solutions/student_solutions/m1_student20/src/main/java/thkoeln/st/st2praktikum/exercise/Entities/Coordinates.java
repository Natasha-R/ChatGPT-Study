package thkoeln.st.st2praktikum.exercise.Entities;

public class Coordinates {
    public Coordinates(Integer x, Integer y){
        this.x_Coordinate = x;
        this.y_Coordinate = y;
    }
    private Integer x_Coordinate;
    public Integer getX_Coordinate(){
        return this.x_Coordinate;
    }
    private Integer y_Coordinate;
    public Integer getY_Coordinate(){
        return this.y_Coordinate;
    }
}
