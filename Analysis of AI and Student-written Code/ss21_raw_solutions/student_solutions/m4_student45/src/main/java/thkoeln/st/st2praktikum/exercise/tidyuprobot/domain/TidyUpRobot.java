package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.domainprimitives.OrderType;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.transporttechnology.domain.Connection;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TidyUpRobot {
    @Id
    @Generated
    private UUID id;
    private String name;
    private UUID currentRoomId;

    @Embedded
    private Coordinate position;

    @ManyToMany
    private Map<UUID, Room> rooms = new HashMap<>();

    @ElementCollection(targetClass = Order.class, fetch = FetchType.EAGER)
    List<Order> orders;

    public TidyUpRobot(String name, Map<UUID, Room> rooms) {
        this.name = name;
        id = UUID.randomUUID();
        this.rooms = rooms;
        orders = new ArrayList<>();
    }

    public TidyUpRobot(String name, Map<UUID, Room> rooms, List<Order> orders) {
        this.name = name;
        id = UUID.randomUUID();
        this.rooms = rooms;
        this.orders = orders;
    }

    public Boolean execute(Order order) {
        UUID destinationRoomId;
        Integer numberOfSteps;
        OrderType orderType = order.getOrderType();
        Room currentRoom;

        orders.add(order);

        switch (orderType) {
            case NORTH:
                currentRoom = rooms.get(currentRoomId);
                numberOfSteps = order.getNumberOfSteps();
                moveNorth(numberOfSteps, currentRoom);
                return true;

            case EAST:
                currentRoom = rooms.get(currentRoomId);
                numberOfSteps = order.getNumberOfSteps();
                moveEast(numberOfSteps, currentRoom);
                return true;

            case WEST:
                currentRoom = rooms.get(currentRoomId);
                numberOfSteps = order.getNumberOfSteps();
                moveWest(numberOfSteps, currentRoom);
                return true;

            case SOUTH:
                currentRoom = rooms.get(currentRoomId);
                numberOfSteps = order.getNumberOfSteps();
                moveSouth(numberOfSteps, currentRoom);
                return true;

            case TRANSPORT:
                destinationRoomId = order.getGridId();
                return transport(destinationRoomId);

            case ENTER:
                destinationRoomId = order.getGridId();
                return enter(destinationRoomId);

            default:
                return false;
        }
    }

    private void moveNorth(int steps, Room currentRoom) {
        currentRoom.removeTidyUpRobotId(position);

        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.getX(), position.getY() + possibleSteps, "no"))
            possibleSteps++;

        int x = position.getX();
        int y = position.getY() + possibleSteps;
        position = new Coordinate(x, y);

        currentRoom.addTidyUpRobotId(position, id);
    }

    private void moveEast(int steps, Room currentRoom) {
        currentRoom.removeTidyUpRobotId(position);

        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.getX() + possibleSteps, position.getY(), "ea"))
            possibleSteps++;

        int x = position.getX() + possibleSteps;
        int y = position.getY();
        position = new Coordinate(x, y);

        currentRoom.addTidyUpRobotId(position, id);
    }

    private void moveSouth(int steps, Room currentRoom) {
        currentRoom.removeTidyUpRobotId(position);

        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.getX(), position.getY() - possibleSteps, "so"))
            possibleSteps++;

        int x = position.getX();
        int y = position.getY() - possibleSteps;
        position = new Coordinate(x, y);

        currentRoom.addTidyUpRobotId(position, id);
    }

    private void moveWest(int steps, Room currentRoom) {
        currentRoom.removeTidyUpRobotId(position);

        int possibleSteps = 0;
        while (possibleSteps < steps && currentRoom.hasNoWall(position.getX() - possibleSteps, position.getY(), "we"))
            possibleSteps++;

        int x = position.getX() - possibleSteps;
        int y = position.getY();
        position = new Coordinate(x, y);

        currentRoom.addTidyUpRobotId(position, id);
    }

    private Boolean transport(UUID id) {
        Connection connection = getRoomSquareConnection();
        if (connection == null)
            return false;

        UUID firstId = connection.getSourceId();
        UUID secondId = connection.getDestinationId();
        Coordinate firstCoordinate = connection.getSourceCoordinate();
        Coordinate secondCoordinate = connection.getDestinationCoordinate();

        if (id.compareTo(secondId) == 0) {
            return transport(secondId, secondCoordinate);
        }
        else if (id.compareTo(firstId) == 0) {
            return transport(firstId, firstCoordinate);
        }
        else
            return false;
    }

    private Boolean transport(UUID destinationRoomId, Coordinate destinationCoordinate){
        Room currentRoom = rooms.get(currentRoomId);
        Room destinationRoom = rooms.get(destinationRoomId);

        if (!destinationRoom.isSquareOccupied(destinationCoordinate)) {
            currentRoom.removeTidyUpRobotId(position);
            position = destinationCoordinate;
            currentRoomId = destinationRoomId;
            destinationRoom.addTidyUpRobotId(id, destinationCoordinate);
            return true;
        }
        else
            return false;
    }


    private Boolean enter(UUID destinationRoomId) {
        Room currentRoom = rooms.get(destinationRoomId);
        Coordinate position = new Coordinate(0, 0);

        if(currentRoomId == null && !isRoomSquareOccupied(destinationRoomId, position)){
            this.position = position;
            currentRoomId = destinationRoomId;
            currentRoom.addTidyUpRobotId(position, id);
            return true;
        }
        else
            return false;
    }

    private Boolean isRoomSquareOccupied(UUID destinationRoomId, Coordinate coordinate){
        Room currentRoom = rooms.get(destinationRoomId);
        return currentRoom.isSquareOccupied(coordinate);
    }

    private Connection getRoomSquareConnection(){
        Room currentRoom = rooms.get(currentRoomId);
        return currentRoom.getSquareConnection(position);
    }

    public UUID getRoomId() {
        return currentRoomId;
    }

    public Coordinate getCoordinate() {
        return position;
    }

    public void clearOrderHistory(){
        orders.clear();
    }
}
