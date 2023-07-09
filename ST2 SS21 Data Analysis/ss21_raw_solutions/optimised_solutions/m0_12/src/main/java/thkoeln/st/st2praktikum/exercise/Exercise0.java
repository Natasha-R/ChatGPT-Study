package thkoeln.st.st2praktikum.exercise;

import java.util.*;

public class Exercise0 implements Walkable {

    private Position robotPosition;

    public Exercise0() {
        robotPosition = new Position(3, 0);
    }

    private class Position {
        int x;
        int y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + this.x + "," + this.y + ")";
        }
    }

    private int getWidth() {
        return 12;
    }

    private int getHeight() {
        return 8;
    }

    private List<Set<Integer>> getHorizontalWalls() {
        return Arrays.asList(
            new HashSet<>(Arrays.asList(3, 4, 5, 6, 7)),
            new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8))
        );
    }

    private List<Set<Integer>> getVerticalWalls() {
        return Arrays.asList(
            new HashSet<>(Arrays.asList(0, 1, 2, 3)),
            new HashSet<>(Arrays.asList(0, 1, 2))
        );
    }

    @Override
    public String walkTo(String walkCommandString) {
        if (walkCommandString == null || walkCommandString.isEmpty()) {
            throw new IllegalArgumentException("Command cannot be null or empty");
        }


        if (walkCommandString.length() < 6 || walkCommandString.charAt(0) != '[' || walkCommandString.charAt(3) != ',' || walkCommandString.charAt(walkCommandString.length() - 1) != ']') {
            throw new IllegalArgumentException("Invalid command format");
        }

        String direction = walkCommandString.substring(1, 3);
        int steps;
        try {
            steps = Integer.parseInt(walkCommandString.substring(4, walkCommandString.length() - 1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number of steps");
        }


        if (steps < 0) {
            throw new IllegalArgumentException("Number of steps cannot be negative");
        }


        List<Set<Integer>> horizontalWalls = getHorizontalWalls();
        List<Set<Integer>> verticalWalls = getVerticalWalls();

        switch (direction) {
            case "no":
                for (int i = 0; i < steps && robotPosition.y < getHeight() - 1; i++) {
                    if (!horizontalWalls.contains(robotPosition.y + 1) || !horizontalWalls.get(robotPosition.y + 1).contains(robotPosition.x)) {
                        robotPosition.y++;
                    } else break;
                }
                break;
            case "so":
                for (int i = 0; i < steps && robotPosition.y > 0; i++) {
                    if (!horizontalWalls.contains(robotPosition.y) || !horizontalWalls.get(robotPosition.y).contains(robotPosition.x)) {
                        robotPosition.y--;
                    } else break;
                }
                break;
            case "ea":
                for (int i = 0; i < steps && robotPosition.x < getWidth() - 1; i++) {
                    if (!verticalWalls.contains(robotPosition.x + 1) || !verticalWalls.get(robotPosition.x + 1).contains(robotPosition.y)) {
                        robotPosition.x++;
                    } else break;
                }
                break;
            case "we":
                for (int i = 0; i < steps && robotPosition.x > 0; i++) {
                    if (!verticalWalls.contains(robotPosition.x) || !verticalWalls.get(robotPosition.x).contains(robotPosition.y)) {
                        robotPosition.x--;
                    } else break;
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }


        return robotPosition.toString();
    }
}

