package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.UUidable;
import thkoeln.st.st2praktikum.exercise.droid.Direction;

import java.util.UUID;

public class Command implements  CommandService {
    private final Integer power;
    private final UUID spaceShipUUId;
    private final Direction dir;

    public Command(UUID spaceShipUUId, Direction dir) {
        this.power = null;
        this.spaceShipUUId = spaceShipUUId;
        this.dir = dir;
    }

    public Command(Integer power, Direction dir) {
        this.power = power;
        this.spaceShipUUId = null;
        this.dir = dir;
    }

    @Override
    public String toString() {
        return "Command{" +
                "power=" + power +
                ", spaceShipUUId=" + spaceShipUUId +
                ", dir=" + dir +
                '}';
    }

    public Integer getPower() {
        return power;
    }

    public UUID getSpaceShipUUId() {
        return spaceShipUUId;
    }

    public Direction getDir() {
        return dir;
    }
}
