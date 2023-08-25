package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    protected List<Wall> walls = new ArrayList<>();

    @OneToMany
    protected List<TidyUpRobot> tidyUpRobots = new ArrayList<>();

    @OneToMany
    protected List<Connection> connections = new ArrayList<>();

    public Room(int width, int height){
        this.width = width;
        this.height = height;
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }
    public void addWall(Wall barrier){
        if (barrier.getStart().getX()<=width && barrier.getEnd().getX() <= width && barrier.getStart().getY()<=height && barrier.getEnd().getY() <= height) {
            walls.add(barrier);
        }else throw new RuntimeException();
    }
    public void addTidyUpRobot(TidyUpRobot tidyUpRobot){
        tidyUpRobots.add(tidyUpRobot);
    }

    protected Connection fetchConnectionByDestinationRoomId(UUID destinationRoom) {
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getDestinationRoom().equals(destinationRoom)) {
                return connections.get(i);
            }
        }
        throw new RuntimeException("There isn't a Connection with this ID ["+ destinationRoom + "]");
    }
}