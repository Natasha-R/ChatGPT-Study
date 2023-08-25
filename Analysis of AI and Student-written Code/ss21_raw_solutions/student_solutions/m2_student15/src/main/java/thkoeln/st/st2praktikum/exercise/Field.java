package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Field {
    private List<Wall> walls = new ArrayList<Wall>();
    private UUID id = UUID.randomUUID();
    private int height, width;

    public Field(int height, int width){
        this.height = height;
        this.width = width;
    }
    public Field getField(){
        return this;
    }

    public void addWall(Wall wall){
        walls.add(wall);
    }

}

