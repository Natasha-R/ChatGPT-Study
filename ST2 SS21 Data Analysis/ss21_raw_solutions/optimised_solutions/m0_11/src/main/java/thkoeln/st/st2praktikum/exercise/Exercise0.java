package thkoeln.st.st2praktikum.exercise;

import java.awt.Point;

public class Exercise0 implements Walkable {

    private static final int ROOM_WIDTH;
    private static final int ROOM_HEIGHT;

    private Point location;
    private boolean[][] walls;

    static {
        ROOM_WIDTH = 12;
        ROOM_HEIGHT = 8;
    }

    public Exercise0() {
        location = new Point(3, 0);
        initializeRoom();
        setupWalls();
    }

    private void initializeRoom() {
        walls = new boolean[ROOM_WIDTH][ROOM_HEIGHT];

        for (int i = 0; i < ROOM_HEIGHT; i++) {
            for (int j = 0; j < ROOM_WIDTH; j++) {
                walls[j][i] = false;
            }
        }
    }

    private void setupWalls() {
        for (int i = 0; i < ROOM_HEIGHT; i++) {
            for (int j = 0; j < ROOM_WIDTH; j++) {
                if (j == 3 && i < 4) {
                    walls[j][i] = true;
                }

                if (j == 7 && i < 3) {
                    walls[j][i] = true;
                }

                if (j > 3 && j < 8 && i == 4) {
                    walls[j][i] = true;
                }

                if (i == 3 && j > 3 && j < 8) {
                    walls[j][i] = true;
                }
            }
        }
    }

    public void addWall(int x, int y) {
        if (x >= 0 && x < ROOM_WIDTH && y >= 0 && y < ROOM_HEIGHT) {
            walls[x][y] = true;
        }
    }

    public void removeWall(int x, int y) {
        if (x >= 0 && x < ROOM_WIDTH && y >= 0 && y < ROOM_HEIGHT) {
            walls[x][y] = false;
        }
    }

    public boolean isWall(int x, int y) {
        if (x >= 0 && x < ROOM_WIDTH && y >= 0 && y < ROOM_HEIGHT) {
            return walls[x][y];
        }
        return false;
    }

    public String walkTo(String walkCommandString) {
        String[] command = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = command[0];
        int steps = Integer.parseInt(command[1]);

        for (int i = 0; i < steps; i++) {
            switch (direction) {
                case "no":
                    moveNorth();
                    break;
                case "ea":
                    moveEast();
                    break;
                case "so":
                    moveSouth();
                    break;
                case "we":
                    moveWest();
                    break;
            }
        }

        return "(" + location.x + "," + location.y + ")";
    }

    private void moveNorth() {
        System.out.println("Attempting to move North...");

        int potentialNewY = location.y + 1;
        if (potentialNewY < ROOM_HEIGHT) {
            System.out.println("New location is within room height.");

            if (!walls[location.x][potentialNewY]) {
                System.out.println("No wall detected at new location. Proceeding with the move.");
                location.y = potentialNewY;
            } else {
                System.out.println("Wall detected at new location. Cannot move North.");
            }
        } else {
            System.out.println("New location exceeds room height. Cannot move North.");
        }
    }

    private void moveEast() {
        System.out.println("Attempting to move East...");

        int potentialNewX = location.x + 1;
        if (potentialNewX < ROOM_WIDTH) {
            System.out.println("New location is within room width.");

            if (!walls[potentialNewX][location.y]) {
                System.out.println("No wall detected at new location. Proceeding with the move.");
                location.x = potentialNewX;
            } else {
                System.out.println("Wall detected at new location. Cannot move East.");
            }
        } else {
            System.out.println("New location exceeds room width. Cannot move East.");
        }
    }

    private void moveSouth() {
        System.out.println("Attempting to move South...");

        int potentialNewY = location.y - 1;
        if (potentialNewY >= 0) {
            System.out.println("New location is within room height.");

            if (!walls[location.x][potentialNewY]) {
                System.out.println("No wall detected at new location. Proceeding with the move.");
                location.y = potentialNewY;
            } else {
                System.out.println("Wall detected at new location. Cannot move South.");
            }
        } else {
            System.out.println("New location is less than 0. Cannot move South.");
        }
    }

    private void moveWest() {
        System.out.println("Attempting to move West...");

        int potentialNewX = location.x - 1;
        if (potentialNewX >= 0) {
            System.out.println("New location is within room width.");

            if (!walls[potentialNewX][location.y]) {
                System.out.println("No wall detected at new location. Proceeding with the move.");
                location.x = potentialNewX;
            } else {
                System.out.println("Wall detected at new location. Cannot move West.");
            }
        } else {
            System.out.println("New location is less than 0. Cannot move West.");
        }
    }

}
