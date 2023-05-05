package thkoeln.st.st2praktikum.exercise.Move;

import lombok.Getter;
import thkoeln.st.st2praktikum.exercise.Coordinate;

public class Hitbox {

    @Getter
    private Coordinate basePosition;

    public Coordinate getNorthPosition() {
        return new Coordinate(basePosition.getX(), basePosition.getY() + 1);
    }

    public Coordinate getEastPosition() {
        return new Coordinate(basePosition.getX() + 1,basePosition.getY());
    }

    public Coordinate getSouthPosition() {
        if(basePosition.getY() == 0) {
            return basePosition;
        }
        return new Coordinate(basePosition.getX(), basePosition.getY() - 1);
    }

    public Coordinate getWestPosition() {
        if(basePosition.getX() == 0) {
            return basePosition;
        }
        return new Coordinate(basePosition.getX() - 1, basePosition.getY());
    }

    public Coordinate getNorthEastPosition() {
        return new Coordinate(basePosition.getX() + 1, basePosition.getY() + 1);
    }

    public Hitbox(Coordinate coordinate) {
        basePosition = coordinate;
    }
}
