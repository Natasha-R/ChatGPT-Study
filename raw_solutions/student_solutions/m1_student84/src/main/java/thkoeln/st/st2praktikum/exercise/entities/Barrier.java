package thkoeln.st.st2praktikum.exercise.entities;

import lombok.Getter;

public class Barrier {
    @Getter
    Coordinate start;
    @Getter
    Coordinate end;

    public Barrier(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    public static Barrier fromString(String barrierString) {
        String[] coordinateStrings = barrierString.split("-");

        return new Barrier(Coordinate.fromString(coordinateStrings[0]), Coordinate.fromString(coordinateStrings[1]));
    }
}
