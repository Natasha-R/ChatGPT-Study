package thkoeln.st.st2praktikum.exercise.Robot;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.Room.Room;

import java.util.UUID;

public abstract class Robot {

    @Getter
    private UUID id;

    public Robot(){
        id = UUID.randomUUID();
    }

    public abstract void move(Order order);

    public abstract boolean enterRoom(Room room, Coordinate coordinate);

    public abstract boolean changeRoom(Room destinationRoom);
}
