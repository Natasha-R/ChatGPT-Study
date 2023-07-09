package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Exercise0 implements Walkable {
    private int width = 12;
    private int height = 9;
    private Point position = new Point(0, 2);
    private static final String NORTH = "no";
    private static final String EAST = "ea";
    private static final String SOUTH = "so";
    private static final String WEST = "we";

    @Override
    public String walk(String walkCommandString) {
        String direction = walkCommandString.substring(1, 3);
        int steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1));

        switch (direction) {
            case NORTH:
                position.y = Math.min(position.y + steps, height - 1);
                if (position.x <= 3 && position.y >= 3 && position.y <= 4) {
                    position.y = 3;
                } else if (position.x >= 4 && position.x <= 7 && position.y >= 5) {
                    position.y = 5;
                }
                break;
            case EAST:
                position.x = Math.min(position.x + steps, width - 1);
                if (position.y <= 3 && position.x >= 3 && position.x <= 5) {
                    position.x = 3;
                } else if (position.y >= 0 && position.y <= 4 && position.x >= 5 && position.x <= 6) {
                    position.x = 5;
                } else if (position.y >= 5 && position.y <= 9 && position.x >= 7) {
                    position.x = 7;
                }
                break;
            case SOUTH:
                position.y = Math.max(position.y - steps, 0);
                if (position.x <= 3 && position.y <= 2) {
                    position.y = 2;
                } else if (position.x >= 4 && position.x <= 7 && position.y <= 4) {
                    position.y = 4;
                }
                break;
            case WEST:
                position.x = Math.max(position.x - steps, 0);
                if (position.y >= 0 && position.y <= 3 && position.x <= 2) {
                    position.x = 2;
                } else if (position.y >= 0 && position.y <= 4 && position.x <= 4) {
                    position.x = 4;
                } else if (position.y >= 5 && position.y <= 9 && position.x <= 6) {
                    position.x = 6;
                }
                break;
        }

        return String.format("(%d,%d)", position.x, position.y);
    }
}
