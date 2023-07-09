package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.UUID;

public class Space extends  AbstractEntity{

    @Getter
    private int height;
    @Getter
    private int width;
    @Getter
    private ArrayList<Wall> walls;

    public Space (int height, int width){
        this.height = height;
        this.width = width;
        walls = new ArrayList<Wall>();
    }

    public Boolean checkCoordinateWithinSpace(Coordinate coordinate){
        return coordinate.getX() <= width && coordinate.getY() <= height;

    }





}
