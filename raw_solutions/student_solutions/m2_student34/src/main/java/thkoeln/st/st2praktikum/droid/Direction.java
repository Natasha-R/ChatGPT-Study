package thkoeln.st.st2praktikum.droid;

import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@AllArgsConstructor
public enum Direction {
    NORTH("no"),
    EAST("ea"),
    SOUTH("so"),
    WEST("we");

    private String abbreviation;

    public static Direction of(String direction) {
        switch(direction) {
            case "no": return NORTH;
            case "ea": return EAST;
            case "so": return SOUTH;
            case "we": return WEST;
            default: throw new IllegalArgumentException("Direction unknown: " + direction);
        }
    }

    public static Stream<String> allAbbreviations() {
        return Arrays.stream(Direction.values()).map(Direction::getAbbreviation);
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
