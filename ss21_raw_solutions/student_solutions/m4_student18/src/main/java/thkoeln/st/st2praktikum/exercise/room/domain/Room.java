package thkoeln.st.st2praktikum.exercise.room.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.core.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;

import javax.persistence.*;
import java.util.*;

@Entity
public class Room extends AbstractEntity {

    @Getter
    private int height;

    @Getter
    private int width;

    @Getter
    @ElementCollection(targetClass = Obstacle.class, fetch = FetchType.EAGER)
    private final List<Obstacle> roomObstacles = new ArrayList<>();

    @Getter
    @OneToMany (cascade = CascadeType.REMOVE)
    private final Map<UUID, Connection> connections = new HashMap<>();

    public Room(int height, int width){
        this.height = height;
        this.width = width;

        Obstacle boundaryNorth = new Obstacle(0,height,width,height);
        Obstacle boundaryWest = new Obstacle(0,0,0,height);
        Obstacle boundaryEast = new Obstacle(width,0,width,height);
        Obstacle boundarySouth = new Obstacle(0,0,width,0);

        this.roomObstacles.add(boundaryNorth);
        this.roomObstacles.add(boundaryWest);
        this.roomObstacles.add(boundaryEast);
        this.roomObstacles.add(boundarySouth);
    }

    protected Room(){}

}


