package thkoeln.st.st2praktikum.exercise;

public class Exercise0 implements Moveable {

    private int x = 7;
    private int y = 7;
    private int width = 11;
    private int height = 8;
    private int[][] barriers = {{2, 1, 10, 1}, {2, 1, 2, 6}, {2, 6, 7, 6}, {10, 1, 10, 8}};

    @Override
    public String move(String moveCommandString) {
        String[] parts = moveCommandString.substring(1, moveCommandString.length() - 1).split(",");
        String direction = parts[0];
        int steps = Integer.parseInt(parts[1]);

        if (direction.equals("no")) {
            for (int i = 0; i < steps; i++) {
                if (y - 1 < 0 || isBarrier(x, y - 1)) {
                    break;
                }
                y--;
            }
        } else if (direction.equals("ea")) {
            for (int i = 0; i < steps; i++) {
                if (x + 1 >= width || isBarrier(x + 1, y)) {
                    break;
                }
                x++;
            }
        } else if (direction.equals("so")) {
            for (int i = 0; i < steps; i++) {
                if (y + 1 >= height || isBarrier(x, y + 1)) {
                    break;
                }
                y++;
            }
        } else if (direction.equals("we")) {
            for (int i = 0; i < steps; i++) {
                if (x - 1 < 0 || isBarrier(x - 1, y)) {
                    break;
                }
                x--;
            }
        }

        return "(" + x + "," + y + ")";
    }

    private boolean isBarrier(int x, int y) {
        for (int[] barrier : barriers) {
            if (x >= barrier[0] && x <= barrier[2] && y >= barrier[1] && y <= barrier[3]) {
                return true;
            }
        }

        // Check if the next position would be inside a barrier.
        int nextX = x + (direction.equals("ea") ? 1 : (direction.equals("we") ? -1 : 0));
        int nextY = y + (direction.equals("no") ? -1 : (direction.equals("so") ? 1 : 0));
        if (nextX >= 0 && nextX < width && nextY >= 0 && nextY < height) {
            for (int[] barrier : barriers) {
                if (nextX >= barrier[0] && nextX <= barrier[2] && nextY >= barrier[1] && nextY <= barrier[3]) {
                    return true;
                }
            }
        }

        return false;
    }
}