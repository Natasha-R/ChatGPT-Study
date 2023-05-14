package thkoeln.st.st2praktikum.exercise;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.services.ObstacleService;
import thkoeln.st.st2praktikum.exercise.types.GeometricPosition;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Getter
@Entity
public class Obstacle {

    @Id
    private final UUID obstacleId = UUID.randomUUID();

    @Embedded
    private Coordinate start;

    @Embedded
    private Coordinate end;

    private GeometricPosition geometricPosition = null;


    public Obstacle(Coordinate pos1, Coordinate pos2) {
        this.checkCoordinateOrder(pos1, pos2);
    }

    /**
     * @param obstacleString the barrier in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) throws IllegalArgumentException {

        String[] obstacleItem = obstacleString.split("-");

        final Coordinate tempStart = new Coordinate(obstacleItem[0]);
        final Coordinate tempEnd = new Coordinate(obstacleItem[1]);
        this.checkCoordinateOrder(tempStart, tempEnd);
    }

    protected Obstacle() {
    }

    private void checkCoordinateOrder(Coordinate start, Coordinate end) {
        if (start.getX().equals(end.getX())) {
            this.geometricPosition = GeometricPosition.VERTICAL;
            if (ObstacleService.checkObstacleCoordinatesInOrder(start.getY(), end.getY())) {
                this.start = start;
                this.end = end;
            } else {
                this.end = start;
                this.start = end;
            }
        }
        if (start.getY().equals(end.getY())) {
            this.geometricPosition = GeometricPosition.HORIZONTAL;
            if (ObstacleService.checkObstacleCoordinatesInOrder(start.getX(), end.getX())) {
                this.start = start;
                this.end = end;
            } else {
                this.end = start;
                this.start = end;
            }
        }
        if (this.geometricPosition == null) {
            throw new IllegalStateException("GeometricPosition is null");
        }
    }
}
