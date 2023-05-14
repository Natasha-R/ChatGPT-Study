package thkoeln.st.st2praktikum.exercise;

import javax.persistence.MappedSuperclass;
import java.util.Optional;

@MappedSuperclass
public interface Map extends Entity {
    Optional<Movement> maxMove(Movement movement);

    void addRectangleObstacle(RectangleObstacle obstacle);

    boolean isInBounds(Position position);

    boolean enter(Droid droid, Position targetPosition);
    boolean leave(Droid droid);
}
