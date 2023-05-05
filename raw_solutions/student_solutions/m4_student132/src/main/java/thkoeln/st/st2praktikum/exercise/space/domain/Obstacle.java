package thkoeln.st.st2praktikum.exercise.space.domain;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.GeometricPosition;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Id;


@Getter
@Embeddable
public class Obstacle {

    @Id
    private String obstacleId;

    @Embedded
    private Coordinate start;

    @Embedded
    private Coordinate end;

    private GeometricPosition geometricPosition = null;


    public Obstacle(Coordinate pos1, Coordinate pos2) {
        this.obstacleId = pos1.toString() + "-" + pos2.toString();
        this.checkPointOrder(pos1, pos2);
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) throws IllegalArgumentException {

        this.obstacleId = obstacleString;

        String[] obstacleItem = obstacleString.split("-");

        final Coordinate tempStart = new Coordinate(obstacleItem[0]);
        final Coordinate tempEnd = new Coordinate(obstacleItem[1]);
        this.checkPointOrder(tempStart, tempEnd);
    }

    protected Obstacle() {
    }

    public static Obstacle fromString(String obstacleString) throws IllegalArgumentException {
        return new Obstacle(obstacleString);
    }

    private void checkPointOrder(Coordinate start, Coordinate end) {
        if (start.getX().equals(end.getX())) {
            this.geometricPosition = GeometricPosition.VERTICAL;
            if (this.checkObstaclePointsInOrder(start.getY(), end.getY())) {
                this.start = start;
                this.end = end;
            } else {
                this.end = start;
                this.start = end;
            }
        }
        if (start.getY().equals(end.getY())) {
            this.geometricPosition = GeometricPosition.HORIZONTAL;
            if (this.checkObstaclePointsInOrder(start.getX(), end.getX())) {
                this.start = start;
                this.end = end;
            } else {
                this.end = start;
                this.start = end;
            }
        }
        if (this.geometricPosition == null) {
            throw new IllegalStateException("GeometricPositionDesignation is null");
        }
    }

    public Boolean checkObstaclePointsInOrder(Integer start, Integer end) {
        return start < end;
    }
}
