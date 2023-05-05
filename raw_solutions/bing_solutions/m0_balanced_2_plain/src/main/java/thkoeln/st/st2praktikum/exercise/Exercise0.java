package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x = 3;
    private int y = 0;
    private int[][] walls = {{3, 0}, {3, 1}, {3, 2}, {3, 3}, {4, 3}, {5, 3}, {6, 3}, {7, 3}, {7, 2}, {7, 1}, {7, 0}, {1, 4}, {2, 4}, {3, 4}, {4, 4}, {5, 4}, {6, 4}, {7, 4}};
    private int width = 12;
    private int height = 8;

    @Override
    public String walkTo(String walkCommandString) {
        String[] walkCommand = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = walkCommand[0];
        int steps = Integer.parseInt(walkCommand[1]);

        switch (direction) {
            case "no":
                for (int i = y + steps; i <= height; i++) {
                    if (isWall(x, y + steps - i)) {
                        y += i - y - steps;
                        break;
                    } else if (i == height) {
                        y += steps;
                    }
                }
                break;
            case "ea":
                for (int i = x + steps; i <= width; i++) {
                    if (isWall(x + steps - i, y)) {
                        x += i - x - steps;
                        break;
                    } else if (i == width) {
                        x += steps;
                    }
                }
                break;
            case "so":
                for (int i = y - steps; i >= 0; i--) {
                    if (isWall(x, y - steps + i)) {
                        y -= y - steps + i;
                        break;
                    } else if (i == 0) {
                        y -= steps;
                    }
                }
                break;
            case "we":
                for (int i = x - steps; i >= 0; i--) {
                    if (isWall(x - steps + i, y)) {
                        x -= x - steps + i;
                        break;
                    } else if (i == 0) {
                        x -= steps;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid direction");
        }

        return "(" + x + "," + y + ")";
    }

    private boolean isWall(int x, int y) {
        for (int[] wall : walls) {
            if (wall[0] == x && wall[1] == y) {
                return true;
            }
        }

        return false;
    }
}