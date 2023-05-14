package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {
    int height;
    int width;
    UUID id = UUID.randomUUID();

    List<Wall> walls = new ArrayList<Wall>();



    public Room(int givenHeight, int givenWidth){
        height = givenHeight;
        width = givenWidth;
    }

    public List<Wall> getWalls(){
        return walls;
    }public int getHeight(){
        return height;
    }public int getWidth(){
        return width;
    }public UUID getId(){
        return id;
    }

    public void addWall(String wallString){
        walls.add(new Wall(wallString));
    }

}
