package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Walkable {

    public static final String NORTH = "no";
    public static final String EAST = "ea";
    public static final String SOUTH = "so";
    public static final String WEST = "we";

    public static final int ROOM_WIDTH = 12;
    public static final int ROOM_HEIGHT = 8;
    public static final int[][] WALLS = {{3,0,3,3}, {4,3,7,3}, {7,0,7,2}, {1,4,8,4}};

    private int x;
    private int y;

    public Exercise0() {
        x = 3;
        y = 0;
    }

    @Override
    public String walkTo(String walkCommandString) {
        walkCommandString = walkCommandString.replace("[", "").replace("]", "");
        String[] parts = walkCommandString.split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        switch (direction) {
            case NORTH:
                y += moveVertical(steps,true);
                break;
            case EAST:
                x += moveHorizontal(steps,true);
                break;
            case SOUTH:
                y -= moveVertical(steps,false);
                break;
            case WEST:
                x -= moveHorizontal(steps,false);
                break;
            default:
                return null;
        }

        return "(" + x + "," + y + ")";
    }

    private int moveVertical(int steps, boolean up) {
        int target = y + (up ? steps : -steps);

        if (target < 0 || target >= ROOM_HEIGHT) {
            target = up ? ROOM_HEIGHT - 1 : 0;
        }

        for (int[] wall : WALLS) {
            if (wall[0] == wall[2] && wall[0] == x && ((up && wall[1] <= y && wall[3] > y && wall[3] <= target) ||
                    (!up && wall[1] >= y && wall[1] < y && wall[1] >= target))) {
                target = up ? wall[3] : wall[1];
            }
        }

        return up ? target - y : y - target;
    }

    private int moveHorizontal(int steps, boolean right) {
        int target = x + (right ? steps : -steps);

        if (target < 0 || target >= ROOM_WIDTH) {
            target = right ? ROOM_WIDTH - 1 : 0;
        }

        for (int[] wall : WALLS) {
            if (wall[1] == wall[3] && wall[1] == y && ((right && wall[0] <= x && wall[2] > x && wall[2] <= target) ||
                    (!right && wall[0] >= x && wall[0] < x && wall[0] >= target))) {
                target = right ? wall[2] : wall[0];
            }
        }

        return right ? target - x : x - target;
    }
}