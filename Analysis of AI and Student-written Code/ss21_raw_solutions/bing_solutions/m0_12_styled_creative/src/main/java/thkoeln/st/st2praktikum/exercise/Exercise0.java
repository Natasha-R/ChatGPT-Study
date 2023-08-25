package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    private static final String NORTH = "no";
    private static final String EAST = "ea";
    private static final String SOUTH = "so";
    private static final String WEST = "we";

    private static final int WIDTH = 12;
    private static final int HEIGHT = 8;
    private static final int[][] WALLS = {{3, 0, 3, 3}, {4, 3, 7, 3}, {7, 0, 7, 2}, {1, 4, 8, 4}};

    private int x;
    private int y;

    public Exercise0() {
        x = 3;
        y = 0;
    }

    public String walkTo(String c) {
        c = c.replace("[", "").replace("]", "");
        String[] p = c.split(",");
        String d = p[0];
        int s = Integer.parseInt(p[1]);

        switch (d) {
            case NORTH:
                moveNorth(s);
                break;
            case EAST:
                moveEast(s);
                break;
            case SOUTH:
                moveSouth(s);
                break;
            case WEST:
                moveWest(s);
                break;
            default:
                break;
        }

        return "(" + x + "," + y + ")";
    }

    private void moveNorth(int s) {
        for (int i = 0; i < s; i++) {
            if (y == HEIGHT - 1 || hasWall(x, y + 1)) {
                break;
            } else {
                y++;
            }
        }
    }

    private void moveEast(int s) {
        for (int i = 0; i < s; i++) {
            if (x == WIDTH - 1 || hasWall(x + 1, y)) {
                break;
            } else {
                x++;
            }
        }
    }

    private void moveSouth(int s) {
        for (int i = 0; i < s; i++) {
            if (y == 0 || hasWall(x, y - 1)) {
                break;
            } else {
                y--;
            }
        }
    }

    private void moveWest(int s) {
        for (int i = 0; i < s; i++) {
            if (x == 0 || hasWall(x - 1, y)) {
                break;
            } else {
                x--;
            }
        }
    }

    private boolean hasWall(int x, int y) {
        for (int[] w : WALLS) {
            int x1 = w[0];
            int y1 = w[1];
            int x2 = w[2];
            int y2 = w[3];
            if (x1 == x2 && x == x1 && y >= y1 && y <= y2) {
                return true;
            }
            if (y1 == y2 && y == y1 && x >= x1 && x <= x2) {
                return true;
            }
        }
        return false;
    }
}