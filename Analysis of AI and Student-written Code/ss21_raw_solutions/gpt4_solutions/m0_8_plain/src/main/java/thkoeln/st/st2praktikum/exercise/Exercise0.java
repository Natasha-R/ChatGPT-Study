package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {
    private int x;
    private int y;
    private int[][] walls;

    public Exercise0() {
        x = 0;
        y = 2;
        walls = new int[][]{
                {3, 0, 3, 3},
                {5, 0, 5, 4},
                {4, 5, 7, 5},
                {7, 5, 7, 9}
        };
    }

    @Override
    public String walk(String walkCommandString) {
        String[] parts = walkCommandString.substring(1, walkCommandString.length() - 1).split(",");
        String direction = parts[0].trim();
        int steps = Integer.parseInt(parts[1].trim());

        switch (direction) {
            case "no":
                for (int i = 0; i < steps; i++) {
                    if (!isWallCollision(x, y + 1)) {
                        y++;
                    }
                }
                break;
            case "ea":
                for (int i = 0; i < steps; i++) {
                    if (!isWallCollision(x + 1, y)) {
                        x++;
                    }
                }
                break;
            case "so":
                for (int i = 0; i < steps; i++) {
                    if (!isWallCollision(x, y - 1)) {
                        y--;
                    }
                }
                break;
            case "we":
                for (int i = 0; i < steps; i++) {
                    if (!isWallCollision(x - 1, y)) {
                        x--;
                    }
                }
                break;
        }

        return String.format("(%d,%d)", x, y);
    }

    private boolean isWallCollision(int newX, int newY) {
        if (newX < 0 || newX > 11 || newY < 0 || newY > 8) {
            return true;
        }

        for (int[] wall : walls) {
            if (wall[0] == newX && wall[1] <= newY && newY < wall[3]) {
                return true;
            } else if (wall[1] == newY && wall[0] <= newX && newX < wall[2]) {
                return true;
            }
        }

        return false;
    }
}