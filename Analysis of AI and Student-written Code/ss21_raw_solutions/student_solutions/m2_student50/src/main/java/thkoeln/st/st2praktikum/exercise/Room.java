package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
public class Room extends AbstractEntity {

    private int width;
    private int height;
    protected ArrayList<Wall> walls = new ArrayList<>();
    protected ArrayList<TidyUpRobot> tidyUpRobots = new ArrayList<>();
    protected ArrayList<Connection> connections = new ArrayList<>();

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

    protected Connection fetchConnectionByDestinationRoomId(UUID destinationSpace) {
        for (int i = 0; i < connections.size(); i++) {
            if (connections.get(i).getDestinationRoom().equals(destinationSpace)) {
                return connections.get(i);
            }
        }
        throw new RuntimeException("There isn't a Connection with this ID ["+ destinationSpace + "]");
    }
}