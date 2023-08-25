package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Room {
    private int length;
    private int width;
    private Set<Wall> verticallyWalls = new HashSet<>();
    private Set<Wall> horizontallyWalls = new HashSet<>();

    public Room(int length, int width){
        this.length = length;
        this.width = width;
    }

    public void addWall(Wall wall){
        if(wallIsInsideRoom(wall)){
            if(wall.getFrom().getX() == wall.getTo().getX()){
                verticallyWalls.add(wall);
            }else
                if(wall.getFrom().getY() == wall.getTo().getY()){
                    horizontallyWalls.add(wall);
                }
        }
    }

    private boolean wallIsInsideRoom(Wall wall){
        return wall.getTo().getX() >= 0 && wall.getFrom().getX() >= 0 && wall.getTo().getY() >= 0 && wall.getFrom().getY() >= 0 &&
                wall.getTo().getX() <= length && wall.getFrom().getX() <= length && wall.getTo().getY() <= width && wall.getFrom().getY() <= width;
    }
}
