package thkoeln.st.st2praktikum.exercise;

// A class that implements the Walkable interface
public class Exercise0 implements Walkable {

    // Constants for the size of the room
    public static final int ROOM_WIDTH = 12;
    public static final int ROOM_HEIGHT = 8;

    // Constants for the directions
    public static final String NORTH = "no";
    public static final String EAST = "ea";
    public static final String SOUTH = "so";
    public static final String WEST = "we";

    // A nested class that represents a wall
    private static class Wall {
        // The coordinates of the start and end points of the wall
        private int startX;
        private int startY;
        private int endX;
        private int endY;

        // A constructor that takes the coordinates as parameters
        public Wall(int startX, int startY, int endX, int endY) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }

        // A method that checks if a wall is horizontal or vertical
        public boolean isHorizontal() {
            return startY == endY;
        }

        // A method that checks if a wall is blocking a given coordinate in a given direction
        public boolean isBlocking(int x, int y, String direction) {
            // If the wall is horizontal
            if (isHorizontal()) {
                // Check if the coordinate is on the same row as the wall
                if (y == startY) {
                    // Check if the coordinate is strictly between the start and end points of the wall
                    if (x > startX && x < endX) {
                        // Check if the direction is north or south
                        if (direction.equals(NORTH) || direction.equals(SOUTH)) {
                            // The wall is blocking
                            return true;
                        }
                    }
                }
            } else { // If the wall is vertical
                // Check if the coordinate is on the same column as the wall
                if (x == startX) {
                    // Check if the coordinate is strictly between the start and end points of the wall
                    if (y > startY && y < endY) {
                        // Check if the direction is east or west
                        if (direction.equals(EAST) || direction.equals(WEST)) {
                            // The wall is blocking
                            return true;
                        }
                    }
                }
            }
            // The wall is not blocking
            return false;
        }
    }

    // An array of walls in the room
    private Wall[] walls;

    // The current position of the robot
    private int x;
    private int y;

    // A constructor that initializes the walls and the position of the robot
    public Exercise0() {
        walls = new Wall[4];
        walls[0] = new Wall(3,0,3,3);
        walls[1] = new Wall(4,3,7,3);
        walls[2] = new Wall(7,0,7,2);
        walls[3] = new Wall(1,4,8,4);
        x = 3;
        y = 0;
    }

    // A method that implements the walkTo method from the Walkable interface
    @Override
    public String walkTo(String walkCommandString) {
        try {
            // Parse the command string and extract the direction and steps
            String direction = walkCommandString.substring(1,3);
            int steps = Integer.parseInt(walkCommandString.substring(4,5));

            // Move the robot according to the direction and steps
            switch (direction) {
                case NORTH:
                    // Move the robot north until it reaches the room boundary or a wall
                    for (int i = 0; i < steps; i++) {
                        // Check if the next cell is within the room boundary
                        if (y + 1 < ROOM_HEIGHT) {
                            // Check if any wall is blocking the next cell
                            boolean blocked = false;
                            for (Wall wall : walls) {
                                if (wall.isBlocking(x, y + 1, direction)) {
                                    blocked = true;
                                    break;
                                }
                            }
                            // If not blocked, move to the next cell
                            if (!blocked) {
                                y++;
                            } else {
                                // If blocked, stop moving
                                break;
                            }
                        } else {
                            // If out of the room boundary, stop moving
                            break;
                        }
                    }
                    break;
                case EAST:
                    // Move the robot east until it reaches the room boundary or a wall
                    for (int i = 0; i < steps; i++) {
                        // Check if the next cell is within the room boundary
                        if (x + 1 < ROOM_WIDTH) {
                            // Check if any wall is blocking the next cell
                            boolean blocked = false;
                            for (Wall wall : walls) {
                                if (wall.isBlocking(x + 1, y, direction)) {
                                    blocked = true;
                                    break;
                                }
                            }
                            // If not blocked, move to the next cell
                            if (!blocked) {
                                x++;
                            } else {
                                // If blocked, stop moving
                                break;
                            }
                        } else {
                            // If out of the room boundary, stop moving
                            break;
                        }
                    }
                    break;
                case SOUTH:
                    // Move the robot south until it reaches the room boundary or a wall
                    for (int i = 0; i < steps; i++) {
                        // Check if the next cell is within the room boundary
                        if (y - 1 > 0) {
                            // Check if any wall is blocking the next cell
                            boolean blocked = false;
                            for (Wall wall : walls) {
                                if (wall.isBlocking(x, y - 1, direction)) {
                                    blocked = true;
                                    break;
                                }
                            }
                            // If not blocked, move to the next cell
                            if (!blocked) {
                                y--;
                            } else {
                                // If blocked, stop moving
                                break;
                            }
                        } else {
                            // If out of the room boundary, stop moving
                            break;
                        }
                    }
                    break;
                case WEST:
                    // Move the robot west until it reaches the room boundary or a wall
                    for (int i = 0; i < steps; i++) {
                        // Check if the next cell is within the room boundary
                        if (x - 1 > 0) {
                            // Check if any wall is blocking the next cell
                            boolean blocked = false;
                            for (Wall wall : walls) {
                                if (wall.isBlocking(x - 1, y, direction)) {
                                    blocked = true;
                                    break;
                                }
                            }
                            // If not blocked, move to the next cell
                            if (!blocked) {
                                x--;
                            } else {
                                // If blocked, stop moving
                                break;
                            }
                        } else {
                            // If out of the room boundary, stop moving
                            break;
                        }
                    }
                    break;
                default:
                    // If the direction is invalid, throw an exception
                    throw new IllegalArgumentException("Invalid direction: " + direction);
            }

            // Return the current position of the robot as a string
            return "(" + x + "," + y + ")";
        } catch (Exception e) {
            // If any error occurs, return the error message as a string
            return e.getMessage();
        }
    }
}