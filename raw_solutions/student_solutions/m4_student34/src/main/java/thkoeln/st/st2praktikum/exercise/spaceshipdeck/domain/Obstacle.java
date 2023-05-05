package thkoeln.st.st2praktikum.exercise.spaceshipdeck.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Obstacle {

    private static final Pattern obstacleStringPattern =
            Pattern.compile("^\\(\\d,\\d\\)-\\(\\d,\\d\\)$");

    @Embedded
    private Coordinate start;
    @Embedded
    private Coordinate end;


    public Obstacle(Coordinate pos1, Coordinate pos2) {
        this.start = pos1;
        this.end = pos2;
        this.switchCoordinatesWhenNecessary();
        this.assertNotDiagonal();
    }

    /**
     * @param obstacleString the obstacle in form of a string e.g. (1,2)-(1,4)
     */
    public Obstacle(String obstacleString) {
        var matcher = Obstacle.obstacleStringPattern.matcher(obstacleString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(obstacleString + "is malformed");
        }
        var stringTokenizer = new StringTokenizer(obstacleString, "-");
        this.start = new Coordinate(stringTokenizer.nextToken());
        this.end = new Coordinate(stringTokenizer.nextToken());

        this.switchCoordinatesWhenNecessary();
        this.assertNotDiagonal();
    }

    public static Obstacle fromString(String obstacleString) {
        return new Obstacle(obstacleString);
    }

    public boolean isVertical() {
        return this.start.getX().equals(this.end.getX());
    }

    public boolean isHorizontal() {
        return this.start.getY().equals(this.end.getY());
    }

    public boolean cuts(TaskType taskType, Coordinate sourceCoordinate) {
        switch (taskType) {
            case NORTH:
                return this.cutsNorth(taskType, sourceCoordinate);
            case EAST:
                return this.cutsEast(taskType, sourceCoordinate);
            case SOUTH:
                return this.cutsSouth(taskType, sourceCoordinate);
            case WEST:
                return this.cutsWest(taskType, sourceCoordinate);
            default:
                return false;
        }
    }

    private void assertNotDiagonal() {
        if (!this.isVertical() && !this.isHorizontal()) {
            throw new IllegalArgumentException("Obstacle is not allowed to be diagonal");
        }
    }

    private void switchCoordinatesWhenNecessary() {
        var origin = new Coordinate(0, 0);
        if (this.start.distance(origin) > this.end.distance(origin)) {
            var tmp = this.start;
            this.start = this.end;
            this.end = tmp;
        }
    }

    private boolean cutsNorth(TaskType taskType, Coordinate sourceCoordinate) {
        var targetCoordinate = sourceCoordinate.move(taskType, 1);
        if (this.isVertical()) {
            return false;
        }
        if (sourceCoordinate.getX() < this.start.getX() || sourceCoordinate.getX() >= this.end.getX()) {
            return false;
        }
        return sourceCoordinate.getY() < this.start.getY() && targetCoordinate.getY() >= this.start.getY();
    }

    private boolean cutsEast(TaskType taskType, Coordinate sourceCoordinate) {
        var targetCoordinate = sourceCoordinate.move(taskType, 1);
        if (this.isHorizontal()) {
            return false;
        }
        if (sourceCoordinate.getY() >= this.end.getY() || sourceCoordinate.getY() < this.start.getY()) {
            return false;
        }
        return sourceCoordinate.getX() < this.start.getX() && targetCoordinate.getX() >= this.start.getX();
    }

    private boolean cutsSouth(TaskType taskType, Coordinate sourceCoordinate) {
        var targetCoordinate = sourceCoordinate.move(taskType, 1);
        if (this.isVertical()) {
            return false;
        }
        if (sourceCoordinate.getX() < start.getX() || sourceCoordinate.getX() >= end.getX()) {
            return false;
        }
        return sourceCoordinate.getY() >= start.getY() && targetCoordinate.getY() < start.getY();
    }

    private boolean cutsWest(TaskType taskType, Coordinate sourceCoordinate) {
        var targetCoordinate = sourceCoordinate.move(taskType, 1);
        if(this.isHorizontal()) {
            return false;
        }
        if (sourceCoordinate.getY() < start.getY() || sourceCoordinate.getY() >= end.getY()) {
            return false;
        }
        return sourceCoordinate.getX() >= start.getX() && targetCoordinate.getX() < start.getX();
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
