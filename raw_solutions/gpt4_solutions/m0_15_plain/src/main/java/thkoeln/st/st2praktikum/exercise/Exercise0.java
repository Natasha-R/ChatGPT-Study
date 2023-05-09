package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    private int x;
    private int y;
    private final int fieldWidth = 12;
    private final int fieldHeight = 9;
    private final int[][] walls = {
            {3, 0, 3, 3},
            {5, 0, 5, 4},
            {4, 5, 7, 5},
            {7, 5, 7, 9}
    };

    public Exercise0() {
        x = 0;
        y = 2;
    }

    @Override
    public String walk(String walkCommandString) {
        String[] commandParts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = commandParts[0];
        int steps = Integer.parseInt(commandParts[1]);

        for (int i = 0; i < steps; i++) {
            int nextX = x;
            int nextY = y;

            switch (direction) {
                case "no":
                    nextY++;
                    break;
                case "ea":
                    nextX++;
                    break;
                case "so":
                    nextY--;
                    break;
                case "we":
                    nextX--;
                    break;
            }

            if (isValidMove(nextX, nextY)) {
                x = nextX;
                y = nextY;
            } else {
                break;
            }
        }

        return "(" + x + "," + y + ")";
    }

    private boolean isValidMove(int x, int y) {
        if (x < 0 || x >= fieldWidth || y < 0 || y >= fieldHeight) {
            return false;
        }

        for (int[] wall : walls) {
            if (isCollidingWithWall(x, y, wall)) {
                return false;
            }
        }

        return true;
    }

    private boolean isCollidingWithWall(int x, int y, int[] wall) {
        if (wall[0] == wall[2]) {
            if (x == wall[0] && y >= wall[1] && y < wall[3]) {
                return true;
            }
        } else if (wall[1] == wall[3]) {
            if (y == wall[1] && x >= wall[0] && x < wall[2]) {
                return true;
            }
        }

        return false;
    }
}
