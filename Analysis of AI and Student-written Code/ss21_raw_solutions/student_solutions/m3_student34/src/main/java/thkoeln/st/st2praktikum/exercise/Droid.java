package thkoeln.st.st2praktikum.exercise;

import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public interface Droid extends IObstacle {
    boolean move(Command command);

    Position getPosition();

    String getName();

    UUID getSpaceShipDeckId();

    Coordinate getCoordinate();
}
