package thkoeln.st.st2praktikum.parser;

import lombok.AllArgsConstructor;

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

    public String getAbbreviation() {
        return abbreviation;
    }
}
