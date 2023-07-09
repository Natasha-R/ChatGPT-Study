package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Field implements FieldInterface{
    Coordinate wallstart, wallend;
    List<String> walls = new ArrayList<String>();
    UUID id = UUID.randomUUID();
    int height, width;

    public Field(int height, int width){
        this.height = height;
        this.width = width;
    }
    public UUID getId(){
        return id;
    }

    public Field getField(){
        return this;
    }

    public void addWall(String wallString){
        walls.add(wallString);
    }

    public void stringToCoordinate(String wall){
        String[] stringArray = wall.split("[\\W]");
        int[] intArray = new int[4];
        int act = 0;
        for (int i = 0; i < stringArray.length; i++){
            if(!stringArray[i].isEmpty()) {
                intArray[act] = Integer.parseInt(stringArray[i]);
                act++;
            }
        }

        wallstart = new Coordinate(intArray[0], intArray[1]);
        wallend = new Coordinate(intArray[2], intArray[3]);

    }
}
