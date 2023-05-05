package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;


public class Room{
    private final int height;
    private final int width;

    @Getter
    private final ArrayList<Obstacle> roomObstacles = new ArrayList<>();
    @Getter
    private final HashMap<UUID, Connection> connections = new HashMap<>();

    public Room(int height, int width){
        this.height = height;
        this.width = width;

        Obstacle boundaryNorth = new Obstacle(0,height,width,height);
        Obstacle boundaryWest = new Obstacle(0,0,0,height);
        Obstacle boundaryEast = new Obstacle(width,0,width,height);
        Obstacle boundarySouth = new Obstacle(0,0,width,0);

        roomObstacles.add(boundaryNorth);
        roomObstacles.add(boundaryWest);
        roomObstacles.add(boundaryEast);
        roomObstacles.add(boundarySouth);
    }

}


