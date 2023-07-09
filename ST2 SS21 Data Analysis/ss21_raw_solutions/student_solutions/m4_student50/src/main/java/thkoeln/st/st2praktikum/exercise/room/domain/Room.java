package thkoeln.st.st2praktikum.exercise.room.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import thkoeln.st.st2praktikum.exercise.AbstractEntity;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.transportsystem.domain.Connection;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Room extends AbstractEntity {
    private int width;
    private int height;

    @ElementCollection(targetClass = Coordinate.class,fetch = FetchType.EAGER)
    public List<Wall> walls = new ArrayList<>();

    @OneToMany
    public List<TidyUpRobot> tidyUpRobots = new ArrayList<>();

    @OneToMany
    protected List<Connection> connections = new ArrayList<>();

    public Room(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public void addWall(Wall wall){
        if (wall.getStart().getX()<=width && wall.getEnd().getX() <= width && wall.getStart().getY()<=height && wall.getEnd().getY() <= height) {
            walls.add(wall);
        }else throw new RuntimeException();
    }
    public void addTidyUpRobot(TidyUpRobot tidyUpRobot){
        tidyUpRobots.add(tidyUpRobot);
    }

    public Connection fetchConnectionByDestinationRoomId(UUID destinationRoom) {
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getDestinationRoom().equals(destinationRoom)) {
                return connections.get(i);
            }
        }
        throw new RuntimeException("There isn't a Connection with this ID ["+ destinationRoom + "]");
    }
}
