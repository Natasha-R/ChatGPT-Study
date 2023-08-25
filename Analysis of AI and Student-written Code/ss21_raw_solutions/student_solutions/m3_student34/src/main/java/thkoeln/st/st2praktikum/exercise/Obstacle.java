package thkoeln.st.st2praktikum.exercise;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class Obstacle {

    private static final Pattern obstacleStringPattern =
            Pattern.compile("^\\(\\d,\\d\\)-\\(\\d,\\d\\)$");

    private Coordinate start;
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
        if(!matcher.matches()) {
            throw new IllegalArgumentException(obstacleString + "is malformed");
        }
        var stringTokenizer = new StringTokenizer(obstacleString, "-");
        this.start = new Coordinate(stringTokenizer.nextToken());
        this.end = new Coordinate(stringTokenizer.nextToken());

        this.switchCoordinatesWhenNecessary();
        this.assertNotDiagonal();
    }

    public boolean isVertical() {
        return this.start.getX().equals(this.end.getX());
    }

    public boolean isHorizontal() {
        return this.start.getY().equals(this.end.getY());
    }

    private void assertNotDiagonal() {
        if(!this.isVertical() && !this.isHorizontal()) {
            throw new IllegalArgumentException("Obstacle is not allowed to be diagonal");
        }
    }

    private void switchCoordinatesWhenNecessary() {
        var origin = new Coordinate(0, 0);
        if(this.start.distance(origin) > this.end.distance(origin)) {
            var tmp = this.start;
            this.start = this.end;
            this.end = tmp;
        }
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
